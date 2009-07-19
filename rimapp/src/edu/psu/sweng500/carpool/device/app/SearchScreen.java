package edu.psu.sweng500.carpool.device.app;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class SearchScreen extends MainScreen
{
	private String[] mainMenuItems;
	private MainScreenObjectListField menu;
	
	private ShowAllResultsScreen showAllScreen;
	private FindByDateScreen findByDateScreen;
	
	TitleBar titleBar = new TitleBar();
    int titleBarHeight = titleBar.getPreferredHeight();
    
	public SearchScreen()
	{
		//this.setTitle("Search");
		this.add(titleBar);
		
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBarHeight);
		        
        menu = new MainScreenObjectListField();
        String mnuSearch = "Show my trips";
        String mnuFindByDate = "Find my trips by Date";
        String mnuSearchAll = "Show all trips";
        String mnuFindByDateAll = "Find all trips by Date";
        mainMenuItems = new String[]{mnuSearch, mnuFindByDate, mnuSearchAll, mnuFindByDateAll};
        menu.set(mainMenuItems);
        verticalManager.add(menu);
        this.add(verticalManager);
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = menu.getSelectedIndex();
        switch (selected) {
            case 0:
            	if (null == showAllScreen)
                {
            		showAllScreen = new ShowAllResultsScreen();
                }
            	
            	showAllScreen.setUserName(SessionSettings.getUserName());
            	showAllScreen.searchTrips();
            	UiApplication.getUiApplication().pushScreen(showAllScreen); 
            break;
            case 1:
            	if (null == findByDateScreen)
                {
            		findByDateScreen = new FindByDateScreen();
                }
            	
            	findByDateScreen.setUserName(SessionSettings.getUserName());
            	UiApplication.getUiApplication().pushScreen(findByDateScreen); 
            break;
            case 2:
            	if (null == showAllScreen)
                {
            		showAllScreen = new ShowAllResultsScreen();
                }
            	
            	showAllScreen.setUserName(null);
            	showAllScreen.searchTrips();
            	UiApplication.getUiApplication().pushScreen(showAllScreen); 
            break;
            case 3:
            	if (null == findByDateScreen)
                {
            		findByDateScreen = new FindByDateScreen();
                }
            	
            	findByDateScreen.setUserName(null);
            	UiApplication.getUiApplication().pushScreen(findByDateScreen); 
            break;
        }
        
        return true;
    }
	
	private class MainScreenObjectListField extends ObjectListField {
        
        private Bitmap icon = Bitmap.getBitmapResource("edu/psu/sweng500/carpool/device/app/img/document.png");                
        
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

        
    public boolean isDirty()
    {
    	return false;
    }
}
