package foxie.washingmachine.proxy;

import foxie.washingmachine.WashingPowder;
import foxie.washingmachine.blocks.WashingMachine;
import foxie.washingmachine.blocks.WashingMachineTE;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProxyCommon {
   public static WashingMachine wm;
   public static WashingPowder wp;

   public void preinit() {
      wm = new WashingMachine();
      wp = new WashingPowder();
      GameRegistry.registerTileEntity(WashingMachineTE.class, "washingmachine");
      GameRegistry.register(wp);
   }

   public void init() {

   }

   public void postinit() {

   }

   public IThreadListener getThreadListener(MessageContext ctx) {
      return (IThreadListener) ctx.getServerHandler().playerEntity.worldObj;
   }
}
