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
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import net.minecraft.src.vazkii.updatemanager.IUMAdvanced;
import net.minecraft.src.vazkii.updatemanager.IUpdateManager;
import net.minecraft.src.vazkii.updatemanager.ModType;

public class mod_ChatMacros extends BaseMod implements IUpdateManager, IUMAdvanced {

	static HashMap<String, String> macros = new HashMap();
	static List<String> macrosList = new LinkedList();
	
	static char macroCharStart = '{';
	static char macroCharEnd = '}';
	
	public static boolean macrosEnabled = true;
	public static boolean closeOnFinish = true;
	
	public String getVersion() {
		return "by Vazkii. Version [1.0] for 1.2.5";
	}

	public void load() {
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
        		if(tokens[0].equalsIgnoreCase("macros") || tokens[0].equalsIgnoreCase("reload")) continue;
        		
        		if(tokens[0].equalsIgnoreCase("macrocharstart")){
        			macroCharStart = tokens[1].charAt(0);
        			continue;
        		}
            	if(tokens[0].equalsIgnoreCase("macrocharend")){
            		macroCharEnd = tokens[1].charAt(0);
            		continue;
            	}
            		macros.put(tokens[0], tokens[1]);
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
		writer.write("# 	¿enchants : Displays the Enchantments on the item you're wielding.\r");
		writer.write("#\r");
		writer.write("# To use a macro just type in the macro start character, the name of the macro and the macro end character.\r");
		writer.write("# In the chat menu, you can press ALT to toggle macros, and you can press RCONTROL to toggle the way the chat\r");
		writer.write("# window reacts to you pressing ENTER, be that closing the GUI or just clearing the message.\r");
		writer.write("#\r");
		writer.write("# There's also two prebuilt macros, one named 'macros' that will display the macros you have, and one named\r");
		writer.write("# 'reload', that will reload your macros so you don't have to close your minecraft everytime you change this file.\r");
		writer.write("#\r");
		writer.write("# Example, with the default settings:\r");
		writer.write("# Typing in {modpage} would display on the chat as http://bit.ly/s6rL8h.\r");
		writer.write("#\r");
		writer.write("# TIP: The TAB character gets nulled in the file parsing, so you can use if for convenience!\r");
		writer.write("#\r");
		writer.write("# Place your macros here:\r");
		writer.write("# Codes for Convenience: » ¿\r");
		writer.write("	\r");
		writer.write("################# File End.");
		writer.close();
	}

	public static String messWithMessage(String message) {
		if(!message.startsWith(Character.toString(macroCharStart)) || !message.endsWith(Character.toString(macroCharEnd)))
			return message;
		String newMessage = message;
		
		newMessage = newMessage.substring(1, newMessage.length()-1);
		if(newMessage.matches("macros")){
			String allMacros = "Macro List: ";
			for(String macro : macrosList)
				allMacros =	allMacros.concat(macro).concat(", ");
			allMacros = allMacros.concat("macros, reload.");
					
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
		
		if(macros.containsKey(newMessage)){
			String macro = macros.get(newMessage);
			if(macro.contains("¿"))
			return parseMacro(macros.get(newMessage));
			return macros.get(newMessage);
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
        List list = player.playerNames;
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
				enchs = enchs.substring(0, enchs.length()-2);
			
			return enchs;
		}else return "null";
	}
	
	public static void reloadMacros() throws IOException{
		macros = null;
		macros = new HashMap();
		macrosList.clear();	
		File f = new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "Chat Macros.txt");
		readMacros(f);
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

	public ModType getModType() {
		return ModType.UNDEFINED;
	}

}
