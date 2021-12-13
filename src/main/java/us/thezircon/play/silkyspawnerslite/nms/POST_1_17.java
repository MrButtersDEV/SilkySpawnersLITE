package us.thezircon.play.silkyspawnerslite.nms;

import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class POST_1_17 implements nmsHandler {

    public ItemStack set(String key, ItemStack item, String data) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey("silkymob", "type"), PersistentDataType.STRING, data);

        BlockStateMeta meta = (BlockStateMeta) itemMeta;
        CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();
        csm.setSpawnedType(EntityType.valueOf(data));

        meta.setBlockState(csm);
        item.setItemMeta(meta);
        return item;
    }

    public String get(String key, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        BlockStateMeta meta = (BlockStateMeta) itemMeta;
        CreatureSpawner csm = (CreatureSpawner) meta.getBlockState();
        //return itemMeta.getPersistentDataContainer().get(new NamespacedKey("silkymob", "type"), PersistentDataType.STRING);
        return csm.getSpawnedType().toString();
    }
}