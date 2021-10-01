package com.highd120.endstart.block.advancementcafter;

import java.util.List;

import com.highd120.endstart.recipe.IRecipeItem;

import lombok.Builder;
import lombok.Data;
import net.minecraft.item.ItemStack;

@Data
@Builder
public class AdvancementCrafterRecipeData {
	private List<String> advancementsList;
	private List<IRecipeItem> inputList;
	private ItemStack output;
	private int minEnergy;
}
