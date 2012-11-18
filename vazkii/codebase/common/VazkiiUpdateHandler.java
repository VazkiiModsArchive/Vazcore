package vazkii.codebase.common;

import updatemanager.common.UpdateManager;
import updatemanager.common.UpdateManagerMod;
import updatemanager.common.checking.CheckingMethod;

import cpw.mods.fml.common.Mod;

public abstract class VazkiiUpdateHandler extends UpdateManagerMod {

	public VazkiiUpdateHandler(Mod m) {
		super(m);
	}

	@Override
	public String getModURL() {
		return VazcoreReference.VAZKII_MODS_URL;
	}

	@Override
	public CheckingMethod getCheckingMethod() {
		return CheckingMethod.LEXICOGRAPHICAL;
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
