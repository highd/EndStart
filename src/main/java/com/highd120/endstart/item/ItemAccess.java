package com.highd120.endstart.item;

import com.highd120.endstart.util.ItemUtil;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;

public class ItemAccess extends ItemHasMeta {

	public ItemAccess() {
		super(new String[] {
			"access_input",
			"access_output"
		});
        this.maxStackSize = 1;
	}
	
    private static IItemHandler getInventory(final World world, final BlockPos pos, final EnumFacing side) {
		final TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null) {
			return null;
		}
		if (tileEntity instanceof TileEntityChest) {
			final IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) tileEntity);
			if (doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
				return doubleChest;
			}
		}
		return tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				? tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				: null;
    }
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (player.isSneaking()) {
			IItemHandler handler = getInventory(world, pos, side);
			if (handler == null) {
				return EnumActionResult.PASS;
			}
			if (stack.getMetadata() == 0) {
				for (int i = 0; i < handler.getSlots(); i++) {
					ItemStack extractItem = handler.extractItem(i, 1, false);
					if (extractItem != null && !extractItem.isEmpty()) {
						ItemUtil.dropItem(world, pos, extractItem);
						break;
					}
				}
			} else {
				ItemStack target = player.inventory.getStackInSlot(0);
				if (!target.isEmpty()) {
					ItemStack input = target.copy();
					input.setCount(1);
					for (int i = 0; i < handler.getSlots(); i++) {
						ItemStack insertedItem = handler.insertItem(i, input, false);
						if (insertedItem == null || insertedItem.isEmpty()) {
							target.shrink(1);
							break;
						}
					}
				}
			}
		} else {
			stack.setItemDamage(1 - stack.getMetadata());
		}
		return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
	}

}
