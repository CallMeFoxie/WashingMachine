package foxie.washingmachine;

import foxie.lib.FoxLog;
import foxie.lib.client.slot.SlotFilterInstanceof;
import foxie.lib.client.slot.SlotFilterItem;
import foxie.lib.inventory.SmartContainer;
import foxie.washingmachine.blocks.WashingMachineTE;
import foxie.washingmachine.proxy.ProxyCommon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.tileentity.TileEntity;

public class WashingMachineContainer extends SmartContainer {

   private WashingMachineTE te;

   public WashingMachineContainer(InventoryPlayer inv, TileEntity te) {
      super(te);

      if(!(te instanceof WashingMachineTE)) {
         FoxLog.error("Tried opening Washing Machine container on non-washing machine TE!");
         return;
      }

      this.te = (WashingMachineTE) te;

      addSlotToContainer(new SlotFilterItem(te, 0, 9, 8, Items.POTIONITEM, Items.SPLASH_POTION, ProxyCommon.wp));
      addSlotToContainer(new SlotFilterItem(te, 1, 31, 8, Items.POTIONITEM, Items.SPLASH_POTION, ProxyCommon.wp));

      addSlotToContainer(new SlotFilterInstanceof(te, 2, 59, 29, ItemArmor.class));
      addSlotToContainer(new SlotFilterInstanceof(te, 3, 77, 29, ItemArmor.class));
      addSlotToContainer(new SlotFilterInstanceof(te, 4, 59, 47, ItemArmor.class));
      addSlotToContainer(new SlotFilterInstanceof(te, 5, 77, 47, ItemArmor.class));

      bindPlayerInventory(inv);
   }

   @Override
   public boolean canInteractWith(EntityPlayer player) {
      return te.getProgress() == 0 && super.canInteractWith(player);
   }
}
