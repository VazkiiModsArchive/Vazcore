package vazkii.codebase.common;

public enum EnumVazkiiMods
{

	VAZCORE("core", "VazCore", "vc"), EBON("ebon", "The Ebon Mod", "em"), RECALL("recall", "Recall", "rc"), EASYFPS("easyfps", "EasyFPS", "ef"), HEALTH_BARS("healthbars", "Health Bars", "hb"), CHAT_MACROS("chatmacros", "Chat Macros", "cm"), LAYMAN_MOD_MAKER("lmm", "Layman Mod Maker", "lm");

	private EnumVazkiiMods(String fileName, String displayName, String acronym) {
		this.fileName = fileName;
		this.displayName = displayName;
		this.acronym = acronym;
	}

	private String fileName;
	private String displayName;
	private String acronym;

	public String getFileName() {
		return fileName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getAcronym() {
		return acronym;
	}

}
