package com.highd120.endstart.block;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.BlockFalling;

@BlockRegister(name = "end_sand")
public class BlockEndSand extends BlockFalling {
	public BlockEndSand() {
        setCreativeTab(EndStartCreativeTab.INSTANCE);
	}

}
