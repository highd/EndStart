package com.highd120.endstart.block.advancementcafter;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import net.minecraft.item.ItemStack;

@Data
@Builder
public class AdvancementCrafterRecipeData {
	private List<String> advancementsList;
	private List<ItemStack> inputList;
	private ItemStack output;
	private int minEnergy;
}
