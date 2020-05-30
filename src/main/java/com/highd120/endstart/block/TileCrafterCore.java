package com.highd120.endstart.block;

import java.util.List;

import javax.annotation.Nonnull;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

public class TileCrafterCore extends TileEntityBase {
	private boolean active;
	
    @Override
    public void update() {
    	
    }
    
    public void activate() {
    	active = !active;
    }
    
    public boolean isActive() {
    	return active;
    }
    
    public void breakEvent() {
    	
    }
}
