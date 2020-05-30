package com.highd120.endstart.block;

import javax.annotation.Nonnull;

import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@BlockRegister(name = "crafter_core")
public class BlockCrafterCore extends Block  {
    public static final PropertyBool ACTIVE = PropertyBool.create("active");
	public BlockCrafterCore() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.GLASS);
		setHardness(100.0F);
		setResistance(2000.0F);
		IBlockState state = blockState.getBaseState().withProperty(ACTIVE, false);
        setDefaultState(state);
	}
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
            EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX,
            float hitY, float hitZ) {
        if (!worldIn.isRemote) {
        	TileCrafterCore tile = (TileCrafterCore) worldIn.getTileEntity(pos);
            tile.activate();
            worldIn.setBlockState(pos, state.withProperty(ACTIVE, Boolean.valueOf(tile.isActive())), 4);
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	TileCrafterCore tile = (TileCrafterCore) world.getTileEntity(pos);
        if (tile != null) {
            tile.breakEvent();
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCrafterCore();
    }
    
    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ACTIVE);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = state.getValue(ACTIVE) ? 1 : 0;
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean active = meta == 1;
        return getDefaultState().withProperty(ACTIVE, active);
    }   
}
