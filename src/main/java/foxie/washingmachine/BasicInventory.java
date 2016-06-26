package foxie.washingmachine;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;

public class BasicInventory implements IInventory {

   protected final ItemStack[] inventory;
   protected final String inventoryName;
   protected final int inventoryLimit;
   protected Container owner;

   public BasicInventory(int slots, String inventoryName, int inventoryLimit, Container container) {
      this.inventoryName = inventoryName;
      this.inventoryLimit = inventoryLimit;
      inventory = new ItemStack[slots];
      this.owner = container;
   }

   public BasicInventory(int slots, String inventoryName, int inventoryLimit) {
      this.inventoryName = inventoryName;
      this.inventoryLimit = inventoryLimit;
      inventory = new ItemStack[slots];
   }

   public BasicInventory(int slots) {
      this.inventoryName = "";
      this.inventoryLimit = 64;
      this.inventory = new ItemStack[slots];
   }

   public void setContainer(Container container) {
      this.owner = container;
   }

   @Override
   public int getSizeInventory() {
      return inventory.length;
   }

   @Override
   public ItemStack getStackInSlot(int slot) {
      if (slot >= inventory.length)
         return null;

      return inventory[slot];
   }

   @Override
   public ItemStack decrStackSize(int slot, int amount) {
      ItemStack stack = null;

      if (inventory[slot] != null) {
         if (inventory[slot].stackSize <= amount) {
            stack = inventory[slot];
            inventory[slot] = null;
         } else {
            stack = inventory[slot].splitStack(amount);
            if (inventory[slot].stackSize == 0) {
               inventory[slot] = null;
            }
         }
      }

      return stack;
   }

   @Nullable
   @Override
   public ItemStack removeStackFromSlot(int index) {
      return null;
   }

   @Override
   public void setInventorySlotContents(int slot, ItemStack item) {
      inventory[slot] = item;
   }

   @Override
   public int getInventoryStackLimit() {
      return inventoryLimit;
   }

   @Override
   public void markDirty() {

   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      return true;
   }

   @Override
   public void openInventory(EntityPlayer player) {

   }

   @Override
   public void closeInventory(EntityPlayer player) {

   }

   @Override
   public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
      return true;
   }

   @Override
   public int getField(int id) {
      return 0;
   }

   @Override
   public void setField(int id, int value) {

   }

   @Override
   public int getFieldCount() {
      return 0;
   }

   @Override
   public void clear() {

   }

   public void load(NBTTagCompound nbt) {
      NBTTagList list = nbt.getTagList("Inventory", 10);
      for (int i = 0; i < list.tagCount(); i++) {
         NBTTagCompound tag = list.getCompoundTagAt(i);
         byte slot = tag.getByte("Slot");
         if (slot >= 0 && slot < inventory.length) {
            if (tag.getBoolean("Empty") == true)
               inventory[slot] = null;
            else
               inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
         }
      }
   }

   public void save(NBTTagCompound nbt) {
      NBTTagList itemList = new NBTTagList();
      for (int i = 0; i < inventory.length; i++) {
         ItemStack stack = inventory[i];
         if (stack != null) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot", (byte) i);
            stack.writeToNBT(tag);
            itemList.appendTag(tag);
         } else {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setByte("Slot", (byte) i);
            tag.setBoolean("Empty", true);
            itemList.appendTag(tag);
         }
      }
      nbt.setTag("Inventory", itemList);
   }

   @Override
   public String getName() {
      return inventoryName;
   }

   @Override
   public boolean hasCustomName() {
      return inventoryName != "";
   }

   @Override
   public ITextComponent getDisplayName() {
      return new TextComponentString(inventoryName);
   }
}