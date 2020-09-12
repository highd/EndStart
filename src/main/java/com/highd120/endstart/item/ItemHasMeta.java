package com.highd120.endstart.item;

import javax.annotation.Nonnull;

import com.highd120.endstart.EndStartMain;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHasMeta extends ItemBase {
	private String[] meteNameList;

	public ItemHasMeta(String[] meteNameList) {
		this.meteNameList = meteNameList;
		setHasSubtypes(true);
	}

	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(!isInCreativeTab(tab)) {
			return;
		}
		for (int i = 0; i < meteNameList.length; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + EndStartMain.MOD_ID + "." + meteNameList[Math.min(meteNameList.length - 1,
				par1ItemStack.getItemDamage())];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (int i = 0; i < meteNameList.length; i++) {
			String location = EndStartMain.MOD_ID + ":" + meteNameList[i];
			ModelLoader.setCustomModelResourceLocation(this, i,
					new ModelResourceLocation(location, "inventory"));
		}
	}
}
