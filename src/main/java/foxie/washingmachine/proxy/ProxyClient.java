package foxie.washingmachine.proxy;

public class ProxyClient extends ProxyCommon {
   @Override
   public void preinit() {
      super.preinit();
      ProxyCommon.wm.initModel();
   }

   @Override
   public void init() {
      super.init();

   }
}
