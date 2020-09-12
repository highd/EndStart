package com.highd120.endstart.item;

import com.highd120.endstart.EndStartCreativeTab;
import com.highd120.endstart.util.item.ItemRegister;

import net.minecraft.item.ItemSword;

@ItemRegister(name = "end_stone_sword")
public class ItemEndStoneSword extends ItemSword {

	public ItemEndStoneSword() {
		super(ToolMaterial.STONE);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        this.setMaxDamage(60);
	}

}
