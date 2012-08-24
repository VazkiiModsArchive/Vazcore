package vazkii.codebase.common;

import java.util.LinkedList;

import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityList;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet3Chat;
import net.minecraftforge.common.EnumHelper;
import vazkii.um.common.UpdateManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.relauncher.ReflectionHelper;

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
	
	public static void registerNewToolMaterial(String name, int harvestLevel, int maxUses, float efficiencyOnProperMaterial, int damageVsEntity, int enchantability){
		EnumHelper.addToolMaterial(name, harvestLevel, maxUses, efficiencyOnProperMaterial, damageVsEntity, enchantability);
	}
	
	public static void registerNewArmorMaterial(String name, int maxDamageFactor, int[] damageReductionAmounts, int enchantability) {
		EnumHelper.addArmorMaterial(name, maxDamageFactor, damageReductionAmounts, enchantability);
	}
	
	public static <C extends Enum> C getEnumConstant(String name, Class<? extends C> clazz) {
		for(C constant : clazz.getEnumConstants()){
			if(constant.name().matches(name))
				return constant;
		}
	
		return null;
	}
	
	public static void sendChatMessage(EntityPlayer player, String message) {
		sendChatMessage(player, message, false);
	}
	
	public static void sendChatMessage(EntityPlayer player, String message, boolean op) {
		if(!op || getServer().getConfigurationManager().areCommandsAllowed(player.username)){
			Packet3Chat chatPacket = new Packet3Chat(message);
			EntityPlayerMP mpPlayer = getServer().getConfigurationManager().getPlayerForUsername(player.username);
					
			if (player != null) mpPlayer.serverForThisPlayer.sendPacketToPlayer(chatPacket);
		}
	}

}
