package us.thezircon.play.silkyspawnerslite.commands.SilkySpawner.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;
import us.thezircon.play.silkyspawnerslite.commands.CMDManager;
import us.thezircon.play.silkyspawnerslite.utils.HexFormat;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class give extends CMDManager {

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    private static final Logger log = Logger.getLogger("Minecraft");

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getDescription() {
        return "Gives a spawner to yourself or someone else.";
    }

    @Override
    public String getSyntax() {
        return "/Silky give [type] <user>";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {


        String msgPrefix = HexFormat.format(plugin.getLangConfig().getString("msgPrefix"));
        String msgNoperm = HexFormat.format(plugin.getLangConfig().getString("msgNoPerms"));
        String msgSpawnerTypeError = HexFormat.format(plugin.getLangConfig().getString("msgSpawnerTypeError"));
        String msgGiveSelf = HexFormat.format(plugin.getLangConfig().getString("msgGiveSelf"));
        String msgGiveOther = HexFormat.format(plugin.getLangConfig().getString("msgGiveOther"));
        String msgReceiveSpawner = HexFormat.format(plugin.getLangConfig().getString("msgReceiveSpawner"));
        String defaultSpawnerName = HexFormat.format(plugin.getLangConfig().getString("spawnerName"));
        String helpGive = HexFormat.format( plugin.getLangConfig().getString("helpGive"));

        if (sender instanceof Player) { // Player
            Player player = (Player) sender;

            if (args.length > 1) {
                //Get Spawner Type
                String mobType = args[1].toUpperCase();

                //Give or Drop Spawner
                ItemStack spawner_to_give = new ItemStack(Material.SPAWNER);
                BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
                CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();

                csm.setSpawnedType(EntityType.valueOf(mobType));

                meta.setBlockState(csm);
                //meta.setDisplayName(ChatColor.AQUA + mobtype.replace("_", " ") + " Spawner");
                meta.setDisplayName(defaultSpawnerName.replace("{TYPE}", csm.getSpawnedType().toString().replace("_", " ")));

                meta.addItemFlags();
                spawner_to_give.setItemMeta(meta);

                ItemStack giveItem = plugin.getNMS().set("SilkyMob", spawner_to_give, csm.getSpawnedType().toString());

                if (args.length == 2 && player.hasPermission("silkyspawners.give.self")) { // No User Name
                    player.sendMessage(msgPrefix + " " + msgGiveSelf.replace("{TYPE}", mobType.replace("_", " ")));
                    player.getInventory().addItem(giveItem);
                } else if (args.length <= 4 && args.length >= 3 && player.hasPermission("silkyspawners.give.other")) { // User Name
                    Player target = Bukkit.getPlayer(args[2]);

                    if (target == null) {
                        sender.sendMessage(helpGive);
                        return;
                    }

                    if (args.length==4) {
                        int amt = Integer.valueOf(args[3]);
                        giveItem.setAmount(amt);
                    }
                    target.getInventory().addItem(giveItem);

                    player.sendMessage(msgPrefix + " " + msgGiveOther.replace("{TYPE}", mobType.replace("_", " ")).replace("{TARGET}", target.getName().toString()));
                    target.sendMessage(msgPrefix + " " + msgReceiveSpawner.replace("{TYPE}", mobType.replace("_", " ")));
                }
            } else {
                player.sendMessage(msgPrefix + ChatColor.RED + " " + getSyntax());
            }
        } else { // Console
            if (args.length > 0) {
                //Get Spawner Type
                String mobtype = args[1].toUpperCase();

                //Give or Drop Spawner
                ItemStack spawner_to_give = new ItemStack(Material.SPAWNER);
                BlockStateMeta meta = (BlockStateMeta) spawner_to_give.getItemMeta();
                CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();

                csm.setSpawnedType(EntityType.valueOf(mobtype));

                meta.setBlockState(csm);
                //meta.setDisplayName(ChatColor.AQUA + mobtype.replace("_", " ") + " Spawner");
                meta.setDisplayName(defaultSpawnerName.replace("{TYPE}", csm.getSpawnedType().toString().replace("_", " ")));

                meta.addItemFlags();
                spawner_to_give.setItemMeta(meta);

                ItemStack giveItem = SilkySpawnersLITE.getNMS().set("SilkyMob", spawner_to_give, csm.getSpawnedType().toString());

                if (args.length >= 3 && args.length < 5) { // User Name
                    Player target = Bukkit.getPlayer(args[2]);

                    if (args.length==4) {
                        int amt = Integer.valueOf(args[3]);
                        giveItem.setAmount(amt);
                    }

                    if (target != null) {
                        target.getInventory().addItem(giveItem);
                    } else {
                        System.out.println("Invalid target - null");
                    }

                    sender.sendMessage(msgPrefix + " " + msgGiveOther.replace("{TYPE}", mobtype.replace("_", " ")).replace("{TARGET}", target.getName().toString()));
                    target.sendMessage(msgPrefix + " " + msgReceiveSpawner.replace("{TYPE}", mobtype.replace("_", " ")));
                }
            }
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
