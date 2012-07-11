package net.minecraft.src;

import java.io.File;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

public class GuiChatMacros extends GuiChat {

	boolean hasAddGuiOpen = false;
	GuiTextField macroContentsField;
	GuiTextField macroNameField;
	
	GuiChat superGui;
	
	public GuiChatMacros(GuiChat superGui){
		super();
		this.superGui = superGui;
	}
	
    public void updateScreen()
    {
    	super.updateScreen();
    	if(hasAddGuiOpen){
        	if(!controlList.isEmpty()){
            	GuiButton b = (GuiButton) controlList.get(0);
            	b.enabled = mod_ChatMacros.isMacroValid(macroNameField.getText(), macroContentsField.getText());
        	}
            this.macroContentsField.updateCursorCounter();
            this.macroNameField.updateCursorCounter();
        }
    }
	
    protected void keyTyped(char par1, int par2)
    {
    	if(par2 == Keyboard.KEY_LMENU){
    		mod_ChatMacros.macrosEnabled = !mod_ChatMacros.macrosEnabled;
    		try{
    			mod_ChatMacros.setProps();
    		}catch (IOException e){
    			e.printStackTrace();
    		}
    	}
    	if(par2 == Keyboard.KEY_RCONTROL){
    		mod_ChatMacros.closeOnFinish = !mod_ChatMacros.closeOnFinish;
    		try{
    			mod_ChatMacros.setProps();
    		}catch (IOException e){
    			e.printStackTrace();
    		}
    	}
        if (par2 == 15)
        {
            this.completePlayerName();
        }
        else
        {
        	ModLoader.<GuiChat, Boolean>setPrivateValue(GuiChat.class, superGui, 2, false);
        }

        if (par2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (par2 == 28)
        {
            String var3 = this.inputField.getText().trim();

            if (var3.length() > 0 && !this.mc.lineIsCommand(var3))
            {
            	if(hasAddGuiOpen && (macroNameField.getIsFocused() || macroContentsField.getIsFocused())) return;
            	
            	if(mod_ChatMacros.macrosEnabled){
            	String msg = mod_ChatMacros.messWithMessage(var3);
            	if(msg != null){
    				if(msg.contains("¶")){
    					String[] macroTokens = msg.split("¶");
    					boolean invalid = false;
    					for(String s : macroTokens)
    						if(s.length() == 0 || s.length() > 100) invalid = true;
    					if(!invalid){
    						for(String s : macroTokens)
    							mc.thePlayer.sendChatMessage(s);
    					            if(mod_ChatMacros.closeOnFinish)
    					                mc.displayGuiScreen((GuiScreen)null);
    					                else inputField.setText("");
    					            return;
    					}else mc.thePlayer.addChatMessage("§cInvalid Message!");
    				}else if(msg.equals("-[jus'jx.,s@y7fe™")){ //This code is the code for the add command, it's defined in mod_ChatMacros.
    					resetFields();
    					inputField.setText("");
			            return;
    				}
    				mc.thePlayer.sendChatMessage(msg);
            	}
            	else if(mod_ChatMacros.closeOnFinish)
                    mc.displayGuiScreen((GuiScreen)null);
                	else inputField.setText("");
            	}else mc.thePlayer.sendChatMessage(var3);
            }
            if(mod_ChatMacros.closeOnFinish)
            mc.displayGuiScreen((GuiScreen)null);
            else inputField.setText("");
        }
        else if (par2 == 200)
        {
            this.getSentHistory(-1);
        }
        else if (par2 == 208)
        {
            this.getSentHistory(1);
        }
        else if (par2 == 201)
        {
            this.mc.ingameGUI.adjustHistoryOffset(19);
        }
        else if (par2 == 209)
        {
            this.mc.ingameGUI.adjustHistoryOffset(-19);
        }
        else
        {
        	if(hasAddGuiOpen){
            	if(inputField.getIsFocused())
                    inputField.textboxKeyTyped(par1, par2);
                	else if(macroContentsField.getIsFocused())
                	macroContentsField.textboxKeyTyped(par1, par2);
                	else macroNameField.textboxKeyTyped(par1, par2);
        	}else inputField.textboxKeyTyped(par1, par2);
        }
    }
    
    protected void actionPerformed(GuiButton par1GuiButton)
    {
    	switch(par1GuiButton.id){	

    	case 0:{
    		if(mod_ChatMacros.addMacroRemotely(macroNameField.getText(), macroContentsField.getText())){
    			macroContentsField.setText("");
    			macroNameField.setText("");
    			try {
					mod_ChatMacros.readMacros(new File(ModLoader.getMinecraftInstance().getAppDir("minecraft"), "Chat Macros.txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		break;
    		}	
    	case 1: {
    		macroContentsField.setText(macroContentsField.getText().concat("¿"));
    		macroContentsField.setFocused(true);
    		break;
    		}	
    	case 2: {
    		macroContentsField.setText(macroContentsField.getText().concat("¶"));
    		macroContentsField.setFocused(true);
    		break;
    		}
		case 3: {
			clearFields();
			break;
			}
    	}
    }
    
    public void drawScreen(int par1, int par2, float par3)
    {
    	super.drawScreen(par1, par2, par3);
    	drawRect(2, this.height - 37, fontRenderer.getStringWidth("Finish Mode: Close Chat Pane. (RCONTROL)") + 6, this.height - 16, Integer.MIN_VALUE);
        if(mod_ChatMacros.macrosEnabled)
        drawString(fontRenderer, "§9Chat Macros §aEnabled. §9(ALT)" , 2, height-36, 0xFFFFFF);
        else drawString(fontRenderer, "§9Chat Macros §cDisabled. §9(ALT)" , 2, height-36, 0xFFFFFF);
        if(mod_ChatMacros.closeOnFinish)
        drawString(fontRenderer, "§9Finish Mode: Close Chat Pane. (RCONTROL)" , 2, height-26, 0xFFFFFF);
        else drawString(fontRenderer, "§9Finish Mode: Clear Message. (RCONTROL)" , 2, height-26, 0xFFFFFF);
        if(hasAddGuiOpen){
            macroContentsField.drawTextBox();
            macroNameField.drawTextBox();
            drawString(fontRenderer, "»", width-344, 10, 0xFFFFFF);
            if(mod_ChatMacros.macrosList.contains(macroNameField.getText()) || macroNameField.getText().equalsIgnoreCase("macros") || macroNameField.getText().equalsIgnoreCase("help") || macroNameField.getText().equalsIgnoreCase("reload") || macroNameField.getText().equalsIgnoreCase("add"))
            drawString(fontRenderer, "That macro already exists.", width - 415, 25, 0xFF0000);
            else if(macroNameField.getText().isEmpty())
            drawString(fontRenderer, "You must specify a macro name.", width - 415, 25, 0xFF0000);
            else if(macroContentsField.getText().isEmpty())
            drawString(fontRenderer, "You must specify the macro's contents.", width - 415, 25, 0xFF0000);     
        }
    }
    
    void resetFields(){
    	hasAddGuiOpen = true;
        this.macroContentsField = new GuiTextField(this.fontRenderer, width - 330, 5, 320, 16);
        this.macroContentsField.setMaxStringLength(256);
        this.macroNameField = new GuiTextField(this.fontRenderer, width - 416, 5, 65, 16);
        this.macroNameField.setMaxStringLength(16);
    	controlList.add(new GuiButton(0, width-100, 26, 18, 20, "+"));
    	controlList.add(new GuiButton(1, width-80, 26, 18, 20, "¿"));
    	controlList.add(new GuiButton(2, width-60, 26, 18, 20, "¶"));
    	controlList.add(new GuiButton(3, width-40, 26, 18, 20, "X"));
        this.inputField.setCanLoseFocus(true);
    }
    
    void clearFields(){
    	hasAddGuiOpen = false;
    	macroContentsField = null;
    	macroNameField = null;
        this.inputField.setFocused(true);
        this.inputField.setCanLoseFocus(false);
    	controlList.clear();
    }
	
}
