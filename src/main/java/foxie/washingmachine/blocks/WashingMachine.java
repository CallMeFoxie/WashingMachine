package foxie.washingmachine.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class WashingMachine extends BlockContainer {
   public static final PropertyDirection FACING = BlockHorizontal.FACING;

   public WashingMachine() {
      super(Material.IRON);
      setUnlocalizedName("washingmachine");
      setRegistryName("washingmachine:washingmachine");
      setCreativeTab(CreativeTabs.REDSTONE);
      GameRegistry.register(this);
      GameRegistry.register(new ItemBlock(this), getRegistryName());
      setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   @Override
   public TileEntity createNewTileEntity(World worldIn, int meta) {
      return new WashingMachineTE();
   }

   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
   }

   public EnumBlockRenderType getRenderType(IBlockState state) {
      return EnumBlockRenderType.MODEL;
   }

   @Override
   public IBlockState getStateFromMeta(int meta) {
      return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
   }

   @Override
   public int getMetaFromState(IBlockState state) {
      return state.getValue(FACING).getIndex();
   }

   @Override
   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, FACING);
   }

   @Override
   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
      TileEntity te = worldIn.getTileEntity(pos);
      if(te == null || playerIn.isSneaking())
         return false;

      playerIn.openGui(foxie.washingmachine.WashingMachine.INSTANCE, 0, worldIn, pos.getX(),  pos.getY(),  pos.getZ());
      return true;

   }
}
