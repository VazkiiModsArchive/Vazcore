package vazkii.codebase.client.handlers;

import java.util.EnumSet;

import vazkii.codebase.client.CornerText;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {

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

	}

	public void onRenderTick(Object... tickData) {
		CornerText.onTick();
	}

}
