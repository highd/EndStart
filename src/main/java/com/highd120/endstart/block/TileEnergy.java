package com.highd120.endstart.block;

import com.highd120.endstart.block.base.TileEntityBase;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEnergy extends TileEntityBase {

	@Override
	public void update() {
		for (EnumFacing facing : EnumFacing.VALUES) {
			TileEntity entity = world.getTileEntity(pos.offset(facing));
			if (entity == null) continue;
			IEnergyStorage storage = entity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
			if (storage == null) continue;
			storage.receiveEnergy(10000, false);
		}
	}

}
