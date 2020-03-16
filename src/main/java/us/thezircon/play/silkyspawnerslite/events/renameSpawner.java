package us.thezircon.play.silkyspawnerslite.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class renameSpawner implements Listener {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    @EventHandler
    public void onClickEvent(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        String itemDiabledAnvil = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("itemDiabledAnvil"));

        ItemStack denyItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = denyItem.getItemMeta();
        meta.setDisplayName(itemDiabledAnvil);
        denyItem.setItemMeta(meta);

        String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
        String msgDiabledAnvil = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgDiabledAnvil"));
        Boolean anvilRename = plugin.getConfig().getBoolean("anvilRename");

        if (anvilRename) {
            return;
        }

        if (e.getInventory().getType().equals(InventoryType.ANVIL)){
            if (e.getCurrentItem().getType().equals(Material.SPAWNER)) {
                e.getInventory().setItem(3, denyItem);
                e.setCancelled(true);
                player.sendMessage(msgPrefix+" "+msgDiabledAnvil);
            } else if (e.getCurrentItem().equals(denyItem)) {
                e.setCancelled(true);
            }
        }
    }

}