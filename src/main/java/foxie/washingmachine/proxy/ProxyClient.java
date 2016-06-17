package foxie.washingmachine.proxy;

public class ProxyClient extends ProxyCommon {
   @Override
   public void init() {
      super.init();
      ProxyCommon.wm.initModel();
   }
}
