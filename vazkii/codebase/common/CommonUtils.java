package vazkii.codebase.common;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
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

}
