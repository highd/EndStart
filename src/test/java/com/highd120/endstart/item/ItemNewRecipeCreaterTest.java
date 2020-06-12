package com.highd120.endstart.item;

import org.junit.Test;

import net.minecraft.nbt.NBTTagCompound;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ItemNewRecipeCreaterTest {
    @Test
    public void testEmpty() {
        NBTTagCompound tag = new NBTTagCompound();
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", new ArrayList<>());
        assertEquals(result, "{}");
    }

    @Test
    public void testNormalSingle(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("a", "aaa");
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", new ArrayList<>());
        assertEquals(result, "{a:\"aaa\"}");
    }

    @Test
    public void testNormalDouble(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("a", "aaa");
        tag.setFloat("b", 0.3f);
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", new ArrayList<>());
        assertEquals(result, "{a:\"aaa\",b:0.3f}");
    }

    @Test
    public void testNormalHasChild(){
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound child = new NBTTagCompound();
        child.setInteger("c", 100);
        tag.setString("a", "aaa");
        tag.setTag("b", child);
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", new ArrayList<>());
        assertEquals(result, "{a:\"aaa\",b:{c:100}}");
    }

    @Test
    public void testNormalDoubleHasChild(){
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("a", "aab");
        tag.setFloat("b", 0.3f);
        List<String> filter = new ArrayList<>();
        filter.add("b");
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", filter);
        assertEquals(result, "{a:\"aab\"}");
    }

    @Test
    public void testNormalHasChildHasFilter(){
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagCompound child = new NBTTagCompound();
        child.setInteger("c", 100);
        tag.setString("a", "aac");
        tag.setTag("b", child);
        List<String> filter = new ArrayList<>();
        filter.add("b.c");
        String result = ItemNewRecipeCreater.getNbtTagString(tag, "", filter);
        assertEquals(result, "{a:\"aac\"}");
    }
}