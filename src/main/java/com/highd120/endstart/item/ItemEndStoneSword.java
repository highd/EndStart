package com.highd120.endstart.item;

import com.highd120.endstart.EndStartCreativeTab;
import net.minecraft.item.ItemSword;

public class ItemEndStoneSword extends ItemSword {

	public ItemEndStoneSword() {
		super(ToolMaterial.STONE);
        setCreativeTab(EndStartCreativeTab.INSTANCE);
        this.setMaxDamage(60);
	}

}
