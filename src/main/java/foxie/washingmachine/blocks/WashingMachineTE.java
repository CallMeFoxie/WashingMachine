package foxie.washingmachine.blocks;

import foxie.lib.SimpleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class WashingMachineTE extends TileEntity implements IInventory, ITickable {
   SimpleInventory inventory;

   public WashingMachineTE() {
      inventory = new SimpleInventory(2);
   }

   @Override
   public int getSizeInventory() {
      return inventory.getSizeInventory();
   }

   @Nullable
   @Override
   public ItemStack getStackInSlot(int index) {
      return inventory.getStackInSlot(index);
   }

   @Nullable
   @Override
   public ItemStack decrStackSize(int index, int count) {
      return inventory.decrStackSize(index, count);
   }

   @Nullable
   @Override
   public ItemStack removeStackFromSlot(int index) {
      return inventory.removeStackFromSlot(index);
   }

   @Override
   public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
      inventory.setInventorySlotContents(index, stack);
   }

   @Override
   public int getInventoryStackLimit() {
      return inventory.getInventoryStackLimit();
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer player) {
      return inventory.isUseableByPlayer(player);
   }

   @Override
   public void openInventory(EntityPlayer player) {
      inventory.openInventory(player);
   }

   @Override
   public void closeInventory(EntityPlayer player) {
      inventory.closeInventory(player);
   }

   @Override
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      return inventory.isItemValidForSlot(index, stack);
   }

   @Override
   public int getField(int id) {
      return inventory.getField(id);
   }

   @Override
   public void setField(int id, int value) {
      inventory.setField(id, value);
   }

   @Override
   public int getFieldCount() {
      return inventory.getFieldCount();
   }

   @Override
   public void clear() {
      inventory.clear();
   }

   @Override
   public String getName() {
      return inventory.getName();
   }

   @Override
   public boolean hasCustomName() {
      return inventory.hasCustomName();
   }

   @Override
   public ITextComponent getDisplayName() {
      return inventory.getDisplayName();
   }

   @Override
   public void update() {
      // TODO
   }
}
