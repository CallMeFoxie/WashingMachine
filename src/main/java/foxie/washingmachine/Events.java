package foxie.washingmachine;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;

public class Events {
   public static Events INSTANCE;

   public Events() {
      INSTANCE = this;
   }

   public void preinit() {

   }

   public void init() {
      MinecraftForge.EVENT_BUS.register(INSTANCE);
   }

   public void postinit() {
   }

   public void serverStarted(FMLServerStartedEvent event) {

   }

}
