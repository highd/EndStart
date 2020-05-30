package com.highd120.endstart.block;

import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

@BlockRegister(name = "crafter_heater")
public class BlockCrafterHeater extends Block {
	public BlockCrafterHeater() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.GLASS);
		setHardness(100.0F);
		setResistance(2000.0F);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
