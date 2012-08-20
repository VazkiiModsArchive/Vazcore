package vazkii.codebase.common;

public enum EnumVazkiiMods {
	
	VAZCORE("core", "VazCore"),
	EBON("ebon", "The Ebon Mod"),
	RECALL("recall", "Recall"),
	EASYFPS("easyfps", "EasyFPS"),
	HEALTH_BARS("healthbars", "Health Bars"),
	CHAT_MACROS("chatmacros", "Chat Macros");

	private EnumVazkiiMods(String fileName, String displayName){
		this.fileName = fileName;
		this.displayName = displayName;
	}
	
	private String fileName;
	private String displayName;
	
	public String getFileName(){
		return fileName;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
}
