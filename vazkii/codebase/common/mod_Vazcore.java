package vazkii.codebase.common;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

import updatemanager.common.ModConverter;
import vazkii.codebase.client.handlers.ClientTickHandler;
import vazkii.codebase.common.handlers.VazcoreUpdateHandler;

import net.minecraft.src.NBTTagCompound;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;

@Mod(modid = "a_vazcore_Vaz", name = "Vazcore", version = "by Vazkii. Version [1.0.6] for 1.4.2.")
public class mod_Vazcore {

	public static File cacheFile;
	public static Set<String> loadedVzMods = new TreeSet();

	@PreInit
	public void onPreInit(FMLPreInitializationEvent event) {
		IOUtils.initFiles();
	}

	@Init
	public void onInit(FMLInitializationEvent event) {
		if (CommonUtils.getSide().isClient()) clientInit();
		else serverInit();

		new VazcoreUpdateHandler(ModConverter.getMod(getClass()));
	}

	public void clientInit() {
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		vazkii.codebase.client.SecurityManager.validate();
		cacheFile = IOUtils.getCacheFile(EnumVazkiiMods.VAZCORE);
		NBTTagCompound cmp = IOUtils.getTagCompoundInFile(cacheFile);
		if (!cmp.hasKey("hasConnectedToServer")) cmp.setBoolean("hasConnectedToServer", false);
		IOUtils.injectNBTToFile(cmp, cacheFile);
	}

	public void serverInit() {

	}
}
