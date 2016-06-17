package foxie.washingmachine.proxy;

import foxie.washingmachine.blocks.WashingMachine;
import foxie.washingmachine.blocks.WashingMachineTE;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ProxyCommon {
   public static WashingMachine wm;
   public void preinit() {

   }

   public void init() {
      wm = new WashingMachine();
      GameRegistry.registerTileEntity(WashingMachineTE.class, "washingmachine");
   }

   public void postinit() {

   }
}