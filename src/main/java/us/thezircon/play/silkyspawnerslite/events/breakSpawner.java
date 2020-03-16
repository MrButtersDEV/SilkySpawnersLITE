package us.thezircon.play.silkyspawnerslite.events;

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

public class breakSpawner implements Listener{

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    boolean requireMinePerm = plugin.getConfig().getBoolean("requireMinePerm");
    boolean doDrop2Ground = plugin.getConfig().getBoolean("doDrop2Ground");
    String msgFullInv = plugin.getLangConfig().getString("msgFullInv");
    String msgPrefix = plugin.getLangConfig().getString("msgPrefix");

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent e){

        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location loc = e.getBlock().getLocation();

        System.out.println("breakEvent - Run");

        //Drop %
        double spawnerDropChance = plugin.getConfig().getDouble("spawnerDropChance");
        if (spawnerDropChance != 1.00) {
            double dropNum = Math.random();
            if (dropNum <= spawnerDropChance) {
                return;
            }
        }

        if ((block.getType().equals(Material.SPAWNER)) && (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))){

            if (requireMinePerm && !player.hasPermission("silkyspawners.mine")) {
                return;
            }

            System.out.println("IS Spawner - Is Silk - has perm");

            //Get Spawner
            CreatureSpawner cs = (CreatureSpawner) block.getState();

            //Give or Drop Spawner
            ItemStack spawner_to_give = new ItemStack(Material.SPAWNER);
            BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
            CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();

            csm.setSpawnedType(cs.getSpawnedType());

            meta.setBlockState(csm);
            meta.setDisplayName(ChatColor.AQUA + (cs.getSpawnedType().toString().replace("_", " ")) + " Spawner");
            meta.addItemFlags();

            spawner_to_give.setItemMeta(meta);

            //ADD INV SIZE CHECK & FLIP
            if (doDrop2Ground) { // Drops Spawner to ground
                block.getWorld().dropItemNaturally(loc, spawner_to_give);
            } else { // Gives spawner to inventory
                if (player.getInventory().firstEmpty() == -1) {
                    block.getWorld().dropItemNaturally(loc, spawner_to_give);
                    player.sendMessage(msgPrefix+" "+msgFullInv);
                } else {
                    player.getInventory().addItem(spawner_to_give);
                }
            }
        }

    }
}