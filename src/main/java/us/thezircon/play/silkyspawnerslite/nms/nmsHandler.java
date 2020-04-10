package us.thezircon.play.silkyspawnerslite.nms;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public interface nmsHandler {

    public ItemStack set(String key, ItemStack item, String data);
    //public ItemStack set(String key, ItemStack item, EntityType entity);

    public String get(String key, ItemStack item);
    //public EntityType get(String key, ItemStack item);

    public boolean formated(String key, ItemStack item);

}
