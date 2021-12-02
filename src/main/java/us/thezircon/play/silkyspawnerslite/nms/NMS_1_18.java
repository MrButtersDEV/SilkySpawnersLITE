//package us.thezircon.play.silkyspawnerslite.nms;
//
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagString;
//import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
//import org.bukkit.inventory.ItemStack;
//
//public class NMS_1_18 implements nmsHandler {
//
//    public ItemStack set(String key, ItemStack item, String data) {
//        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound nmsItemCompound = nmsItem.r() ? nmsItem.s() : new NBTTagCompound();
//        if (nmsItemCompound == null) {
//            return null;
//        } else {
//            nmsItemCompound.a(key, NBTTagString.a(data));
//            nmsItem.c(nmsItemCompound);
//            return CraftItemStack.asBukkitCopy(nmsItem);
//        }
//    }
//
//    public String get(String key, ItemStack item) {
//        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
//        NBTTagCompound nmsItemCompound = nmsItem.r() ? nmsItem.s() : new NBTTagCompound();
//        if (nmsItemCompound == null) {
//            return null;
//        } else {
//            String nbtKey = nmsItemCompound.l(key);
//            return nbtKey;
//        }
//    }
//}