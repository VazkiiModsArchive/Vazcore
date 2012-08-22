package vazkii.codebase.client;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;

import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.VazcoreReference;

public final class CornerText {

	private static List<ICornerTextHandler> cornerTextHandlers = new LinkedList();

	public static boolean registerCornerTextHandler(ICornerTextHandler handler) {
		return cornerTextHandlers.add(handler);
	}

	private static List<CornerTextEntry> updateCornerText(float partialTicks) {
		List<CornerTextEntry> foundEntries = new LinkedList();

		for (ICornerTextHandler handler : cornerTextHandlers) {
			List<CornerTextEntry> handlerEntries = handler.updateCornerText(partialTicks);

			if (handlerEntries != null && !handlerEntries.isEmpty()) foundEntries.addAll(handlerEntries);
		}

		return foundEntries;
	}

	public static void onTick(float partialTicks) {
		Minecraft mc = CommonUtils.getMc();

		int y = 2;
		int x = 2;

		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (CornerTextEntry entry : updateCornerText(partialTicks)) {
			mc.fontRenderer.drawStringWithShadow(entry.text, x, y, entry.color);
			y += VazcoreReference.CORNER_TEXT_ENTRY_SIZE;
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

}
