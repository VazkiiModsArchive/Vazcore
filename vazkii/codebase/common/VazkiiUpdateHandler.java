package vazkii.codebase.common;

import vazkii.um.common.UpdateManager;
import vazkii.um.common.UpdateManagerMod;
import cpw.mods.fml.common.Mod;

public abstract class VazkiiUpdateHandler extends UpdateManagerMod {

	public VazkiiUpdateHandler(Mod m) {
		super(m);
	}

	@Override
	public String getModURL() {
		return "http://www.minecraftforum.net/topic/528166-125-forge-vazkiis-mods-1-year-anniversary-last-updated-11712/";
	}

	@Override
	public abstract String getModName();

	@Override
	public abstract String getUMVersion();

	@Override
	public String getSpecialButtonName() {
		return "Twitter";
	}

	@Override
	public void onSpecialButtonClicked() {
		UpdateManager.openWebpage("https://twitter.com/Vazkii");
	}

}
