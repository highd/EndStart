package com.highd120.endstart.item;

import com.highd120.endstart.util.item.ItemRegister;

@ItemRegister(name = "item_blood")
public class ItemBlood extends ItemHasMeta {

	public ItemBlood() {
		super(new String[] { 
			"red_blood",
			"black_blood",
			"green_blood"
		});
	}

}
