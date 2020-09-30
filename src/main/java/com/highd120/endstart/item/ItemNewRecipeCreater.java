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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.highd120.endstart.util.NbtTagUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaDoubleChestItemHandler;
public class ItemNewRecipeCreater extends ItemBase {
	private static ExtraCraftData craftData;
	public static void load() {
        final Path extraDataPath = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\config\\extraCraft.json");
		try {
            if (Files.exists(extraDataPath)) {
                Gson gson = new Gson();
                final Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\config\\extraCraft.json");
                craftData = gson.fromJson(Files.newBufferedReader(path), ExtraCraftData.class);
            } else {
                craftData = new ExtraCraftData();
                craftData.setNbtFilter(new HashMap<>());
                craftData.setRecipeTemplate(new HashMap<>());
                craftData.setIsPrint(new HashMap<>());
            }
		} catch (final IOException error) {
			error.printStackTrace();
		}
    }

    @SuppressWarnings("unchecked")
	private static void updateDb(Consumer<Map<String, String> > consumer) {
        final Path tmpFilePath = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\tempNewScript");
		try {
            Map<String,String> codeMap = new HashMap<>();
            Gson gson = new Gson();
            if (Files.exists(tmpFilePath)) {
                codeMap = gson.fromJson(Files.newBufferedReader(tmpFilePath), Map.class);
            }
            consumer.accept(codeMap);
            Files.write(tmpFilePath, Lists.newArrayList(gson.toJson(codeMap)));
            Collection<String> script = codeMap.values();
            new File(Minecraft.getMinecraft().mcDataDir, "scripts").mkdir();
            Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\scripts\\extra_new.zs");
            Files.write(path, script);
		} catch (final IOException error) {
			error.printStackTrace();
		}
    }

    public static void deleteRecipe(ItemStack stack) {
        updateDb(map -> map.remove(createItemText(stack)));
    }

    private static IItemHandler getInventory(final World world, final BlockPos pos, final EnumFacing side) {
		final TileEntity tileEntity = world.getTileEntity(pos);
		if (tileEntity == null) {
			return null;
		}
		if (tileEntity instanceof TileEntityChest) {
			final IItemHandler doubleChest = VanillaDoubleChestItemHandler.get((TileEntityChest) tileEntity);
			if (doubleChest != VanillaDoubleChestItemHandler.NO_ADJACENT_CHESTS_INSTANCE) {
				return doubleChest;
			}
		}
		return tileEntity.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				? tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)
				: null;
    }

    private static Pattern elementPattern = Pattern.compile("\\$\\(\\d+,\\d+\\)");
    private static Pattern functionPattern = Pattern.compile("\\$\\((\\d+),(\\d+)\\)");

    private static String createItemText(ItemStack stack) {
        if (stack.isEmpty()) {
            return "null";
        }
        if (stack.getItem() == ModItems.argument) {
            return NbtTagUtil.getString(ItemArgument.TAG, stack).orElse("null");
        }
        final String itemName = stack.getItem().getRegistryName().toString();
        final int meta = stack.getMetadata();
        List<String> nbtFilter = craftData.getNbtFilter().get(itemName + ":" + meta);
        if (nbtFilter == null) {
            nbtFilter = new ArrayList<>();
        }
        final NBTTagCompound tag = stack.getTagCompound();
        String nbtTagString = "";
        if (tag != null) {
            nbtTagString = getNbtTagString(tag, "", nbtFilter);
            if (!nbtTagString.equals("{}")) {
                nbtTagString = ".withTag(" + nbtTagString + ")";
            } else {
                nbtTagString = "";
            }
        }
        return "<" + itemName + ":" + meta + ">" + nbtTagString;
    }

    private String convertRecipeCode(String template, final IItemHandler handler, final int width) {
        final Matcher matcher = elementPattern.matcher(template);
        while (matcher.find()) {
            final String element = matcher.group();
            final Matcher pointMatcher = functionPattern.matcher(element);
            pointMatcher.find();
            final int x = Integer.parseInt(pointMatcher.group(1));
            final int y = Integer.parseInt(pointMatcher.group(2));
            final int slot = y * width + x;
            final ItemStack slotStack = handler.getStackInSlot(slot);
            final String result = createItemText(slotStack);
            template = template.replace(element, result);
        }
        return template;
    }

    @Override
    public EnumActionResult onItemUse(final EntityPlayer playerIn, final World worldIn, final BlockPos pos,
            final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        if (worldIn.isRemote) {
            return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        final IItemHandler handler = getInventory(worldIn, pos, facing);
        if (handler == null) {
            return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        final String blockName = worldIn.getBlockState(pos).getBlock().getRegistryName().toString();
        final IBlockState downState = worldIn.getBlockState(pos.down());
        final String downBlockName = downState.getBlock().getRegistryName().toString();
        final int downBlockMeta = downState.getBlock().getMetaFromState(downState);
        final Integer width = craftData.getWidthMap().get(blockName);
        final String downBlockString = downBlockName + ":" + downBlockMeta;
        final String template = craftData.getRecipeTemplate().get(downBlockString);
        final ItemStack output = handler.getStackInSlot(0);
        if (width == null || template == null) {
            return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        }
        final String code = convertRecipeCode(template, handler, width);
        if (!craftData.getIsPrint().containsKey(downBlockString) || craftData.getIsPrint().get(downBlockString)) {
        	updateDb(map -> map.put(createItemText(output), code));
        } else {
    		Toolkit kit = Toolkit.getDefaultToolkit();
    		Clipboard clip = kit.getSystemClipboard();
    		StringSelection ss = new StringSelection(code);
    		clip.setContents(ss, null);
        }
        return super.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    private static String getNbtTagSubString(final NBTTagCompound tag, final String parent, final String key, final List<String> nbtFilter) {
        final NBTBase child = tag.getTag(key);
        if (child instanceof NBTTagCompound) {
            final String childString = getNbtTagString((NBTTagCompound)child, parent + key + ".", nbtFilter);
            if (childString.equals("{}")) {
                return "";
            }
            return key + ":" + childString;
        }
        return key + ":" + child.toString();
    }

    public static String getNbtTagString(final NBTTagCompound tag, final String parent, final List<String> nbtFilter) {
        if (tag.getSize() == 0) return "{}";
        final String inner = tag.getKeySet().stream()
            .filter(key -> !nbtFilter.contains(parent + key))
            .map(key -> getNbtTagSubString(tag, parent, key, nbtFilter))
            .filter(str -> !str.isEmpty())
            .collect(Collectors.joining(","));
        return "{" + inner + "}";
    }
}