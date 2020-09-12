package com.highd120.endstart.block;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.highd120.endstart.item.ItemBlood;
import com.highd120.endstart.util.ItemUtil;
import com.highd120.endstart.util.block.BlockRegister;
import com.highd120.endstart.util.item.ItemManager;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@BlockRegister(name = "blood")
public class BlockBlood extends Block {
	enum Color implements IStringSerializable {
		RED, BLACK;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1);
    public static final PropertyEnum<Color> COLOR = PropertyEnum.create("color", Color.class);

    /**
     * コンストラクター。
     */
    public BlockBlood() {
        super(Material.ROCK);
        setHardness(0.1F);
        setSoundType(SoundType.STONE);
        IBlockState state = blockState.getBaseState()
                .withProperty(COLOR, Color.RED);
        setDefaultState(state);
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
        return AABB;
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	Color color = Color.values()[meta];
        return getDefaultState()
                .withProperty(COLOR, color);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileBlood();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
		ItemUtil.dropItem(world, pos, ItemManager.getItemStack(
				ItemBlood.class, state.getValue(COLOR).ordinal()));
    }

}
