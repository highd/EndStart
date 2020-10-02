package com.highd120.endstart.block;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;

public class TileLavaStone extends TileEntityBase {
	int count = 0;
	@Override
	public void subReadNbt(NBTTagCompound compound) {
		super.subReadNbt(compound);
		count = compound.getInteger("count");
	}
	
	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		super.subWriteNbt(compound);
		compound.setInteger("count", count);
	}
	
	@Override
	public void update() {
		Block topBlock = world.getBlockState(pos.up()).getBlock();
		if (topBlock == Blocks.COBBLESTONE) {
			count++;
			if (count == 15) {
				world.setBlockState(pos.up(), Blocks.LAVA.getDefaultState(), 3);
				world.setBlockToAir(pos.up(2));
				count = 0;
			}
		}
	}

}
