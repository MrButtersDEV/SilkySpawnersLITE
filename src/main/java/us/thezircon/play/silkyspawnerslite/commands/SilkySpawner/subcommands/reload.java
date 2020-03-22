package us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.commands.CMDManager;

import java.util.List;
import java.util.logging.Logger;

public class reload extends CMDManager {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin.";
    }

    @Override
    public String getSyntax() {
        return "/silky reload";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {

        String msgPrefix = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgPrefix"));
        String msgReload = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgReload"));
        String msgNoperm = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("msgNoPerms"));

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("silkyspawners.admin")) {
                plugin.reloadConfig();
                plugin.langReload();
                player.sendMessage((msgPrefix + " " + msgReload));
            } else {
                player.sendMessage(msgPrefix + " " + msgNoperm);
            }
        } else {
            plugin.reloadConfig();
            plugin.langReload();
            log.info("Reloaded");
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
