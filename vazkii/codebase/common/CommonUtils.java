package vazkii.codebase.common;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import vazkii.um.common.UpdateManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;

public class CommonUtils {

	public static boolean isOnline() {
		return UpdateManager.online;
	}

	public static Minecraft getMc() {
		return Minecraft.getMinecraft();
	}

	public static MinecraftServer getServer() {
		return MinecraftServer.getServer();
	}

	public static Side getSide() {
		return FMLCommonHandler.instance().getSidedDelegate().getSide();
	}

	public static int parseHexString(String stringToParse) {
		return Integer.parseInt(stringToParse, 16);
	}

	public static boolean flipBoolean(boolean b) {
		return !b;
	}

	public static String getEntityName(Entity entity) {
		return EntityList.getEntityString(entity);
	}

	public static <T> LinkedList<T> listOf(T instance) {
		LinkedList<T> l = new LinkedList();
		l.add(instance);
		return l;
	}
}
