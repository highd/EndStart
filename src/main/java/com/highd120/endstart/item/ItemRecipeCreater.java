package com.highd120.endstart.item;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.highd120.endstart.util.item.ItemRegister;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;

@ItemRegister(name = "recipe")
public class ItemRecipeCreater extends ItemBase {
	private static Map<String, String> recipes = new HashMap<>();

	private static Map<String, List<String>> nbts = new HashMap<>();

	public static void deleteRecipe(ItemStack item) {
		recipes.remove(createStackCode(item));
		try {
			Gson gson = new Gson();
			new File(Minecraft.getMinecraft().mcDataDir, "tempScript").mkdir();
			Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\tempScript\\temp");
			Files.write(path, Lists.newArrayList(gson.toJson(recipes)));
			path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\scripts\\temp.zs");
			Files.write(path, recipes.values());
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	/**
	 * データのロード。
	 */
	@SuppressWarnings("unchecked")
	public static void load() {
		Gson gson = new Gson();
		try {
			Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\tempScript\\temp");
			String json = String.join("", Files.readAllLines(path));
			recipes = gson.fromJson(json, Map.class);
		} catch (IOException err) {
			err.printStackTrace();
		}
		try {
			Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\tempScript\\nbt.json");
			String json = String.join("", Files.readAllLines(path));
			nbts = gson.fromJson(json, Map.class);
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn,
			BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tile = worldIn.getTileEntity(pos);
		TileEntity tileUp = worldIn.getTileEntity(pos.add(0, 1, 0));
		if (tile != null && tileUp != null && !worldIn.isRemote) {
			NBTTagCompound tag = new NBTTagCompound();
			tile.writeToNBT(tag);
			IItemHandler hadler = getInventory(worldIn, pos.add(0, 1, 0), facing);
			if (tag != null && hadler != null) {
				printInfo(tag, playerIn);
				ItemStack result = hadler.getStackInSlot(0);
				if (result != null) {
					recipes.put(createStackCode(result), createAvaritiaRecipe(result, tag));
				}
			}
			try {
				Gson gson = new Gson();
				new File(Minecraft.getMinecraft().mcDataDir, "tempScript").mkdir();
				Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\tempScript\\temp");
				Files.write(path, Lists.newArrayList(gson.toJson(recipes)));
				path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\scripts\\temp.zs");
				Files.write(path, recipes.values());
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	private static String createAvaritiaRecipe(ItemStack result, NBTTagCompound tag) {
		List<String> codeList = new ArrayList<>();
		for (int y = 0; y < 9; y++) {
			List<String> codeChild = new ArrayList<>();
			for (int x = 0; x < 9; x++) {
				String id = "Craft" + (y * 9 + x);
				if (tag.hasKey(id)) {
					String name = tag.getCompoundTag(id).getString("id");
					int damage = tag.getCompoundTag(id).getInteger("Damage");
					NBTTagCompound childTag = tag.getCompoundTag(id).getCompoundTag("tag");
					String childTagText = createNbtData(childTag, name);
					if (childTagText != null) {
						childTagText = ".onlyWithTag(" + childTagText + ")";
					} else {
						childTagText = "";
					}
					codeChild.add("<" + name + ":" + damage + ">" + childTagText);
				} else {
					codeChild.add("null");
				}
			}
			codeList.add("[" + String.join(",", codeChild) + "]");
		}
		return "mods.avaritia.ExtremeCrafting.addShaped(\r\n"
				+ createStackCode(result) + ",\r\n["
				+ String.join(",\r\n", codeList) + "]);";
	}

	private static String createStackCode(ItemStack stack) {
		String size = stack.stackSize == 1 ? "" : ("*" + stack.stackSize);
		String itemName = stack.getItem().getRegistryName().toString();
		int damage = stack.getItemDamage();
		NBTTagCompound tag = stack.getTagCompound();
		String tagData = createNbtData(tag, itemName);
		if (tagData != null) {
			tagData = ".withTag(" + tagData + ")";
		} else {
			tagData = "";
		}
		return "<" + itemName + ":" + damage + ">" + size + tagData;
	}

	private static String createNbtData(NBTTagCompound tag, String itemName) {
		if (tag == null) {
			return null;
		}
		List<String> whiteList = nbts.get(itemName);
		List<String> nbtData = new ArrayList<>();
		if (whiteList == null) {
			return null;
		}
		for (String white : whiteList) {
			if (tag.hasKey(white)) {
				int data = tag.getInteger(white);
				System.out.println(tag.getString(white));
				nbtData.add(white + ":" + data);
			}
		}
		if (nbtData.isEmpty()) {
			return null;
		}
		return "{" + String.join(",", nbtData) + "}";
	}

	private static IItemHandler getInventory(World world, BlockPos pos, EnumFacing side) {
		TileEntity te = world.getTileEntity(pos);
		if (te == null) {
			return null;
		}
		if (te instanceof TileEntityChest) {
			IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) te);
			if (doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
				return doubleChest;
			}
		}
		IItemHandler ret = te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				? te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				: null;
		if (ret == null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
			ret = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		}
		return ret;
	}

	private static void printInfo(NBTTagCompound tag, EntityPlayer player) {
		String code = tag.toString()
				.replaceAll("\\[", "{")
				.replaceAll("\\]", "}")
				.replaceAll("(\\{|,)([a-zA-Z0-9]+):", "$1\"$2\":")
				.replaceAll("([0-9]+)[a-zA-Z]", "$1");
		player.addChatMessage(new TextComponentString(code));
		Toolkit kit = Toolkit.getDefaultToolkit();
		Clipboard clip = kit.getSystemClipboard();
		StringSelection ss = new StringSelection(code);
		clip.setContents(ss, null);

	}
}
