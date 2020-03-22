package us.thezircon.play.silkyspawnerslite.commands.SilkySpawner;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import us.thezircon.play.silkyspawnerslite.commands.CMDManager;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands.give;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands.help;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands.reload;
import us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands.type;

import java.util.ArrayList;
import java.util.List;

public class Silky implements TabExecutor {

    private ArrayList<CMDManager> subcommands = new ArrayList<>();

    public Silky(){
        subcommands.add(new reload());
        subcommands.add(new type());
        subcommands.add(new give());
        subcommands.add(new help());
    }

    public ArrayList<CMDManager> getSubCommands(){
        return subcommands;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length > 0){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    getSubCommands().get(i).perform(sender, args);
                }
            }
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (sender instanceof ConsoleCommandSender) {
            return null;
        }

        if (args.length == 1){
            ArrayList<String> subcommandsArguments = new ArrayList<>();

            for (int i = 0; i < getSubCommands().size(); i++){
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }

            return subcommandsArguments;
        }else if(args.length >= 2){
            for (int i = 0; i < getSubCommands().size(); i++){
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }

        return null;
    }

}
