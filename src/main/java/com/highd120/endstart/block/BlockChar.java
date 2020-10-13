package com.highd120.endstart.block;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.highd120.endstart.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChar extends net.minecraft.block.Block {
	public enum Color implements IStringSerializable {
		RED, RED_BLACK, GREEN;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	public enum State implements IStringSerializable {
		NORMAL, SAND;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);
    public static final PropertyEnum<State> STATE = PropertyEnum.create("state", State.class);

    private static final AxisAlignedBB AABB = new AxisAlignedBB(
    		1.0 / 16.0, 0, 1.0 / 16.0, 
    		15.0 / 16.0, 2.0 / 16.0, 15.0 / 16.0);
	public BlockChar() {
		super(Material.ROCK);
        IBlockState state = blockState.getBaseState()
                .withProperty(COLOR, Color.RED)
                .withProperty(STATE, State.NORMAL);
        setDefaultState(state);
	}
	
	private boolean isStick(ItemStack stack) {
		int meta = stack.getMetadata();
		return meta == 18 && stack.getItem() == ModItems.extra;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState blockState, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		State state = blockState.getValue(STATE);
		if (!worldIn.isRemote && state == State.NORMAL && stack.isEmpty() && playerIn.isSneaking()) {
    		TileChar tile = (TileChar) worldIn.getTileEntity(pos);
        	tile.setOldRecipe(playerIn, playerIn.isCreative());
		}
        if (!worldIn.isRemote && state == State.NORMAL && !isStick(stack) && !playerIn.isSneaking()) {
    		TileChar tile = (TileChar) worldIn.getTileEntity(pos);
        	tile.changeItem(stack, playerIn.isCreative());
        }
        if (state == State.SAND && isStick(stack)) {
    		TileChar tile = (TileChar) worldIn.getTileEntity(pos);
    		tile.changeNotmal();
        }
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState downBlock = worldIn.getBlockState(pos.down());
		return downBlock.isFullBlock();
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		IBlockState downBlock = worldIn.getBlockState(pos.down());
		if (downBlock.getBlock() == Blocks.AIR) {
            worldIn.setBlockToAir(pos);
		}
	}

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos) {
		State state = blockState.getValue(STATE);
        return state == State.NORMAL ? AABB : FULL_BLOCK_AABB;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		State state = blockState.getValue(STATE);
        return state == State.NORMAL ? NULL_AABB : FULL_BLOCK_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    	return false;
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR, STATE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal() * 2 + 
        		state.getValue(STATE).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	Color color = Color.values()[meta / 2];
    	State state = State.values()[meta & 1];
        return getDefaultState()
                .withProperty(COLOR, color)
                .withProperty(STATE, state);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileChar(state);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	TileChar tile = (TileChar) world.getTileEntity(pos);

        if (tile != null) {
            tile.breakEvent();
        }

        super.breakBlock(world, pos, state);
    }
    
    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
    		EntityPlayer player) {
    	int color = state.getValue(COLOR).ordinal();
    	return new ItemStack(ModItems.chalk, 1, color);
    }
}
