package edu.psu.sweng500.carpool.device.app.screens;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.fields.GreenStarObjectListField;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class CarPoolPreMainScreen extends MainScreen
{
	private String[] mainMenuItems;
	private GreenStarObjectListField menu;
	
	private LoginScreen loginScreen;
	private RegisterUserScreen registerUserScreen;
	
	public CarPoolPreMainScreen()
	{	
		TitleBar titleBar = new TitleBar(1);
		this.add(titleBar);

		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		
		LabelField label1 = new LabelField("Please select:", LabelField.READONLY)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.BLUE);
                graphics.clear();
                super.paint(graphics);
            }
		};
		verticalManager.add(label1);
		verticalManager.add(new SeparatorField());
		
        menu = new GreenStarObjectListField();
        menu.setRowHeight(25);
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
	
}
