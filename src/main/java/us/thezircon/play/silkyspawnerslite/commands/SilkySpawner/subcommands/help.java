package us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.commands.CMDManager;

import java.util.List;

public class help extends CMDManager {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays help menu";
    }

    @Override
    public String getSyntax() {
        return "/silky help";
    }

    @Override
    public void perform(CommandSender player, String[] args) {

        String helpTitle = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("helpTitle"));
        String helpHelp = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("helpHelp"));
        String helpGive = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("helpGive"));
        String helpSpawner = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("helpSpawner"));
        String helpReload = ChatColor.translateAlternateColorCodes('&', plugin.getLangConfig().getString("helpReload"));

        player.sendMessage(helpTitle);
        player.sendMessage(helpHelp);
        player.sendMessage(helpGive);
        player.sendMessage(helpSpawner);
        player.sendMessage(helpReload);

    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
