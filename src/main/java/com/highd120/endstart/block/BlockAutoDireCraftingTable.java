package com.highd120.endstart.block;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.util.block.BlockRegister;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@BlockRegister(name = "auto_dire_crafting")
public class BlockAutoDireCraftingTable extends Block {
	public BlockAutoDireCraftingTable() {
		super(Material.IRON);
		setSoundType(SoundType.GLASS);
		setHardness(50.0F);
		setResistance(2000.0F);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && !playerIn.isSneaking()) {
			playerIn.openGui(EndStartMain.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileAutoDireCraftingTable();
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileAutoDireCraftingTable tile = (TileAutoDireCraftingTable) world.getTileEntity(pos);

		if (tile != null) {
			tile.breakEvent();
		}

		super.breakBlock(world, pos, state);
	}

}
