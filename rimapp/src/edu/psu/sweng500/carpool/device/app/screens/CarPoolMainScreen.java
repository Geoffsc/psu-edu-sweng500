package edu.psu.sweng500.carpool.device.app.screens;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.controllers.CreateTripController;
import edu.psu.sweng500.carpool.device.app.fields.GreenStarObjectListField;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class CarPoolMainScreen extends MainScreen
{
	private String[] mainMenuItems;
	private GreenStarObjectListField menu;
	
	private SearchScreen searchScreen;
	private CarPoolOptionsScreen carPoolOptionsScreen;
	
	private MenuItem startLocationItem = new MenuItem("Options", 110, 10) {
		public void run() {
			pushOptionsScreen();
		}
	};
	public CarPoolMainScreen()
	{	
		TitleBar titleBar = new TitleBar(1);
		this.add(titleBar);

		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		
		LabelField label1 = new LabelField("You are logged in as: " + SessionSettings.getUserName(), LabelField.READONLY)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.BLUE);
                graphics.clear();
                super.paint(graphics);
            }
		};
		verticalManager.add(label1);
		SeparatorField seperator1 = new SeparatorField();
		verticalManager.add(seperator1);
		
        menu = new GreenStarObjectListField();
        menu.setRowHeight(25);
        String mnuSearch = "Search";
        String mnuCreate = "Create trip";
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
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(startLocationItem);
        menu.add(MenuItem.separator(startLocationItem.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void pushOptionsScreen()
	{
		if (carPoolOptionsScreen == null)
		{
			carPoolOptionsScreen = new CarPoolOptionsScreen();
		}
		UiApplication.getUiApplication().pushScreen(carPoolOptionsScreen);
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = menu.getSelectedIndex();
        switch (selected) {
            case 0:
            	if (null == searchScreen)
                {
            		searchScreen = new SearchScreen();
                }
                
                UiApplication.getUiApplication().pushScreen(searchScreen);
            break;
            case 1:
            	CreateTripController controller = CreateTripController.getInstance();
            	controller.execute(this, null);
            	break;
        }
        
        return true;
    }
	
}
