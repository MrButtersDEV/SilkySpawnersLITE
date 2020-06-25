package us.thezircon.play.silkyspawnerslite.nms;

import net.minecraft.server.v1_16_R1.NBTTagCompound;
import net.minecraft.server.v1_16_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class NMS_1_16 implements nmsHandler {

    @Override
    public ItemStack set(String key, ItemStack item, String data) {
        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsItemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();

        if (nmsItemCompound == null) {
            return null;
        }

        //EntityType entity = EntityType.valueOf(data); //

        nmsItemCompound.set(key, NBTTagString.a(data));
        //nmsItemCompound.set(key, NBTTagString.a(entity.name()));
        nmsItem.setTag(nmsItemCompound);

        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public String get(String key, ItemStack item) {
        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nmsItemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
        if (nmsItemCompound == null) {
            return null;
        }

        String nbtKey = nmsItemCompound.getString(key);
        //EntityType entity = null;

        //try {
        //    entity = EntityType.valueOf(nbtKey);
        //} catch (Exception ignore) {}

        //return entity;
        return nbtKey;
    }
}