package foxie.washingmachine.client.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class SlotCapability extends Slot {
   protected IItemHandler handler;
   private TileEntity entity;
   int index;

   public SlotCapability(TileEntity entity, int index, int xPosition, int yPosition) {
      super(null, index, xPosition, yPosition);

      this.index = index;
      this.entity = entity;
      handler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
   }

   @Nullable
   @Override
   public ItemStack getStack() {
      return handler.getStackInSlot(index);
   }

   @Override
   public void putStack(@Nullable ItemStack stack) {
      if(getStack() != null)
         handler.extractItem(index, handler.getStackInSlot(index).stackSize, false);

      handler.insertItem(index, stack, false);

      entity.markDirty();
   }

   @Override
   public void onSlotChanged() {
      // should somehow invalidate? TODO
   }

   @Override
   public int getSlotStackLimit() {
      return 64;
   }

   @Override
   public ItemStack decrStackSize(int amount) {
      ItemStack stack = getStack();
      ItemStack returnStack = null;

      if (stack != null) {
         if (stack.stackSize <= amount) {
            returnStack = stack;
            stack = null;
         } else {
            returnStack = stack.splitStack(amount);
            if (stack.stackSize == 0) {
               stack = null;
            }
         }
      }

      putStack(stack);

      return returnStack;
   }

   @Override
   public boolean isHere(IInventory inv, int slotIn) {
      return super.isHere(inv, slotIn);
   }

   @Override
   public boolean isSameInventory(Slot other) {
      if(other instanceof SlotCapability)
         return ((SlotCapability)other).handler == this.handler;

      return false;
   }
}
