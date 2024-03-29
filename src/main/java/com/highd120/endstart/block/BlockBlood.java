package com.highd120.endstart.block;

import java.util.Locale;
import java.util.Random;

import javax.annotation.Nonnull;

import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.util.ItemUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlood extends Block {
	enum Color implements IStringSerializable {
		RED, BLACK;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	enum Type implements IStringSerializable {
		SLAB, CUBE;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
    private static final AxisAlignedBB AABB_SLAB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
    private static final AxisAlignedBB AABB_CUBE = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);
    public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);

    /**
     * コンストラクター。
     */
    public BlockBlood() {
        super(Material.ROCK);
        setHardness(0.1F);
        setSoundType(SoundType.STONE);
        IBlockState state = blockState.getBaseState()
                .withProperty(COLOR, Color.RED)
                .withProperty(TYPE, Type.SLAB);
        setDefaultState(state);
        setTickRandomly(true);
    }
    
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		world.scheduleUpdate(pos, this, 10);
	}

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	super.randomTick(worldIn, pos, state, rand);
    	Color color = state.getValue(COLOR);
    	if (color == Color.RED) {
    		worldIn.setBlockState(pos, state.withProperty(BlockBlood.COLOR, BlockBlood.Color.BLACK), 3);
    	}
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
    	if (state.getValue(TYPE) ==  Type.SLAB) {
    		return AABB_SLAB;
    	}
        return AABB_CUBE;
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR, TYPE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal() + (state.getValue(TYPE).ordinal() << 1);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	Color color = Color.values()[meta  & 1];
    	Type type = Type.values()[meta >> 1];
        return getDefaultState()
                .withProperty(COLOR, color)
                .withProperty(TYPE, type);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		ItemUtil.dropItem(world, pos, new ItemStack(ModItems.itemBlood, 2,
				state.getValue(COLOR).ordinal()));
    }

}
