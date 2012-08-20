package vazkii.codebase.client;

import java.util.List;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.GuiPlayerInfo;
import net.minecraft.src.NetClientHandler;
import vazkii.codebase.common.CommonUtils;

public class ClientUtils {

	public static EntityClientPlayerMP getClientPlayer(){
		return CommonUtils.getMc().thePlayer;
	}
	
	public static boolean isConnectedToServer(){
		return !CommonUtils.getMc().isSingleplayer();
	}
	
	public static String getFPS(){
		String debug = CommonUtils.getMc().debug;
		String fps = debug.split("[( f]")[0];
		return fps;
	}
	
	public static String getPing(){
		EntityClientPlayerMP player = getClientPlayer();
		String username = player.username;
        String nameToCheck = "nil"; 
        NetClientHandler clientHandler =  player.sendQueue;
        List<GuiPlayerInfo> playerList = clientHandler.playerInfoList;
        int time = -1;
        
        for(GuiPlayerInfo p : playerList){
        	if(!p.name.equals(username))
        		continue;
        	
        	time = p.responseTime;
        	if(time >= 0)
        		return ""+time;
        }
        return "N/A";
	}
	
}
