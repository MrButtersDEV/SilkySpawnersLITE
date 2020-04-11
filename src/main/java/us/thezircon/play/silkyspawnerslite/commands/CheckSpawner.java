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
import org.bukkit.inventory.meta.BlockStateMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class CheckSpawner implements CommandExecutor {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack hand = player.getInventory().getItemInMainHand();

            String type1 = plugin.getNMS().get("SilkyMob", hand);

            BlockStateMeta hand_meta = (BlockStateMeta) hand.getItemMeta();
            CreatureSpawner cs = (CreatureSpawner) hand_meta.getBlockState();

            String type2 = cs.getSpawnedType().toString();

            player.sendMessage("Display Name: " + hand.getItemMeta().getDisplayName());
            player.sendMessage("Silky Type: " + type1);
            player.sendMessage("Spawner Type: " + type2);

        }

        return true;

    }

}
