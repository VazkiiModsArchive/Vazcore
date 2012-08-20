package vazkii.codebase.common.handlers;

import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import vazkii.codebase.common.VazcoreReference;
import vazkii.codebase.common.VazkiiUpdateHandler;
import vazkii.um.client.ModType;
import cpw.mods.fml.common.Mod;

public class VazcoreUpdateHandler extends VazkiiUpdateHandler {

	public VazcoreUpdateHandler(Mod m) {
		super(m);
	}

	@Override
	public String getModName() {
		return "VazCore";
	}

	@Override
	public String getUpdateURL() {
		return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/VazCore/Version.txt";
	}

	@Override
	public String getChangelogURL() {
		return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/VazCore/Changelog.txt";
	}

	@Override
	public String getUMVersion() {
		return VazcoreReference.VERSION;
	}

	@Override
	public ModType getModType() {
		return ModType.CORE;
	}

	@Override
	public ItemStack getIconStack() {
		return new ItemStack(Block.enchantmentTable);
	}

}
