package foxie.washingmachine.network;

import foxie.lib.FoxLog;
import foxie.washingmachine.blocks.WashingMachineTE;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageStartWashing implements IMessage, IMessageHandler<MessageStartWashing, IMessage> {

   public BlockPos pos;

   public MessageStartWashing() {

   }

   public MessageStartWashing(BlockPos pos) {
      this.pos = pos;
   }

   @Override
   public void fromBytes(ByteBuf buf) {
      pos = BlockPos.fromLong(buf.getLong(0));
   }

   @Override
   public void toBytes(ByteBuf buf) {
      buf.setLong(0, pos.toLong());
   }

   @Override
   public IMessage onMessage(MessageStartWashing message, MessageContext ctx) {
      TileEntity entity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.pos);

      if (!(entity instanceof WashingMachineTE)) {
         FoxLog.info("For some reason I got a request to wash a non-Washing Machine TE?");
         return null;
      }

      WashingMachineTE te = (WashingMachineTE) entity;
      te.startWashing();

      return null;
   }
}
