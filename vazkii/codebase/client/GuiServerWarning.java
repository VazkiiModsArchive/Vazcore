package vazkii.codebase.client;

import org.lwjgl.opengl.GL11;

import vazkii.codebase.common.ColorCode;
import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.FormattingCode;
import vazkii.codebase.common.VazcoreReference;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiMultiplayer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.ServerData;

public class GuiServerWarning extends GuiScreen {

	private final static String HEADLINE = ColorCode.DARK_RED + "Server Warning!";
	private final static String[] WARNING = new String[] { ColorCode.RED + "By joining " + ColorCode.INDIGO + VazcoreReference.VAZKII_MODS_SERVER_NAME + " (Vazkii's mods official server)" + ColorCode.RED + " you must", ColorCode.RED + "understand that any actions you carry out that may be against the server rules will", ColorCode.RED + "undoubtedly get you " + FormattingCode.BOLD + "banned" + FormattingCode.RESET + ColorCode.RED + " from both the server and the using of all of Vazkii's Mods.", " ", ColorCode.YELLOW + "Do not ask support questions in the server, go to the threads if you want to do that.", ColorCode.YELLOW + "And no, Vazkii doesn't own this server. The owner is " + FormattingCode.ITALICS + "calebmanley.", " ", ColorCode.AQUA + "When you join the server, a message displaying some survey info on the mods you", ColorCode.AQUA + "have will be displayed, don't worry, it doesn't contain anything malicious.", " ", ColorCode.RED + "You have been warned. Don't complain later.", };

	@Override
	public void initGui() {
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 100, WARNING.length * 12 + 80, "Connect"));
		controlList.add(new GuiButton(1, width / 2 - 100, WARNING.length * 12 + 105, "Cancel"));
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton.id == 0) CommonUtils.getMc().displayGuiScreen(new GuiConnecting(CommonUtils.getMc(), new ServerData(VazcoreReference.VAZKII_MODS_SERVER_NAME, VazcoreReference.VAZKII_MODS_SERVER_IP)));
		else CommonUtils.getMc().displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
		super.actionPerformed(par1GuiButton);
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		GL11.glPushMatrix();
		GL11.glScalef(2.0F, 2.0F, 2.0F);
		fontRenderer.drawStringWithShadow(HEADLINE, width / 4 - fontRenderer.getStringWidth(HEADLINE) / 2, 15, 0xFFFFFF);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
		for (int i = 0; i < WARNING.length; i++)
			fontRenderer.drawString(WARNING[i], width / 2 - fontRenderer.getStringWidth(WARNING[i]) / 2, 60 + i * 12, 0xFFFFFF);
		super.drawScreen(par1, par2, par3);
	}

}
