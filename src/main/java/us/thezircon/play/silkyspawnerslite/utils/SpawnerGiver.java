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

    public void giveSelf(Player player){
        if (player.hasPermission("silkyspawners.give.self")) {
            player.sendMessage(msgPrefix + " " + msgGiveSelf.replace("{TYPE}", mobtype.replace("_", " ")));
            player.getInventory().addItem(giveItem);
        } else {
            player.sendMessage(msgPrefix + " " + msgNoperm);
        }
    }

    public void giveOther(CommandSender sender, Player target){
        if (sender.hasPermission("silkyspawners.give.other")) {
            sender.sendMessage(msgPrefix + " " + msgGiveOther.replace("{TYPE}", mobtype.replace("_", " ")).replace("{TARGET}", target.getName()));
            target.sendMessage(msgPrefix + " " + msgReceiveSpawner.replace("{TYPE}", mobtype.replace("_", " ")));
            target.getInventory().addItem(giveItem);
        } else {
            sender.sendMessage(msgPrefix + " " + msgNoperm);
        }
    }

}
