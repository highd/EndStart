package com.highd120.endstart.block.advancementcafter;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.BlockStove.State;
import com.highd120.endstart.block.base.BlockHasSingleItem;
import com.highd120.endstart.block.base.TileHasSingleItem;
import com.highd120.endstart.item.ModItems;

import net.minecraft.advancements.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAdvancementCrafter extends Block {

	public BlockAdvancementCrafter() {
		super(Material.IRON);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
		setHarvestLevel("pickaxe", 1);
		setHardness(4.0F);
	}
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
    		EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	TileAdvancementCrafter tile = (TileAdvancementCrafter) worldIn.getTileEntity(pos);
		if (!worldIn.isRemote && !playerIn.isSneaking()) {
			playerIn.openGui(EndStartMain.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
    	MinecraftServer server = worldIn.getMinecraftServer();
    	if (server == null) {
    		return true;
    	}
    	if (!(playerIn instanceof EntityPlayerMP)) {
    		return true;
    	}
    	ItemStack heldItem = playerIn.getHeldItem(hand);
        if (heldItem != null && heldItem.getItem() == ModItems.extra && heldItem.getMetadata() == 25) {
        	tile.check((EntityPlayerMP)playerIn, server);
            return true;
        }
    	return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
    	TileAdvancementCrafter tile = (TileAdvancementCrafter) world.getTileEntity(pos);

        if (tile != null) {
            tile.breakEvent();
        }

        super.breakBlock(world, pos, state);
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileAdvancementCrafter();
	}
}
