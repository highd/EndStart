package com.highd120.endstart.block;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockStove extends Block implements IUsableFireStarter{
	public enum State implements IStringSerializable {
		NORMAL, HAS_COAL, FIRE, WEAK_FIRE;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	public static final PropertyEnum<State> STATE = PropertyEnum.create("state", State.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public BlockStove() {
		super(Material.ROCK);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        IBlockState state = blockState.getBaseState()
                .withProperty(STATE, State.NORMAL)
                .withProperty(FACING, EnumFacing.NORTH);
        setDefaultState(state);
		setHarvestLevel("pickaxe", 1);
		setHardness(4.0F);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState blockState, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);
		TileStove tile = (TileStove) worldIn.getTileEntity(pos);
		State state = blockState.getValue(STATE);
		playerIn.sendMessage(new TextComponentString(state.toString()));
		if (stack.getItem() == Items.FLINT_AND_STEEL && isUsable(worldIn, pos, blockState)) {
			stack.damageItem(1, playerIn);
			tile.fire();
            worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, 0.8F);
		} else if (StoveFuelList.isFuel(stack) && (state == State.FIRE || state == State.WEAK_FIRE)) {
			tile.addFuel();
			if (!playerIn.isCreative()) {
				stack.shrink(1);
			}
		} else if (!worldIn.isRemote) {
			tile.changeItem(stack, playerIn.isCreative());
		}
		return true;
	}
	
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, 
    		ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }
    
    @Override
    public int getLightValue(IBlockState blockState) {
    	State state = blockState.getValue(STATE);
    	if (state == State.FIRE) {
    		return 7;
    	}
    	return 0;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	TileStove tile = (TileStove) world.getTileEntity(pos);

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
        return true;
    }

	@Override
	public void fire(World world, BlockPos pos, IBlockState blockstate) {
    	TileStove tile = (TileStove) world.getTileEntity(pos);

        if (tile != null) {
            tile.fire();
        }
	}

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, STATE, FACING);
    }
	
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(STATE).ordinal() | 
        		((state.getValue(FACING).ordinal() - 2) << 2);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	int stateId = meta & 0b11;
    	int faceId = meta >> 2;
    	State state = State.NORMAL;
    	if (0 <= stateId && stateId < State.values().length) {
    		state = State.values()[stateId];
    	}
    	EnumFacing face = EnumFacing.NORTH; 
    	if (0 <= faceId && faceId <= 3) {
    		face = EnumFacing.values()[faceId + 2];
    	}
        return getDefaultState()
                .withProperty(STATE, state)
                .withProperty(FACING, face);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileStove();
    }

	@Override
	public boolean isUsable(World world, BlockPos pos, IBlockState blockstate) {
		return blockstate.getValue(STATE) == State.HAS_COAL;
	}
}
