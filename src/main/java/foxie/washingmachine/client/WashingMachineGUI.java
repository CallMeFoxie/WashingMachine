package foxie.washingmachine.client;

import foxie.washingmachine.WashingMachineContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class WashingMachineGUI extends GuiContainer {
   public WashingMachineGUI(InventoryPlayer inv, TileEntity tileEntity) {
      super(new WashingMachineContainer(inv, tileEntity));
   }

   @Override
   protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
      ResourceLocation rsrc = new ResourceLocation("washingmachine", "textures/gui/washingmachine.png");

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(rsrc);
      int k = (this.width - this.xSize) / 2;
      int l = (this.height - this.ySize) / 2;
      this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
   }
}
