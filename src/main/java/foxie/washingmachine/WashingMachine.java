package foxie.washingmachine;

import foxie.lib.Config;
import foxie.lib.IFoxieMod;
import foxie.washingmachine.proxy.ProxyCommon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = WashingMachine.MODID, name = WashingMachine.NAME, version = WashingMachine.VERSION)
public class WashingMachine implements IFoxieMod {
   public static final String MODID   = "washingmachine";
   public static final String NAME    = "Washing Machine";
   public static final String VERSION = "@VERSION@";

   @SidedProxy(clientSide = "foxie.washingmachine.proxy.ProxyClient", serverSide = "foxie.washingmachine.proxy.ProxyCommon")
   public static ProxyCommon proxy;

   @Mod.Instance(MODID)
   public static WashingMachine INSTANCE;

   private static Config config;

   private Events events;

   @Mod.EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      events = new Events();
      config = new Config(event.getSuggestedConfigurationFile());

      NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

      events.preinit();

      proxy.preinit();
   }

   @Mod.EventHandler
   public void init(FMLInitializationEvent event) {
      events.init();
      proxy.init();
   }

   @Mod.EventHandler
   public void postinit(FMLPostInitializationEvent event) {
      proxy.postinit();
      events.postinit();
   }

   @Override
   public Config getConfig() {
      return config;
   }

   @Override
   public String getModId() {
      return MODID;
   }
}
