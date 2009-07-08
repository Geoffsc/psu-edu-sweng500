package edu.psu.sweng500.carpool.device.app;

import java.io.IOException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;

public class CarPoolMainScreen extends MainScreen
{
	private String[] mainMenuItems;
	private MainScreenObjectListField menu;
	
	private SearchScreen searchScreen;
	private SplashScreen splashScreen;
	private CarPoolOptionsScreen carPoolOptionsScreen;
	
	private MenuItem startLocationItem = new MenuItem("Options", 110, 10) {
		public void run() {
			pushOptionsScreen();
		}
	};
	
	public CarPoolMainScreen()
	{	
		this.setTitle("Car Pool");
        
		LabelField label1 = new LabelField("You are logged in as: " + SessionSettings.getUserName(), LabelField.READONLY);
		this.add(label1);		
		
        menu = new MainScreenObjectListField();
        String mnuSearch = "Search";
        String mnuCreate = "Create trip";
        mainMenuItems = new String[]{mnuSearch, mnuCreate};
        menu.set(mainMenuItems);
        this.add(menu);
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
            	/*
            	if (null == createTripScreen)
                {
            		createTripScreen = new CreateTripScreen1();
                }
                
                UiApplication.getUiApplication().pushScreen(createTripScreen);
                */
                
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
    };
}
