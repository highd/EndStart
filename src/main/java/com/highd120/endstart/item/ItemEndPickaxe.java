package com.highd120.endstart.item;

import java.util.Random;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.util.ItemUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemEndPickaxe extends ItemBase {
	protected static final Random random = new Random();
	public ItemEndPickaxe() {
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        this.maxStackSize = 1;
        this.setMaxDamage(299);
	}

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        if (state.getBlock() == Blocks.END_STONE) {
            return 400;
        }
        return super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos,
    		EntityLivingBase entityLiving) {
        if (!worldIn.isRemote && (double)state.getBlockHardness(worldIn, pos) != 0.0D) {
            stack.damageItem(1, entityLiving);
        }
        ItemStack dust = new ItemStack(ModItems.extra, 1, 17);
        dust.setCount(random.nextInt(4) + 4);
        ItemUtil.dropItem(worldIn, pos, dust);
    	return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

}
