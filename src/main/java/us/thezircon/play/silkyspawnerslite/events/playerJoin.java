package us.thezircon.play.silkyspawnerslite.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class playerJoin implements Listener {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
    String msgUpdate = ChatColor.translateAlternateColorCodes('&', "&6➤ &eClick &6&lHERE&e to view the latest version.");

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("silkyspawners.admin.notifyupdate") && !plugin.UP2Date){
            String ver = Bukkit.getServer().getPluginManager().getPlugin("SilkySpawnersLITE").getDescription().getVersion();
            player.sendMessage(msgPrefix + " " + ChatColor.YELLOW + "Version: " + ChatColor.RED + ver + ChatColor.YELLOW + " is not up to date. Please check your console on next startup or reload.");

            TextComponent message = new TextComponent(msgUpdate);
            message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/silky-spawners-lite-silk-touch-your-spawners-silk-spawners.76103/" ) );
            message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder( "Click to open on spigot!" ).create() ) );
            player.spigot().sendMessage( message );
        }

    }

}