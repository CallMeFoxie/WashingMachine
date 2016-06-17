package foxie.washingmachine.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WashingMachine extends BlockContainer {
   public static final PropertyDirection FACING = BlockHorizontal.FACING;

   public WashingMachine() {
      super(Material.IRON);
      setUnlocalizedName("washingmachine:washingmachine");
      setRegistryName("washingmachine:washingmachine");
      GameRegistry.register(this);
      GameRegistry.register(new ItemBlock(this), getRegistryName());
      setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
   }

   @SideOnly(Side.CLIENT)
   public void initModel() {
      ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
   }

   @Override
   public TileEntity createNewTileEntity(World worldIn, int meta) {
      return new WashingMachineTE();
   }

   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
   {
      return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
   }

   public EnumBlockRenderType getRenderType(IBlockState state)
   {
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
}
