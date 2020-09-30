package com.highd120.endstart.block;

import com.highd120.endstart.EndStartCreativeTab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCrafterCore extends BlockHasSingleItem  {
	public BlockCrafterCore() {
		super(Material.IRON);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.GLASS);
		setHardness(100.0F);
        setResistance(2000.0F);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
		setResistance(2000.0F);
    }

    @Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem != null && heldItem.getItem() == Items.STICK) {
            activate(worldIn, pos);
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    public void activate(World world, BlockPos pos) {
        TileCrafterCore tile = (TileCrafterCore) world.getTileEntity(pos);
        if (tile != null) {
            tile.active();
        }
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCrafterCore();
    }
}
