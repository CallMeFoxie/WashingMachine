package foxie.washingmachine.blocks;

import foxie.lib.Configurable;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

import static foxie.washingmachine.proxy.ProxyCommon.wp;

public class WashingMachineTE extends TileEntity implements ICapabilityProvider, ITickable {

   @Configurable(comment = "how long it will take for the washing machine to run [ticks]", min = "1")
   private static int TICKS = 100; // 2400

   ItemStackHandler inventory;

   // progress = 0: stopped
   // progress < TICKS: running, ++ each tick
   // progress >= TICKS: finished, spit out the result
   private int progress;

   public WashingMachineTE() {
      inventory = new ItemStackHandler(6);
   }

   @Override
   public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
      return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
   }

   @Override
   public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
      if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
         return (T) inventory;

      return super.getCapability(capability, facing);
   }

   @Override
   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      super.writeToNBT(compound);
      compound.setTag("inventory", inventory.serializeNBT());
      compound.setInteger("progress", progress);
      return compound;
   }

   @Override
   public void readFromNBT(NBTTagCompound compound) {
      super.readFromNBT(compound);
      if (compound.hasKey("inventory"))
         inventory.deserializeNBT((NBTTagCompound) compound.getTag("inventory"));
      progress = compound.getInteger("progress");
   }

   @Override
   public void update() {
      if (progress < 0) // safety
         progress = 0;

      if (progress == 0)
         return;

      if (progress >= TICKS) // always compare >= on edges
         finish();

      progress++;
   }

   private void finish() {
      progress = 0;

      ItemStack first = inventory.getStackInSlot(0);
      ItemStack second = inventory.getStackInSlot(1);

      // apply effects
      if (first != null && first.getItem() instanceof ItemPotion)
         applyEffects(PotionUtils.getEffectsFromStack(first));
      if (second != null && second.getItem() instanceof ItemPotion)
         applyEffects(PotionUtils.getEffectsFromStack(second));

      // damage or clear armour
      int dampening = 0;
      if (first != null && first.getItem() == wp) dampening++;
      if (second != null && second.getItem() == wp) dampening++;
      damageOrClearArmour(dampening);

      // remove washing powder and other things
      if (first != null)
         inventory.extractItem(0, first.stackSize, false);
      if (second != null)
         inventory.extractItem(1, second.stackSize, false);
   }

   private void applyEffects(List<PotionEffect> effect) {

   }

   private void damageOrClearArmour(int dampening) {

   }

   public void startWashing() {
      if (progress == 0) {
         progress++;
      }

      // already washing
   }

   public int getProgress() {
      return progress;
   }
}
