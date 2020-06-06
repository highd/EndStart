package com.highd120.endstart.item;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.highd120.endstart.util.item.ItemRegister;

import org.yaml.snakeyaml.Yaml;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

@ItemRegister(name = "new_recipe")
public class ItemNewRecipeCreater extends ItemBase {
	private static ExtraCraftData craftData;
	public static void load() {
        final Path extraDataPath = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\config\\extraCraft.yaml");
		try {
            if (Files.exists(extraDataPath)) {
                Yaml yaml = new Yaml();
                Path path = Paths.get(Minecraft.getMinecraft().mcDataDir + "\\config\\extraCraft.yaml");
                craftData = yaml.loadAs(Files.newBufferedReader(path), ExtraCraftData.class);
            } else {
                craftData = new ExtraCraftData();
                craftData.setNbtFilter(new HashMap<>());
                craftData.setRecipeTemplate(new HashMap<>());
            }
		} catch (IOException error) {
			error.printStackTrace();
		}
    }

    private static String getNbtTagSubString(NBTTagCompound tag, String parent, String key, List<String> nbtFilter) {
        NBTBase child = tag.getTag(key);
        if (child instanceof NBTTagCompound) {
            String childString = getNbtTagString((NBTTagCompound)child, parent + key + ".", nbtFilter);
            if (childString.equals("{}")) {
                return "";
            }
            return key + ":" + childString;
        }
        return key + ":" + child.toString();
    }

    public static String getNbtTagString(NBTTagCompound tag, String parent, List<String> nbtFilter) {
        if (tag.getSize() == 0) return "{}";
        String inner = tag.getKeySet().stream()
            .filter(key -> !nbtFilter.contains(parent + key))
            .map(key -> getNbtTagSubString(tag, parent, key, nbtFilter))
            .filter(str -> !str.isEmpty())
            .collect(Collectors.joining(","));
        return "{" + inner + "}";
    }
}