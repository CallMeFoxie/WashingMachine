package foxie.washingmachine.blocks;

import foxie.lib.Configurable;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
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

      List<ItemStack> armor = gatherArmor();

      // apply effects
      if (first != null && first.getItem() instanceof ItemPotion)
         applyEffects(armor, PotionUtils.getEffectsFromStack(first));
      if (second != null && second.getItem() instanceof ItemPotion)
         applyEffects(armor, PotionUtils.getEffectsFromStack(second));

      // damage or clear armour
      int dampening = 0;
      if (first != null && first.getItem() == wp) dampening++;
      if (second != null && second.getItem() == wp) dampening++;
      damageOrClearArmour(armor, dampening);

      // remove washing powder and other things
      if (first != null)
         inventory.extractItem(0, first.stackSize, false);
      if (second != null)
         inventory.extractItem(1, second.stackSize, false);
   }

   private List<ItemStack> gatherArmor() {
      List<ItemStack> stacks = new ArrayList<>();

      for (int i = 2; i < 6; i++) {
         if (inventory.getStackInSlot(i) != null) {
            ItemStack stack = inventory.extractItem(i, inventory.getStackInSlot(i).stackSize, false);
            stacks.add(stack);
         }
      }

      return stacks;
   }

   private void addPotionToItemStack(ItemStack stack, PotionEffect effect) {
      NBTTagCompound compound = new NBTTagCompound();
      if (stack.hasTagCompound())
         compound = stack.getTagCompound();

      if (!compound.hasKey("Potions"))
         compound.setTag("Potions", new NBTTagList());

      ((NBTTagList) compound.getTag("Potions")).appendTag(effect.writeCustomPotionEffectToNBT(new NBTTagCompound()));

      stack.setTagCompound(compound);
   }

   private void applyEffects(List<ItemStack> armor, List<PotionEffect> effects) {
      for (ItemStack arm : armor)
         for (PotionEffect effect : effects)
            addPotionToItemStack(arm, effect);
   }

   private void damageOrClearArmour(List<ItemStack> armor, int dampening) {
      for(ItemStack arm : armor) {
         if(!(arm.getItem() instanceof ItemArmor))
            continue; // wat, how did this happen?

         ItemArmor xarmor = (ItemArmor)arm.getItem();
         int[] oreIds = OreDictionary.getOreIDs(new ItemStack(xarmor.getArmorMaterial().getRepairItem()));
         boolean isMetal = false;
         boolean isGem = false;

         for(int id : oreIds) {
            if(OreDictionary.getOreName(id).startsWith("ingot")) isMetal = true;
            if(OreDictionary.getOreName(id).startsWith("gem")) isGem = true;
         }

         float damage = 0.1f; // 10%
         if (isMetal)
            damage = 0.25f; // 25% for metal
         if (isGem)
            damage = 0.15f; // 15% for gem
         damage -= dampening / 10; // remove 10% for each dampening

         int nDamage = (int)(arm.getItemDamage() - (arm.getItemDamage() * damage));
         if(nDamage < 0)
            nDamage = 0;
         if (nDamage >= arm.getMaxDamage())
            nDamage = arm.getMaxDamage();

         arm.setItemDamage(nDamage);

      }
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
