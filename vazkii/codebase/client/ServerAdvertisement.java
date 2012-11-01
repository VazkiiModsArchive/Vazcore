package vazkii.codebase.client;

import java.util.List;

import vazkii.codebase.common.CommonUtils;
import vazkii.codebase.common.IOUtils;
import vazkii.codebase.common.VazcoreReference;
import vazkii.codebase.common.mod_Vazcore;

import net.minecraft.src.ServerData;
import net.minecraft.src.ServerList;

import cpw.mods.fml.relauncher.ReflectionHelper;

public final class ServerAdvertisement {

	static boolean hasInitted = false;
	static boolean added = false;
	public static boolean connected = false;
	public static boolean shownWarning = false;

	public static void init() {
		if (hasInitted) return;

		connected = IOUtils.getTagCompoundInFile(mod_Vazcore.cacheFile).getBoolean("hasConnectedToServer");

		ServerList list = detect();
		if (list != null) {
			list.addServerData(new ServerData(VazcoreReference.VAZKII_MODS_SERVER_NAME, VazcoreReference.VAZKII_MODS_SERVER_IP));
			list.saveServerList();
			added = true;
		}
		hasInitted = true;
	}

	public static boolean canDisplayText() {
		return added;
	}

	static ServerList detect() {
		ServerList list = new ServerList(CommonUtils.getMc());
		List<ServerData> servers = ReflectionHelper.getPrivateValue(ServerList.class, list, 1);
		for (ServerData data : servers)
			if (data.serverIP.equals(VazcoreReference.VAZKII_MODS_SERVER_IP)) return null;
				return list;
	}

}
