package edu.psu.sweng500.carpool.device.app.screens;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.fields.DocumentObjectListField;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class SearchScreen extends MainScreen
{
	private String[] mainMenuItems;
	private DocumentObjectListField menu;
	
	private ShowAllResultsScreen showAllScreen;
	private FindByDateScreen findByDateScreen;
	
	public SearchScreen()
	{
		TitleBar titleBar = new TitleBar(2);
		//this.setTitle("Search");
		this.add(titleBar);
		
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		
		SeparatorField seperator1 = new SeparatorField();
		verticalManager.add(seperator1);
		
        menu = new DocumentObjectListField();
        menu.setRowHeight(25);
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

        
    public boolean isDirty()
    {
    	return false;
    }
}
