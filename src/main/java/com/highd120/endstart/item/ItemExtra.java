package com.highd120.endstart.item;

import com.highd120.endstart.util.item.ItemRegister;

@ItemRegister(name = "extra")
public class ItemExtra extends ItemHasMeta {

	public ItemExtra() {
		super(new String[] { "ex_core", "life_core", "fire", "water", "shulker_shell" });
	}

}
