package net.minecraft.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import net.minecraft.client.Minecraft;

import vazkii.um.UpdateManagerMod;

public class mod_ChatMacros extends BaseMod {

	static HashMap<String, String> macros = new HashMap();
	static List<String> macrosList = new LinkedList();
	
	static char macroCharStart = '{';
	static char macroCharEnd = '}';
	
	public static boolean macrosEnabled = true;
	public static boolean closeOnFinish = true;
	
	public String getVersion() {
		return "by Vazkii. Version [1.1.3] for 1.2.5";
	}

	public void load() {
		ModLoader.setInGUIHook(this, true, false);
		File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "Chat Macros.txt");
			try {
				if(!f.exists()){
					f.createNewFile();
					writeInitialFile(f);
				}
				readMacros(f);
				getProps();
			} catch (IOException e) {
			}
		new UpdateHandler(this);
	}
	
	public boolean onTickInGUI(float f, Minecraft mc, GuiScreen g){
		if(g.getClass() == GuiChat.class)
			mc.displayGuiScreen(new GuiChatMacros((GuiChat)g));
		return true;
	}
	
	public static void readMacros(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		int macroCount = 0; 
        List<String> macroList = new ArrayList<String>();
        String line = null;
        
		System.out.println("###############################################################");
		System.out.println("Reading Chat Macros at " + f.getAbsolutePath());
        
        while ((line = reader.readLine()) != null){
        	if(!line.startsWith("#"))
                macroList.add(line);
        }
        
        for(String macro : macroList){
        	String [] tokens = macro.split("»");
        	if(tokens.length == 2){
        		tokens[0] = tokens[0].replaceAll("	", "");
        		tokens[1] = tokens[1].replaceAll("	", "");
        		if(tokens[0].equalsIgnoreCase("macros") || tokens[0].equalsIgnoreCase("reload") || tokens[0].equalsIgnoreCase("help") || tokens[0].equalsIgnoreCase("add")) continue;
        		
        		if(tokens[0].equalsIgnoreCase("macrocharstart")){
        			macroCharStart = tokens[1].charAt(0);
        			continue;
        		}
            	if(tokens[0].equalsIgnoreCase("macrocharend")){
            		macroCharEnd = tokens[1].charAt(0);
            		continue;
            	}
            		macros.put(tokens[0], tokens[1]);
            		if(!macrosList.contains(tokens[0]))
            		macrosList.add(tokens[0]);
            		System.out.println("Put Macro: '" + tokens[0] + "' into '" + tokens[1] + "' at index " + macroCount + ".");
            		macroCount++;
        	}
        } 
		System.out.println("Finished reading Chat Macros: " + macroCount + " Macros located.");
		System.out.println("###############################################################");
	}
	
	public void writeInitialFile(File f) throws IOException {
		System.out.println("Writing Chat Macro file at " + f.getAbsolutePath());
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		
		writer.write("### Chat Macros Mod.\r");
		writer.write("### http://bit.ly/s6rL8h\r");
		writer.write("#\r");
		writer.write("# This is the Chat Macros config file, below you'll see configurations and\r");
		writer.write("# an example macro you can take reference to.\r");
		writer.write("#\r");
		writer.write("# This is the character to begin macros with:\r");
		writer.write("	macroCharStart	»	{\r");
		writer.write("# This is the character to finish macros with:\r");
		writer.write("	macroCharEnd	»	}\r");
		writer.write("# Here is an example macro to help you get started, you can copy it and make more:\r");
		writer.write("# Note that the character '»' is splitting the macro name from the macro itself.\r");
		writer.write("	modpage	»	http://bit.ly/s6rL8h\r");
		writer.write("# Or you could add in a code to add variables to the macros:\r");
		writer.write("# name	»	¿name\r");
		writer.write("# This would type in your username to the chat, but there are more variable codes, here's a list of all of them:\r");
		writer.write("#\r");
		writer.write("# 	¿xpos : Displays your X coordinate;\r");
		writer.write("# 	¿ypos : Displays your Y coordinate;\r");
		writer.write("# 	¿zpos : Displays your Z coordinate;\r");
		writer.write("# 	¿perd : Displays a random number between 1 and 100;\r");
		writer.write("# 	¿pert : Displays a random number between 1 and 1000;\r");
		writer.write("# 	¿dice : Displays a random number between 1 and 6;\r");
		writer.write("# 	¿dim : Displays your dimension;\r");
		writer.write("# 	¿armor : Displays your armor level;\r");
		writer.write("# 	¿food : Displays your food level;\r");
		writer.write("# 	¿hp : Displays your health;\r");
		writer.write("# 	¿fps : Displays your framerate;\r");
		writer.write("# 	¿ping : Displays your ping with the server;\r");
		writer.write("# 	¿names : Displays the names of all the players on the server;\r");
		writer.write("# 	¿name : Displays your username;\r");
		writer.write("# 	¿lvl : Displays your level;\r");
		writer.write("# 	¿item : Displays the item you're wielding;\r");
		writer.write("# 	¿block : Displays the block you are standing on;\r");
		writer.write("# 	¿players : Displays the amount of players on the server;\r");
		writer.write("# 	¿prand : Displays the username of a random player on the server;\r");
		writer.write("# 	¿score : Displays your score (The one that gets shown when you die);\r");
		writer.write("# 	¿sysdate : Displays the System Date;\r");
		writer.write("# 	¿systime : Displays the System Time;\r");
		writer.write("# 	¿timezone : Displays your Timezone;\r");
		writer.write("# 	¿tex : Displays the name of the Texture Pack you're using;\r");
		writer.write("# 	¿enchants : Displays the Enchantments on the item you're wielding;\r");
		writer.write("# 	¿java : Displays the your version of Java.;\r");
		writer.write("# 	¿os : Displays your Operating System;\r");
		writer.write("# 	¿mods : Displays the amount of mods you have loaded;\r");
		writer.write("# 	¿count : Displays the amount of macros you have;\r");
		writer.write("# 	¿potions : Displays the active potion effects on you and their times.\r");
		writer.write("#\r");
		writer.write("# Typing ¶ in the macro will split the message in various messages at that point, keep that in mind.\r");
		writer.write("# when making long macros.\r");
		writer.write("#\r");
		writer.write("# To use a macro just type in the macro start character, the name of the macro and the macro end character.\r");
		writer.write("# In the chat menu, you can press ALT to toggle macros, and you can press RCONTROL to toggle the way the chat\r");
		writer.write("# window reacts to you pressing ENTER, be that closing the GUI or just clearing the message.\r");
		writer.write("#\r");
		writer.write("# There's also four prebuilt macros, one named 'macros' that will display the macros you have, one named\r");
		writer.write("# 'reload', that will reload your macros so you don't have to close your minecraft everytime you change this file,\r");
		writer.write("# one named 'add' that will open a interface to add macros in-game, and one called 'help', that will give you,\r");
		writer.write("# information about a macro.\r");
		writer.write("#\r");
		writer.write("# Example, with the default settings:\r");
		writer.write("# Typing in {modpage} would display on the chat as http://bit.ly/s6rL8h.\r");
		writer.write("#\r");
		writer.write("# TIP: The TAB character gets nulled in the file parsing, so you can use if for convenience!\r");
		writer.write("#\r");
		writer.write("# TIP 2: All lines starting with '#' don't get parsed so you can use them as guidelines.\r");
		writer.write("#\r");
		writer.write("# Place your macros here:\r");
		writer.write("# Codes for Convenience: » ¿ ¶\r");
		writer.write("	");
		writer.close();
	}

	public static String messWithMessage(String message) {
		if(message.contains("¿")) return parseMacro(message);
		
		if(!message.startsWith(Character.toString(macroCharStart)) || !message.endsWith(Character.toString(macroCharEnd)))
			return message;
		String newMessage = message;
		
		newMessage = newMessage.substring(1, newMessage.length()-1);
		if(newMessage.matches("macros")){
			String allMacros = "Macro List: ";
			for(String macro : macrosList)
				allMacros =	allMacros.concat(macro).concat(", ");
			allMacros = allMacros.concat("macros, reload, add, help(macroname).");
					
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(allMacros);
			return null;
		}
		
		if(newMessage.matches("reload")){
			try {
				reloadMacros();
				ModLoader.getMinecraftInstance().thePlayer.addChatMessage("§aMacros Reloaded!");
			} catch (IOException e) {
				e.printStackTrace();
				ModLoader.getMinecraftInstance().thePlayer.addChatMessage("§cFailed to reload Macros!");
			}
			return null;
		}
		
		if(newMessage.matches("add")){
			return "-[jus'jx.,s@y7fe™"; //The ™ sign is in there so you can't easilly replicate the mesasge
		}
		
		if(newMessage.matches("help")){
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage("§cThat's not how you use the help macro, try help'modpage'.");
			return null;
		}
		
		if(newMessage.startsWith("help'") && newMessage.endsWith("'")){
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(parseHelpMacro(newMessage));
			return null;
		}
		
		if(macros.containsKey(newMessage)){
			String macro = macros.get(newMessage);
			String parsedMacro;
			if(macro.contains("¿")){
				parsedMacro = parseMacro(macros.get(newMessage));
				if(macro.contains("¶")) return parsedMacro;
				return parsedMacro.length() == 0 || parsedMacro.length() > 100 ? null : parsedMacro;
			}
			
			String newMacro = macros.get(newMessage);
			if(macro.contains("¶")) return newMacro;
			return newMacro.length() == 0 || newMacro.length() >  100 ? null : newMacro;
		}
		
		ModLoader.getMinecraftInstance().thePlayer.addChatMessage("§cThat macro doesn't exist.");
		return null;
	}
	
	public static void setProps() throws IOException{
		File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "macroProps.dat");
		if(!f.exists()) f.createNewFile();
		
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("macrosEnabled", macrosEnabled);
		nbt.setBoolean("closeOnFinish", closeOnFinish);
		
		CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(f));
	}
	
	public static void getProps() throws IOException {
		File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "macroProps.dat");
		if(!f.exists()) return;
		
		NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(f));
		if(!nbt.hasKey("macrosEnabled")) return;
		
		macrosEnabled = nbt.getBoolean("macrosEnabled");
		closeOnFinish = nbt.getBoolean("closeOnFinish");
	}
	
	public static String parseHelpMacro(String helpMacro){
		String s = helpMacro;
		if(helpMacro == "help'help'") return "help'macroname' » Tells you what the macro in modname does. Prebuilt.";
		String macroToCheck = s.replaceAll("help", "").replaceAll("'", "");

		if(macroToCheck.equalsIgnoreCase("add"))
			return "add » Opens the Add Macro GUI so you can add macros in-game. Prebuilt.";
		if(macroToCheck.equalsIgnoreCase("reload")) 
			return "reload » Reloads the list of macros. Prebuilt.";
		if(macroToCheck.equalsIgnoreCase("macros")) 
			return "macros » Shows all the loaded macros. Prebuilt.";
		
		if(!macrosList.contains(macroToCheck)) return "§c That Macro doesn't exist, couldn't check for it.";
		return macroToCheck + " » " + macros.get(macroToCheck);
	}
	
	public static boolean addMacroRemotely(String name, String contents){
		if(macrosList.contains(name) || name.isEmpty() || contents.isEmpty()) return false;	
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "Chat Macros.txt"), true));
			writer.write("\r" + name + "	»	" + contents);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean isMacroValid(String name, String contents){
		return !((name.equalsIgnoreCase("macros") || name.equalsIgnoreCase("reload") || name.equalsIgnoreCase("help") || name.equalsIgnoreCase("add"))) &&  !macrosList.contains(name) && !name.isEmpty() && !contents.isEmpty();
	}
	
	public static String parseMacro(String macro){
		String s = macro;
		
		EntityPlayer p = ModLoader.getMinecraftInstance().thePlayer;
		Random rand = new Random();
		
		s = s.replaceAll("¿xpos", (int)Math.floor(p.posX)+"");
		s = s.replaceAll("¿ypos", (int)Math.floor(p.posY)+"");
		s = s.replaceAll("¿zpos", (int)Math.floor(p.posZ)+"");
		s = s.replaceAll("¿perc", rand.nextInt(100)+"");
		s = s.replaceAll("¿pert", rand.nextInt(1000)+"");
		s = s.replaceAll("¿dice", (rand.nextInt(6)+1)+"");
		s = s.replaceAll("¿dim", p.dimension+"");
		s = s.replaceAll("¿armor", p.getTotalArmorValue()+"");
		s = s.replaceAll("¿food", p.foodStats.getFoodLevel()+"");
		s = s.replaceAll("¿hp", p.health+"");
		s = s.replaceAll("¿fps", getFps()+"");
		s = s.replaceAll("¿ping", getPing()+"");
		s = s.replaceAll("¿names", getPlayerNames(p.worldObj));
		s = s.replaceAll("¿name", p.username);
		s = s.replaceAll("¿lvl", p.experienceLevel+"");
		s = s.replaceAll("¿item", getHeldItem(p));
		s = s.replaceAll("¿block", getBlockStanding(p));
		s = s.replaceAll("¿players", p.worldObj.playerEntities.size()+"");
		s = s.replaceAll("¿prand", pickRandomPlayer(p.worldObj.playerEntities));
		s = s.replaceAll("¿score", p.score+"");
		s = s.replaceAll("¿sysdate", (new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
		s = s.replaceAll("¿systime", (new SimpleDateFormat("HH:mm").format(new Date())));
		s = s.replaceAll("¿timezone", Calendar.getInstance().getTimeZone().getDisplayName());
		s = s.replaceAll("¿tex", (ModLoader.getMinecraftInstance().texturePackList.selectedTexturePack.texturePackFileName.replaceAll(".zip", "")));
		s = s.replaceAll("¿enchants", getEnchants(p.getCurrentEquippedItem()));
		s = s.replaceAll("¿java", System.getProperty("java.version"));
		s = s.replaceAll("¿os", System.getProperty("os.name"));
		s = s.replaceAll("¿mods", ModLoader.getLoadedMods().size()+"");
		s = s.replaceAll("¿count", macrosList.size()+"");
		s = s.replaceAll("¿potions", getPotions(p));
		
		return s;
	}
	
	static int getFps(){
        String debug = ModLoader.getMinecraftInstance().debug;
        String s = "[( f]+";
        String[] s1 = debug.split(s);
        return  Integer.parseInt(s1[0]);
	}
	
	static int getPing(){
        String username = ModLoader.getMinecraftInstance().thePlayer.username;
        String nameToCheck = "nil";
        NetClientHandler player = ((EntityClientPlayerMP)ModLoader.getMinecraftInstance().thePlayer).sendQueue;
        List list = player.playerInfoList;
        Iterator iterator = list.iterator();
        GuiPlayerInfo info = null;
        int time = -1337;
        while (iterator.hasNext())
        {
            time = -1337;
            info = (GuiPlayerInfo)iterator.next();
            nameToCheck = info.name;
            if (nameToCheck.matches(username))
                time = info.responseTime;
            if (time != -1337)
                return time;
        }
        return -1;
	}
	
	static String getHeldItem(EntityPlayer p){
		ItemStack stack = p.getCurrentEquippedItem();
		if(stack == null)
			return "Air";
		
		return stack.getItem().getItemDisplayName(stack);
	}
	
	static String getBlockStanding(EntityPlayer p){
		World w = p.worldObj;
		ItemStack stack = new ItemStack(w.getBlockId((int)Math.floor(p.posX), (int)Math.floor(p.posY-2), (int)Math.floor(p.posZ)), 1, w.getBlockMetadata((int)Math.floor(p.posX), (int)Math.floor(p.posY-2), (int)Math.floor(p.posZ)));
		
		if(stack.itemID == 0)
			return "Air";
		
		return stack.getItem().getItemDisplayName(stack);
	}
	
	static String pickRandomPlayer(List list){
		int i = list.size();
		Random r = new Random();
		int index = r.nextInt(i);
		
		return ((EntityPlayer)list.get(index)).username;
	}
	
	static String getEnchants(ItemStack stack){
		if(stack == null)return "null";
		if(stack.isItemEnchanted()){
			NBTTagList nbt = stack.getEnchantmentTagList();
			String enchs = "";
			
			for(int i=0; i<nbt.tagCount(); i++){
				NBTTagCompound cp = (NBTTagCompound)nbt.tagAt(i);
				enchs = enchs.concat(Enchantment.enchantmentsList[cp.getShort("id")].getTranslatedName(cp.getShort("lvl")) + ", ");
			}
			if(enchs.endsWith(", "))
				return enchs.substring(0, enchs.length()-2);
			return enchs;
		}else return "null";
	}
	
	static String getPlayerNames(World world){
		List<EntityPlayer> l = world.playerEntities;
		String playerList = "";
		
		for(EntityPlayer e : l)
			playerList = playerList.concat(e.username + ", ");
		if(playerList.endsWith(", "))
			return playerList.substring(0, playerList.length()-2);
		return playerList;
	}
	
	static String getPotions(EntityLiving e){
		Collection<PotionEffect> l = e.getActivePotionEffects();
		String effects = "";
		
		if(l.size() == 0) return "null";
		
		for(PotionEffect p : l)
			effects = effects.concat(StatCollector.translateToLocal(p.getEffectName()) + " " + getPotionDuration(p) + ", ");
				
		if(effects.endsWith(", "))
			return effects.substring(0, effects.length()-2);
		return effects;
	}
	
	static String getPotionDuration(PotionEffect effect){
        int var1 = effect.getDuration();
        int var2 = var1 / 20;
        int var3 = var2 / 60;
        var2 %= 60;
        return var2 < 10 ? var3 + ":0" + var2 : var3 + ":" + var2;
	}
	
	public static void reloadMacros() throws IOException{
		macros = null;
		macros = new HashMap();
		macrosList.clear();	
		File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "Chat Macros.txt");
		readMacros(f);
	}

	public class UpdateHandler extends UpdateManagerMod {

		public UpdateHandler(cpw.mods.fml.common.modloader.BaseMod m) {
			super(m);
		}

		public String getModName() {
			return "Chat Macros";
		}

		public String getChangelogURL() {
			return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/Chat%20Macros/Changelog.txt";
		}

		public String getUpdateURL() {
			return "https://dl.dropbox.com/u/34938401/Mods/On%20Topic/Mods/Chat%20Macros/Version.txt";
		}

		public String getModURL() {
			return "http://www.minecraftforum.net/topic/528166-123-mlforge-vazkiis-mods-ebonapi-last-updated-12512/";
		}
		
		public String getUMVersion(){
			return "1.1.2";
		}
		
	}

}
