package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiChat extends GuiScreen
{
	boolean hasAddGuiOpen = false;
	GuiTextField macroContentsField;
	GuiTextField macroNameField;
	
    private String field_50062_b = "";

    /**
     * keeps position of which chat message you will select when you press up, (does not increase for duplicated
     * messages sent immediately after each other)
     */
    private int sentHistoryCursor = -1;
    private boolean field_50060_d = false;
    private String field_50061_e = "";
    private String field_50059_f = "";
    private int field_50067_h = 0;
    private List field_50068_i = new ArrayList();

    /** used to pass around the URI to various dialogues and to the host os */
    private URI clickedURI = null;

    /** Chat entry field */
    protected GuiTextField inputField;

    /**
     * is the text that appears when you press the chat key and the input box appears pre-filled
     */
    private String defaultInputFieldText = "";

    public GuiChat() {}

    public GuiChat(String par1Str)
    {
        this.defaultInputFieldText = par1Str;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getSentMessageList().size();
        this.inputField = new GuiTextField(this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
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

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.func_50014_d();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();
        if(hasAddGuiOpen){
        	if(!controlList.isEmpty()){
            	GuiButton b = (GuiButton) controlList.get(0);
            	b.enabled = mod_ChatMacros.isMacroValid(macroNameField.getText(), macroContentsField.getText());
        	}
            this.macroContentsField.updateCursorCounter();
            this.macroNameField.updateCursorCounter();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
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
            this.field_50060_d = false;
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

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();

        if (var1 != 0)
        {
            if (var1 > 1)
            {
                var1 = 1;
            }

            if (var1 < -1)
            {
                var1 = -1;
            }

            if (!isShiftKeyDown())
            {
                var1 *= 7;
            }

            this.mc.ingameGUI.adjustHistoryOffset(var1);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        if (par3 == 0)
        {
            ChatClickData var4 = this.mc.ingameGUI.getChatClickDataFromMouse(Mouse.getX(), Mouse.getY());

            if (var4 != null)
            {
                URI var5 = var4.getURI();

                if (var5 != null)
                {
                    this.clickedURI = var5;
                    this.mc.displayGuiScreen(new GuiChatConfirmLink(this, this, var4.func_50088_a(), 0, var4));
                    return;
                }
            }
        }

        if(hasAddGuiOpen){
            macroContentsField.mouseClicked(par1, par2, par3);
            macroNameField.mouseClicked(par1, par2, par3);
        }

        this.inputField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
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

    public void confirmClicked(boolean par1, int par2)
    {
        if (par2 == 0)
        {
            if (par1)
            {
                try
                {
                    Class var3 = Class.forName("java.awt.Desktop");
                    Object var4 = var3.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    var3.getMethod("browse", new Class[] {URI.class}).invoke(var4, new Object[] {this.clickedURI});
                }
                catch (Throwable var5)
                {
                    var5.printStackTrace();
                }
            }

            this.clickedURI = null;
            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Autocompletes player name
     */
    public void completePlayerName()
    {
        Iterator var2;
        GuiPlayerInfo var3;

        if (this.field_50060_d)
        {
            this.inputField.func_50021_a(-1);

            if (this.field_50067_h >= this.field_50068_i.size())
            {
                this.field_50067_h = 0;
            }
        }
        else
        {
            int var1 = this.inputField.func_50028_c(-1);

            if (this.inputField.func_50035_h() - var1 < 1)
            {
                return;
            }

            this.field_50068_i.clear();
            this.field_50061_e = this.inputField.getText().substring(var1);
            this.field_50059_f = this.field_50061_e.toLowerCase();
            var2 = ((EntityClientPlayerMP)this.mc.thePlayer).sendQueue.playerInfoList.iterator();

            while (var2.hasNext())
            {
                var3 = (GuiPlayerInfo)var2.next();

                if (var3.nameStartsWith(this.field_50059_f))
                {
                    this.field_50068_i.add(var3);
                }
            }

            if (this.field_50068_i.size() == 0)
            {
                return;
            }

            this.field_50060_d = true;
            this.field_50067_h = 0;
            this.inputField.func_50020_b(var1 - this.inputField.func_50035_h());
        }

        if (this.field_50068_i.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (var2 = this.field_50068_i.iterator(); var2.hasNext(); var4.append(var3.name))
            {
                var3 = (GuiPlayerInfo)var2.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.addChatMessage(var4.toString());
        }

        this.inputField.func_50031_b(((GuiPlayerInfo)this.field_50068_i.get(this.field_50067_h++)).name);
    }

    /**
     * input is relative and is applied directly to the sentHistoryCursor so -1 is the previous message, 1 is the next
     * message from the current cursor position
     */
    public void getSentHistory(int par1)
    {
        int var2 = this.sentHistoryCursor + par1;
        int var3 = this.mc.ingameGUI.getSentMessageList().size();

        if (var2 < 0)
        {
            var2 = 0;
        }

        if (var2 > var3)
        {
            var2 = var3;
        }

        if (var2 != this.sentHistoryCursor)
        {
            if (var2 == var3)
            {
                this.sentHistoryCursor = var3;
                this.inputField.setText(this.field_50062_b);
            }
            else
            {
                if (this.sentHistoryCursor == var3)
                {
                    this.field_50062_b = this.inputField.getText();
                }

                this.inputField.setText((String)this.mc.ingameGUI.getSentMessageList().get(var2));
                this.sentHistoryCursor = var2;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
        drawRect(2, this.height - 37, fontRenderer.getStringWidth("Finish Mode: Close Chat Pane. (RCONTROL)") + 6, this.height - 16, Integer.MIN_VALUE);
        this.inputField.drawTextBox();
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
        super.drawScreen(par1, par2, par3);
    }
}
