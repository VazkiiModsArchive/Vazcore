package vazkii.codebase.client.handlers;

import java.util.EnumSet;

import vazkii.codebase.client.CornerText;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

	public static int clientTicksElapsed = 0;
	public static int renderTicksElapsed = 0;
	
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {}

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
		
		CornerText.onTick();
	}

}
