package us.thezircon.play.silkyspawnerslite.events;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.utils.HexFormat;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.List;

public class breakSpawner implements Listener{

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    private DecimalFormat f = new DecimalFormat("#0.00");

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e){

        boolean requireMinePerm = plugin.getConfig().getBoolean("requireMineperm");
        boolean doDrop2Ground = plugin.getConfig().getBoolean("doDrop2Ground");
        boolean doPreventBreaking = plugin.getConfig().getBoolean("doPreventBreaking");
        boolean requireSilk = plugin.getConfig().getBoolean("requireSilk");
        boolean chargeOnBreak = plugin.getConfig().getBoolean("chargeOnBreak.enabled");
        boolean sendMSG = plugin.getConfig().getBoolean("chargeOnBreak.sendMSG");
        double priceOnBreak = plugin.getConfig().getDouble("chargeOnBreak.price");
        String msgFullInv = HexFormat.format(plugin.getLangConfig().getString("msgFullInv"));
        String msgPrefix = HexFormat.format(plugin.getLangConfig().getString("msgPrefix"));
        String msgChargedOnMine = HexFormat.format(plugin.getLangConfig().getString("msgChargedOnMine"));
        String msgFundsNeeded = HexFormat.format(plugin.getLangConfig().getString("msgFundsNeeded"));
        String defaultSpawnerName = HexFormat.format(plugin.getLangConfig().getString("spawnerName"));
        String msgYouMayNotBreakThis = HexFormat.format(plugin.getLangConfig().getString("msgYouMayNotBreakThis"));

        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location loc = e.getBlock().getLocation();

        //Check if world is blacklisted
        World world = player.getWorld();
        List<String> blacklistedWorlds = plugin.getConfig().getStringList("blacklist");
        if (blacklistedWorlds.contains(world.getName())) {
            return;
        }

        //Drop %
        double spawnerDropChance = plugin.getConfig().getDouble("spawnerDropChance");
        if (spawnerDropChance != 1.00) {
            double dropNum = Math.random();
            if (dropNum <= spawnerDropChance) {
                return;
            }
        }

        if (block.getType().equals(Material.SPAWNER)){

            if (requireMinePerm && doPreventBreaking && (!player.hasPermission("silkyspawners.mine") || (requireSilk && (!player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))) && !player.isSneaking())) {
                e.setCancelled(true);
                player.sendMessage(msgYouMayNotBreakThis);
                return;
            }

            if (requireSilk && (!player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))) {
                return;
            }

            // Stop spawners from dropping xp
            e.setExpToDrop(0);

            if (requireMinePerm && !player.hasPermission("silkyspawners.mine")) {
                return;
            }

            e.setExpToDrop(0); //Disabled XP

            if (chargeOnBreak) {
                EconomyResponse r = plugin.getEconomy().withdrawPlayer(player, priceOnBreak);
                if(r.transactionSuccess()) {
                    if (sendMSG) {
                        player.sendMessage(msgPrefix + " " + msgChargedOnMine.replace("{PRICE}", f.format(priceOnBreak)));
                    }
                } else {
                    player.sendMessage(msgPrefix + " " + msgFundsNeeded);
                    e.setCancelled(true);
                    return;
                }

            }

            //Get Spawner
            CreatureSpawner cs = (CreatureSpawner) block.getState();

            //Give or Drop Spawner
            ItemStack spawner_to_give = new ItemStack(Material.SPAWNER);
            BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
            CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();

            csm.setSpawnedType(cs.getSpawnedType());

            //Spawners Meta
            meta.setBlockState(csm);
            //meta.setDisplayName(ChatColor.AQUA + (cs.getSpawnedType().toString().replace("_", " ")) + " Spawner");
            meta.setDisplayName(defaultSpawnerName.replace("{TYPE}", cs.getSpawnedType().toString().replace("_", " ")));
            meta.addItemFlags();

            spawner_to_give.setItemMeta(meta); // Set Meta

            //Apply NBT Data
            ItemStack finalSpawner = plugin.getNMS().set("SilkyMob", spawner_to_give, cs.getSpawnedType().toString());

            if (doDrop2Ground) { // Drops Spawner to ground
                block.getWorld().dropItemNaturally(loc, finalSpawner);
            } else { // Gives spawner to inventory
                if (player.getInventory().firstEmpty() == -1) {
                    block.getWorld().dropItemNaturally(loc, finalSpawner);
                    player.sendMessage(msgPrefix+" "+msgFullInv);
                } else {
                    player.getInventory().addItem(finalSpawner);
                }
            }
        }

    }
}