package us.thezircon.play.silkyspawnerslite.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import us.thezircon.play.silkyspawnerslite.SilkySpawnersLITE;

public class placeSpawner implements Listener{

    SilkySpawnersLITE plugin = SilkySpawnersLITE.getPlugin(SilkySpawnersLITE.class);

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onSpawnerPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();

        if (material != Material.SPAWNER) {
            return;
        }

        ItemStack placed = e.getItemInHand();
        //ItemMeta meta = placed.getItemMeta();

        //TEST\
        System.out.println(placed.toString());
        BlockStateMeta hand_meta = (BlockStateMeta) placed.getItemMeta();
        CreatureSpawner cs = (CreatureSpawner) hand_meta.getBlockState();
        System.out.println(cs.getSpawnedType().toString());
        /////////////

        EntityType entity = null;
        try {
            //String entityName = ChatColor.stripColor(meta.getDisplayName()).split(" Spawner")[0].replace(" ", "_").toUpperCase();
            //entity = EntityType.valueOf(entityName);
            entity = EntityType.valueOf(plugin.getNMS().get("SilkyMob", placed));
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            spawner.setSpawnedType(entity);
            spawner.update();
        } catch (Exception ignored) {}
    }
}