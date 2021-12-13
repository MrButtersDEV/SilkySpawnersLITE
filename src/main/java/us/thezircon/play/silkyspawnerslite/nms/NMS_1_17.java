//package us.thezircon.play.silkyspawnerslite.nms;
//
//
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagString;
//import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
//
//import org.bukkit.inventory.ItemStack;
//
//public class NMS_1_17 implements nmsHandler {
//
//    @Override
//    public ItemStack set(String key, ItemStack item, String data) {
//
////        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
////        NBTTagCompound nmsItemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
////
////        if (nmsItemCompound == null) {
////            return null;
////        }
////        nmsItemCompound.set(key, NBTTagString.a(data));
////        nmsItem.setTag(nmsItemCompound);
////
////        return CraftItemStack.asBukkitCopy(nmsItem);
//        return null;
//    }
//
//    @Override
//    public String get(String key, ItemStack item) {
////        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
////        NBTTagCompound nmsItemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
////        if (nmsItemCompound == null) {
////            return null;
////        }
////
////        String nbtKey = nmsItemCompound.getString(key);
////        return nbtKey;
//        return null;
//    }
//}