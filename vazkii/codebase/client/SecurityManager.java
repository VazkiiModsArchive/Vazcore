package vazkii.codebase.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import updatemanager.common.UpdateManager;
import vazkii.codebase.common.CommonUtils;

import com.google.common.collect.ImmutableList;

import net.minecraft.src.Session;

public final class SecurityManager {

	private static ImmutableList<String> illegalUsernames;

	private static ImmutableList<String> getIllegalUsernames() {
		if (!UpdateManager.online) return ImmutableList.of();
		try {
			final String BLACKLIST_URL = "https://dl.dropbox.com/u/34938401/Mods/BLACKLIST.txt";
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(BLACKLIST_URL).openStream()));
			List<String> names = new LinkedList();
			String line = null;
			while ((line = bufferedReader.readLine()) != null)
				names.add(line);
			return ImmutableList.copyOf(names);
		} catch (IOException e) {
			e.printStackTrace();
			return ImmutableList.of();
		}
	}

	public static void validate() {
		if (illegalUsernames == null) illegalUsernames = getIllegalUsernames();

		Session session = CommonUtils.getMc().session;
		if (illegalUsernames.contains(session.username)) throw new IllegalArgumentException(String.format("Player %s is banned from Vazkii's Mods. Go away.", session.username));
	}

}
