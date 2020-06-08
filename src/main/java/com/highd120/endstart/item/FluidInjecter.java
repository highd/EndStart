package com.highd120.endstart.item;

import com.highd120.endstart.util.NbtTagUtil;
import com.highd120.endstart.util.WorldUtil;
import com.highd120.endstart.util.item.ItemRegister;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.UniversalBucket;

@ItemRegister(name = "fluid_injecter")
public class FluidInjecter extends ItemBase {
	public FluidInjecter() {
	}

	private void exchangeDrum(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
		String blockName = world.getBlockState(pos).getBlock().getRegistryName().toString();
		if (!blockName.equals("extrautils2:drum") || world.isRemote) {
			return;
		}
		InventoryPlayer inventory = player.inventory;
		int slot = inventory.getSlotFor(stack);
		if (slot + 1 >= inventory.getSizeInventory()) {
			return;
		}
		ItemStack bucket = inventory.getStackInSlot(slot + 1);
		if (bucket.getItem() instanceof UniversalBucket) {
			String bucketFluid = NbtTagUtil.getString("FluidName", bucket).orElse("");
			WorldUtil.getNbtTag(world, pos).ifPresent(tag -> {
				NBTTagCompound tank = tag.getCompoundTag("tank");
				if (tank.hasKey("FluidName")) {
					String drumFluid = tank.getString("FluidName");
					NbtTagUtil.setString("FluidName", bucket, drumFluid);
					if (!bucketFluid.equals("")) {
						tank.setString("FluidName", bucketFluid);
					} else {
						NBTTagCompound empty = new NBTTagCompound();
						empty.setString("Empty", "");
						tag.setTag("tank", empty);
					}
				} else {
					inventory.setInventorySlotContents(slot + 1, new ItemStack(Items.BUCKET));
					if (!bucketFluid.equals("")) {
						NBTTagCompound fluidTag = new NBTTagCompound();
						fluidTag.setString("FluidName", bucketFluid);
						fluidTag.setInteger("Amount", 1000);
						tag.setTag("tank", fluidTag);
					}
				}
				TileEntity tile = world.getTileEntity(pos);
				tile.readFromNBT(tag);
			});
			return;
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		exchangeDrum(stack, playerIn, worldIn, pos);
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);

	}
}
