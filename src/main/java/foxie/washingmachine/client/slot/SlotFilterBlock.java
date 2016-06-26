package foxie.washingmachine.client.slot;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class SlotFilterBlock extends SlotCapability {

   Block block;

   public SlotFilterBlock(TileEntity entity, int par2, int par3, int par4, Block block) {
      super(entity, par2, par3, par4);
      this.block = block;
   }

   @Override
   public boolean isItemValid(ItemStack item) {
      if (!(item.getItem() instanceof ItemBlock))
         return false;

      ItemBlock it = (ItemBlock) item.getItem();

      if (it.block == this.block)
         return true;

      return false;
   }

}
