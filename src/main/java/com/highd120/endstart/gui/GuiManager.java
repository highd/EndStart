package com.highd120.endstart.gui;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import com.highd120.endstart.EndStartMain;
import com.highd120.endstart.block.advancementcafter.TileAdvancementCrafter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * GUIのマネージャー。
 * @author hdgam
 */
public class GuiManager implements IGuiHandler {

	/**
	 * GUIを開く。
	 * @param player プレイヤー。
	 * @param world ワールド。
	 * @param hand イベントを起こした手。
	 * @param guiId GUIのID。
	 */
	public static void playerOpednGui(EntityPlayer player, World world, EnumHand hand,
			int guiId) {
		if (hand != EnumHand.MAIN_HAND) {
			return;
		}
		player.openGui(EndStartMain.instance, guiId, world, (int) player.posX,
				(int) player.posY, (int) player.posZ);
	}

	private static boolean isHaveAnnotation(Annotation[] annotations, Class<?> clazz) {
		return Arrays.stream(annotations).anyMatch(an -> an.annotationType().equals(clazz));
	}

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y,
			int z) {
		return new ContainerAdvancementCrafter(player.inventory, world, new BlockPos(x, y, z),
				(TileAdvancementCrafter) world.getTileEntity(new BlockPos(x, y, z)));
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y,
			int z) {
		return new GuiAdvancementCrafter(player.inventory, world, new BlockPos(x, y, z),
				(TileAdvancementCrafter) world.getTileEntity(new BlockPos(x, y, z)));
	}
}