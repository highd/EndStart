package com.highd120.endstart.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.highd120.endstart.item.ModItems;
import com.highd120.endstart.util.ItemUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.WorldServer;

public class TileChar extends TileHasInventory {
	public static int INPUT_SLOT_COUNT = 16;
	private BlockChar.State state;
	private BlockChar.Color color;
	private Optional<Integer> oldRecipeIndex = Optional.empty();
    protected Random rand = new Random();
		
	public TileChar() {
		super();
	}

	public TileChar(IBlockState blockState) {
		super();
		state = blockState.getValue(BlockChar.STATE); 
		color = blockState.getValue(BlockChar.COLOR);
	}

	@Override
	public void subReadNbt(NBTTagCompound compound) {
		super.subReadNbt(compound);
		state = BlockChar.State.values()[compound.getInteger("state")];
		color = BlockChar.Color.values()[compound.getInteger("color")];
		if (compound.hasKey("oldRecipeIndex") && compound.getInteger("oldRecipeIndex") != -1) {
			oldRecipeIndex = Optional.of(compound.getInteger("oldRecipeIndex"));
		} else {
			oldRecipeIndex = Optional.empty();
		}
	}
	
	@Override
	public void subWriteNbt(NBTTagCompound compound) {
		super.subWriteNbt(compound);
		compound.setInteger("color", color.ordinal());
		compound.setInteger("state", state.ordinal());
		compound.setInteger("oldRecipeIndex", oldRecipeIndex.orElse(-1));
	}
	
	@Override
	public void update() {
        List<EntityFallingBlock> blocks = getWorld().getEntitiesWithinAABB(EntityFallingBlock.class,
                new AxisAlignedBB(getPos(), getPos().add(1, 2, 1)));
        Block endSand = ModBlocks.endSand;
        blocks.forEach(entity -> {
        	if (entity.getBlock().getBlock() == endSand && entity.getDistanceSq(pos) < 0.64) {
        		entity.setDead();
        		changeSandState();
        	}
        });
	}
	
	public void setOldRecipe(EntityPlayer player, boolean isCreative) {
		oldRecipeIndex.filter(index -> index < CharRecipe.recipes.size()).ifPresent(index -> {
			CharRecipeData recipe = CharRecipe.recipes.get(index);
			recipe.getInputList().forEach(recipeItem -> {
				for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
					ItemStack item = player.inventory.getStackInSlot(i);
					if (ItemUtil.equalItemStackForRecipe(item, recipeItem)) {
						ItemStack clone = item.copy();
						clone.setCount(1);
						boolean result = addItem(clone);
						if (!isCreative && result) {
							item.shrink(1);
						}
						break;
					}
				}
			});
		});
	}
	
	private void changeSandState() {
		IBlockState blockState = world.getBlockState(pos);
		state = BlockChar.State.SAND;
		blockState = blockState.withProperty(BlockChar.STATE, state);
		world.setBlockState(pos, blockState, 3);
		if ( getResult() == ItemStack.EMPTY) {
			CharRecipe.craft(getInputItems(), color).ifPresent(index -> {
				ItemStack item = CharRecipe.recipes.get(index).getOutput();
				oldRecipeIndex = Optional.of(index);
				for (int i = 1; i < INPUT_SLOT_COUNT + 1; i++) {
					itemHandler.setItemStock(i, ItemStack.EMPTY);
				}
				itemHandler.setItemStock(0, item);
			});
		}
	}
	
	public void changeNotmal() {
		IBlockState blockState = world.getBlockState(pos);
		state = BlockChar.State.NORMAL;
		blockState = blockState.withProperty(BlockChar.STATE, state);
		world.setBlockState(pos, blockState, 3);
		int dustCout = rand.nextInt(3) + 1;
		ItemStack dust = new ItemStack(ModItems.extra, dustCout, 17);
		ItemUtil.dropItem(world, pos, dust);
        if (world instanceof WorldServer) {
        	((WorldServer)world).spawnParticle(EnumParticleTypes.BLOCK_CRACK, pos.getX(), pos.getY(), pos.getZ(), 20,
	        		0.5d, 0.5d, 0.5d, 0.05d, 
	        		Block.getStateId(ModBlocks.endSand.getDefaultState()));
        }
	}
	
	@Override
	public void breakEvent() {
		super.breakEvent();
	}
	
	public List<ItemStack> getInputItems() {
		List<ItemStack> inputs = new ArrayList<>();
		for (int i = 1; i < INPUT_SLOT_COUNT + 1; i++) {
			ItemStack stack = itemHandler.getStackInSlot(i);
			if (stack != ItemStack.EMPTY) {
				inputs.add(stack);
			}
		}
		return inputs;
	}
	
	public ItemStack getResult() {
		return itemHandler.getStackInSlot(0);
	}
	
	private Optional<Integer> getLastItemIndex() {
		Optional<Integer> result = Optional.empty();
		for (int i = 0; i < INPUT_SLOT_COUNT + 1; i++) {
			if (itemHandler.getStackInSlot(i) != ItemStack.EMPTY) {
				result = Optional.of(i);
			}
		}
		return result;
	}
	
	private Optional<Integer> getLastEmptyIndex() {
		for (int i = 1; i < INPUT_SLOT_COUNT + 1; i++) {
			if (itemHandler.getStackInSlot(i) == ItemStack.EMPTY) {
				return Optional.of(i);
			}
		}
		return Optional.empty();
	}
	
	private void removeItem() {
		getLastItemIndex().ifPresent(index -> {
			ItemStack stack = itemHandler.getStackInSlot(index);
            ItemUtil.dropItem(world, pos.add(0, 1, 0), stack);
            itemHandler.setStackInSlot(index, ItemStack.EMPTY);
		});
	}
	
	private boolean addItem(ItemStack base) {
		Optional<Integer> indexOp = getLastEmptyIndex();
		indexOp.ifPresent(index -> {
			ItemStack stack = base.copy();
			stack.setCount(1);
            itemHandler.setStackInSlot(index, stack);
		});
		return indexOp.isPresent();
	}
	
	public void changeItem(ItemStack stack, boolean isCreative) {
		if (stack.isEmpty()) {
			removeItem();
		} else {
			boolean success = addItem(stack);
			if (!isCreative && success) {
				stack.shrink(1);
			}
		}		
	}
	
	public static class ItemStackHandler extends SimpleItemStackHandler {
		public ItemStackHandler(TileHasInventory inv) {
			super(inv, INPUT_SLOT_COUNT + 1);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (slot != 0) {
				return ItemStack.EMPTY;
			}
			return super.extractItem(slot, amount, simulate);
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot == 0) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public int getSlotLimit(int slot) {
			return 1;
		}
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new ItemStackHandler(this);
	}

}
