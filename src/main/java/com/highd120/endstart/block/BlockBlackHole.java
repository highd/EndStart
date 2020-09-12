package com.highd120.endstart.block;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

@BlockRegister(name = "blackhole")
public class BlockBlackHole extends Block {
	public BlockBlackHole() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 5);
		setSoundType(SoundType.GLASS);
		setHardness(500.0F);
        setResistance(2000.0F);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
		setResistance(2000.0F);
	}

}
