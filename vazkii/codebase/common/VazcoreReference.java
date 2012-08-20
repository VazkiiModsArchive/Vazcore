package vazkii.codebase.common;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class VazcoreReference {

	public static String VERSION = "1.0";
	@SideOnly(Side.CLIENT) public static int TWITTER_THREAD_SLEEP_TIME = 1800;

}
