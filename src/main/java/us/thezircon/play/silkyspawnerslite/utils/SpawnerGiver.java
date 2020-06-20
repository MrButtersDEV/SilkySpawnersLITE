package us.thezircon.play.silkyspawnerslite.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class SpawnerGiver {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    private ItemStack giveItem;
    private String mobtype;

    private String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
    private String msgNoperm = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgNoPerms"));
    private String msgGiveSelf = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgGiveSelf"));
    private String msgGiveOther = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgGiveOther"));
    private String msgReceiveSpawner = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgReceiveSpawner"));

    /**
     * Constructor for deciding what spawner should be used.
     * @apiNote EntityType - Spigot entity name format
     * @param  spawnerType The type of a spawner that shall be given
     */
    public SpawnerGiver(EntityType spawnerType) {
        ItemStack spawner_to_give = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
        CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();

        csm.setSpawnedType(spawnerType);

        meta.setBlockState(csm);
        meta.setDisplayName(ChatColor.AQUA + spawnerType.toString().replace("_", " ") + " Spawner");
        meta.addItemFlags();
        spawner_to_give.setItemMeta(meta);

        this.mobtype = spawnerType.toString();
        this.giveItem = plugin.getNMS().set("SilkyMob", spawner_to_give, csm.getSpawnedType().toString());
    }

    /**
     * Gives the player a spawner while requiring permission.
     *
     * @param  player the player who is giving thyself a spawner
     */
    public void giveSelf(Player player){
        if (player.hasPermission("silkyspawners.give.self")) {
            player.sendMessage(msgPrefix + " " + msgGiveSelf.replace("{TYPE}", mobtype.replace("_", " ")));
            player.getInventory().addItem(giveItem);
        } else {
            player.sendMessage(msgPrefix + " " + msgNoperm);
        }
    }

    /**
     * Gives player a spawner sending a messages to sender &
     * requiring permission.
     *
     * @param  sender this is who/what is sending a player a spawner and they
     *                will be notified once a spawner is given.
     * @param  target the target player who should receive a spawner
     */
    public void giveOther(CommandSender sender, Player target){
        if (sender.hasPermission("silkyspawners.give.other")) {
            sender.sendMessage(msgPrefix + " " + msgGiveOther.replace("{TYPE}", mobtype.replace("_", " ")).replace("{TARGET}", target.getName()));
            target.sendMessage(msgPrefix + " " + msgReceiveSpawner.replace("{TYPE}", mobtype.replace("_", " ")));
            target.getInventory().addItem(giveItem);
        } else {
            sender.sendMessage(msgPrefix + " " + msgNoperm);
        }
    }

    /**
     * Gives player a spawner without sending any messages &
     * requiring permission.
     *
     * @param  target the target player who should receive a spawner
     * @param  sendMsg should a player be notified when they receive a spawner.
     */
    public void give(Player target, boolean sendMsg){
        if (sendMsg) {
            target.sendMessage(msgPrefix + " " + msgReceiveSpawner.replace("{TYPE}", mobtype.replace("_", " ")));
        }
        target.getInventory().addItem(giveItem);
    }

    /**
     * Gets a spawner that can be used for reference.
     */
    public ItemStack get(){
        return giveItem;
    }
}
