package edu.psu.sweng500.carpool.device.app;

import java.io.IOException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class CarPoolPreMainScreen extends MainScreen
{
	private String[] mainMenuItems;
	private MainScreenObjectListField menu;
	
	private LoginScreen loginScreen;
	private RegisterUserScreen registerUserScreen;
	
	public CarPoolPreMainScreen()
	{	
		TitleBar titleBar = new TitleBar(1);
		this.add(titleBar);

		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		
        menu = new MainScreenObjectListField();
        String mnuSearch = "Login";
        String mnuCreate = "Register";
        mainMenuItems = new String[]{mnuSearch, mnuCreate};
        menu.set(mainMenuItems);
        verticalManager.add(menu);
        this.add(verticalManager);
	}
	    
	public boolean onClose()
	{
		System.exit(0);
		return true;
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = menu.getSelectedIndex();
        switch (selected) {
            case 0:
            	if (null == loginScreen)
                {
            		loginScreen = new LoginScreen();
                }
                
                UiApplication.getUiApplication().pushScreen(loginScreen);
                break;
            case 1:
            	if (null == registerUserScreen)
                {
            		registerUserScreen = new RegisterUserScreen();
                }
                
                UiApplication.getUiApplication().pushScreen(registerUserScreen);
            	break;
        }
        
        return true;
    }
	
	private class MainScreenObjectListField extends ObjectListField {
        
        private Bitmap icon = Bitmap.getBitmapResource("edu/psu/sweng500/carpool/device/app/img/star_green.png");
        
        // We are going to take care of drawing the item.
        public void drawListRow(ListField listField, Graphics graphics, 
                int index, int y, int width) {
            if (null != icon) {
                int offsetY = (this.getRowHeight() - icon.getHeight())/2;
                graphics.drawBitmap(1,y + offsetY, icon.getWidth(),
                        icon.getHeight(),icon,0,0);
                graphics.drawText(mainMenuItems[index], 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + mainMenuItems[index], 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
        }
        
        public void paint(Graphics graphics)
        {
            // Sets the BackgroundColor
            graphics.setBackgroundColor(Color.GRAY);
            // Clears the entire graphic area to the current background
            graphics.clear();
            super.paint(graphics);  
        }
    };
}
