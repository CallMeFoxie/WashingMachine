package foxie.washingmachine.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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

   @Override
   public IThreadListener getThreadListener(MessageContext ctx) {
      return Minecraft.getMinecraft();
   }
}
