package foxie.washingmachine.client.slot;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SlotFilterItem extends SlotCapability {

   private final Item itemFilter;

   public SlotFilterItem(TileEntity entity, int par2, int par3, int par4, Item filter) {
      super(entity, par2, par3, par4);
      this.itemFilter = filter;
   }

   @Override
   public boolean isItemValid(ItemStack item) {
      Item it = item.getItem();
      return it == this.itemFilter;
   }

}
