package us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.commands.CMDManager;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class type extends CMDManager {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    @Override
    public String getName() {
        return "type";
    }

    @Override
    public String getDescription() {
        return "Change a spawners type by looking at it or having a spawner in your hand.";
    }

    @Override
    public String getSyntax() {
        return "/silky type [type]";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
            String msgNoperm = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgNoPerms"));
            String msgNotSpawner = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgNotSpawner"));
            String msgSpawnerTypeError = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgSpawnerTypeError"));
            String msgSpawnerChanged = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgSpawnerChanged"));
            String defaultSpawnerName = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("spawnerName"));

            if (!(args.length > 1)) {
                player.sendMessage(msgPrefix + ChatColor.RED + " " + getSyntax());
                return;
            }

            //Change Spawner - Looking
            Block target = player.getTargetBlock(null, 5);
            Material inhand = player.getInventory().getItemInMainHand().getType();

            String mobtype = args[1].toUpperCase();

            if (inhand.equals(Material.SPAWNER) && player.hasPermission("silkyspawners.change.hand")) { // HAND
                // Change type - In hand
                ItemStack hand = player.getInventory().getItemInMainHand();
                BlockStateMeta hand_meta = (BlockStateMeta) hand.getItemMeta();
                CreatureSpawner csm = (CreatureSpawner) hand_meta.getBlockState();

                csm.setSpawnedType(EntityType.valueOf(mobtype));
                //hand_meta.setDisplayName(ChatColor.AQUA + mobtype + " Spawner");
                hand_meta.setDisplayName(defaultSpawnerName.replace("{TYPE}", csm.getSpawnedType().toString().replace("_", " ")));
                hand_meta.setBlockState(csm);

                hand.setItemMeta(hand_meta);

                ItemStack newHand = plugin.getNMS().set("SilkyMob", hand, csm.getSpawnedType().toString());

                int slot = player.getInventory().getHeldItemSlot();
                player.getInventory().setItem(slot, newHand);
                player.updateInventory();

                player.sendMessage(msgPrefix + " " + msgSpawnerChanged.replace("{TYPE}", mobtype));

            } else {

                if (!player.hasPermission("silkyspawners.change.look")) {
                    player.sendMessage(msgPrefix + " " + msgNoperm);
                    return;
                }

                if (target.getState().getBlock().getType() != Material.SPAWNER) {
                    //Not a spawner
                    player.sendMessage(msgPrefix + " " + msgNotSpawner);
                } else {
                    // Is a spawner
                    try {
                        EntityType type = EntityType.valueOf(mobtype);

                        CreatureSpawner spawnereditor = (CreatureSpawner) target.getState();
                        spawnereditor.setSpawnedType(type);
                        spawnereditor.update();

                        player.sendMessage(msgPrefix + " " + msgSpawnerChanged.replace("{TYPE}", mobtype));

                    } catch (IllegalArgumentException err) {
                        player.sendMessage(msgPrefix + " " + msgSpawnerTypeError.replace("{TYPE}", args[1]));
                    }
                }
            }
        } else {
            sender.sendMessage("[SilkySpawners] You need to be a player to run this command!");
        }
    }

    @Override
    public List<String> arg1(Player player, String[] args) {
        return Arrays.stream(EntityType.values()).map(EntityType::name).collect(Collectors.toList());
    }

    @Override
    public List<String> arg2(Player player, String[] args) {
        return null;
    }

    @Override
    public List<String> arg3(Player player, String[] args) {
        return null;
    }
}
