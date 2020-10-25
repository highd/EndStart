package com.highd120.endstart.block;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockExtra extends Block {	
	public enum Type implements IStringSerializable {
		BLACK_HOLE, MACHINE_BLOCK_3;
		@Override
		public String getName() {
			return name().toLowerCase(Locale.ROOT);
		}
	}
	public static final PropertyEnum<Type> TYPE = PropertyEnum.create("type", Type.class);
	public BlockExtra() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 2);
		setHardness(50.0F);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        IBlockState state = blockState.getBaseState()
                .withProperty(TYPE, Type.BLACK_HOLE);
        setDefaultState(state);
	}

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < Type.values().length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}
	
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	Type type = Type.values()[meta];
        return getDefaultState()
                .withProperty(TYPE, type);
    }
}
