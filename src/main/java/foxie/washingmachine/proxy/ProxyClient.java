package foxie.washingmachine.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class ProxyClient extends ProxyCommon {
   @Override
   public void preinit() {
      super.preinit();
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(wm), 0, new ModelResourceLocation("washingmachine:washingmachine", "inventory"));
      ModelLoader.setCustomModelResourceLocation(wp, 0, new ModelResourceLocation("washingmachine:washingpowder", "inventory"));
   }

   @Override
   public void init() {
      super.init();
   }
}
