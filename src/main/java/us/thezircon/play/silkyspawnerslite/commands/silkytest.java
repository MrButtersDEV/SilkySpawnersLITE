package us.thezircon.play.silkyspawnerslite.commands;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Creature;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class silkytest implements CommandExecutor {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack hand = player.getInventory().getItemInMainHand();

            String e = plugin.getNMS().get("SilkyMob", hand);
            String e2 = plugin.getNMS().get("BlockEntityTag.SpawnData.id", hand);

            player.sendMessage("TYPE: " + e);
            player.sendMessage("E2 - " +e2);

        }

        return true;

    }

}
