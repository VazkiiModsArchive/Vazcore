package vazkii.codebase.client.handlers;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import vazkii.codebase.client.CornerText;
import vazkii.codebase.client.GuiServerWarning;
import vazkii.codebase.client.ServerAdvertisement;
import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.IOUtils;
import vazkii.codebase.common.VazcoreReference;
import vazkii.codebase.common.mod_Vazcore;
import net.minecraft.client.Minecraft;

import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.ServerData;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientTickHandler implements ITickHandler {

	public static int clientTicksElapsed = 0;
	public static int renderTicksElapsed = 0;

	static int serverWarnTicksElapsed = 0;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if (type.equals(EnumSet.of(TickType.RENDER))) onRenderTick(tickData);
		else if (type.equals(EnumSet.of(TickType.CLIENT))) onClientTick(tickData);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.RENDER, TickType.CLIENT);
	}

	@Override
	public String getLabel() {
		return "VazCore_Client";
	}

	public void onClientTick(Object... tickData) {
		++clientTicksElapsed;
	}

	public void onRenderTick(Object... tickData) {
		++renderTicksElapsed;

		CornerText.onTick((Float) tickData[0]);

		Minecraft mc = CommonUtils.getMc();
		GuiScreen screen = mc.currentScreen;
		if (screen != null && screen instanceof GuiMainMenu) {
			ServerAdvertisement.init();
			if (ServerAdvertisement.canDisplayText() && ++serverWarnTicksElapsed <= 1600) {
				int x = screen.width / 2 + 105;
				int y = screen.height / 4 + 54;
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, .6F);
				screen.drawString(mc.fontRenderer, ColorCode.RED + "< " + ColorCode.BRIGHT_GREEN + VazcoreReference.VAZKII_MODS_SERVER_NAME + " (Vazkii's Mods Official Server)", x, y + 12, 0xFFFFFF);
				screen.drawString(mc.fontRenderer, ColorCode.RED + "< " + ColorCode.BRIGHT_GREEN + "was added to the servers list. Thanks", x, y + 24, 0xFFFFFF);
				screen.drawString(mc.fontRenderer, ColorCode.RED + "< " + ColorCode.BRIGHT_GREEN + "for using Vazkii's Mods! :D", x, y + 36, 0xFFFFFF);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}

		connecting: {
			if (screen != null && screen instanceof GuiConnecting) {
				if (ServerAdvertisement.shownWarning || ServerAdvertisement.connected) break connecting;

				GuiConnecting conn = (GuiConnecting) screen;
				ServerData data = mc.getServerData();
				if (data.serverIP.equals(VazcoreReference.VAZKII_MODS_SERVER_IP)) {
					NetClientHandler handler = ReflectionHelper.<NetClientHandler, GuiConnecting> getPrivateValue(GuiConnecting.class, conn, 0);
					ReflectionHelper.setPrivateValue(GuiConnecting.class, conn, true, 1);
					if (handler != null) handler.disconnect();
					mc.displayGuiScreen(new GuiServerWarning());
					ServerAdvertisement.shownWarning = true;
				}
			}
		}

		if (mc.theWorld != null && mc.getServerData() != null && mc.getServerData().serverIP.equals(VazcoreReference.VAZKII_MODS_SERVER_IP) && !ServerAdvertisement.connected) {
			String moddata = "";
			int i = 0;
			for (String s : mod_Vazcore.loadedVzMods) {
				++i;
				moddata = moddata.concat(s);
				if (i != mod_Vazcore.loadedVzMods.size()) moddata = moddata.concat(", ");
			}
			mc.thePlayer.sendChatMessage(String.format("[VazkiiBot] moddata = [%s]", moddata));

			NBTTagCompound cmp = IOUtils.getTagCompoundInFile(mod_Vazcore.cacheFile);
			cmp.setBoolean("hasConnectedToServer", true);
			IOUtils.injectNBTToFile(cmp, mod_Vazcore.cacheFile);
			ServerAdvertisement.connected = true;
		}

	}

}
