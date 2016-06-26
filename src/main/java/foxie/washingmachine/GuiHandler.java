package foxie.washingmachine;

import foxie.washingmachine.client.WashingMachineGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
   @Override
   public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      return ID == 0 ? new WashingMachineContainer(player.inventory, world.getTileEntity(new BlockPos(x, y, z))) : null;
   }

   @Override
   public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
      return ID == 0 ? new WashingMachineGUI(player.inventory, world.getTileEntity(new BlockPos(x, y, z))) : null;
   }
}
