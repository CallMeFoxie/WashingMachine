package foxie.washingmachine;

import foxie.washingmachine.client.slot.SlotCapability;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class WashingMachineContainer extends SmartContainer {

   public WashingMachineContainer(InventoryPlayer inv, TileEntity te) {
      super(inv, te, 6);

      IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

      addSlotToContainer(new SlotCapability(te, 0, 9, 8));
      addSlotToContainer(new SlotCapability(te, 1, 31, 8));

      addSlotToContainer(new SlotCapability(te, 2, 59, 29));
      addSlotToContainer(new SlotCapability(te, 3, 77, 29));
      addSlotToContainer(new SlotCapability(te, 4, 59, 47));
      addSlotToContainer(new SlotCapability(te, 5, 77, 47));

      bindPlayerInventory(inv);
   }
}
