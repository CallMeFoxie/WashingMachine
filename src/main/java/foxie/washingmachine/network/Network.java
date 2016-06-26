package foxie.washingmachine.network;

import foxie.washingmachine.WashingMachine;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Network {
   public static Network              instance;
   public static SimpleNetworkWrapper networkChannel;

   public Network() {
      instance = this;
      networkChannel = NetworkRegistry.INSTANCE.newSimpleChannel(WashingMachine.MODID);
      init();
   }

   private static void init() {
      networkChannel.registerMessage(MessageStartWashing.class, MessageStartWashing.class, 1, Side.SERVER);
   }
}
