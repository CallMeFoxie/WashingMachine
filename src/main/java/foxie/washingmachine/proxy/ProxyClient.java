package foxie.washingmachine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

public class ProxyClient extends ProxyCommon {
   @Override
   public void preinit() {
      super.preinit();
      ProxyCommon.wm.initModel();

   }

   @Override
   public void init() {
      super.init();
      Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(wp, 0, new ModelResourceLocation("washingmachine:washingpowder", "inventory"));

   }
}
