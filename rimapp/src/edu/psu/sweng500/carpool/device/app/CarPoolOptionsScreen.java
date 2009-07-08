package edu.psu.sweng500.carpool.device.app;

import java.io.IOException;
import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Place;
import edu.psu.sweng500.carpool.device.services.generated.User;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;

public class CarPoolOptionsScreen extends MainScreen
{
	private String[] mainMenuItems;
	private MainScreenObjectListField menu;
	
	private SearchScreen searchScreen;
	private SelectLocationScreen selectLocationScreen;
	
	public CarPoolOptionsScreen()
	{	
		this.setTitle("Options");
		
        menu = new MainScreenObjectListField();
        String mnuStartLocation = "Select a default start location";
        String mnuEndLocation = "Select a default end location";
        mainMenuItems = new String[]{mnuStartLocation, mnuEndLocation};
        menu.set(mainMenuItems);
        this.add(menu);
	}
	
	private void pushStartLocationScreen()
	{
		if (selectLocationScreen == null)
		{
			selectLocationScreen = new SelectLocationScreen();
		}
		selectLocationScreen.setType("START");
		selectLocationScreen.setStartLat(40.053209);
		selectLocationScreen.setStartLon(-75.522165);
		CarPoolService_Stub serviceStub = new CarPoolService_Stub();
		try
		{
			User theUser = serviceStub.getUser(SessionSettings.getUserName());
			if (theUser != null)
			{
				Place[] places = theUser.getSavedPlaces();
				// use the START place from the user as the start point
				boolean notFound = true;
				for (int i = 0; i < places.length && notFound; i++)
				{
					Place thePlace = places[i];
					if (thePlace.getCategory().equalsIgnoreCase("start"))
					{
						System.out.println("Found a location for the start place");
						notFound = false;
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming latitude is: " + thePlace.getLatitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming longitude is: " + thePlace.getLongitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
						// screenParameters.put("latitude", thePlace.getLatitude());
						// screenParameters.put("longitude", thePlace.getLongitude());
						selectLocationScreen.setStartLat(thePlace.getLatitude().doubleValue());
						selectLocationScreen.setStartLon(thePlace.getLongitude().doubleValue());
					}
				}
			}
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		
		selectLocationScreen.initialize();
		UiApplication.getUiApplication().pushScreen(selectLocationScreen);
	}
	
	private void pushEndLocationScreen()
	{
		if (selectLocationScreen == null)
		{
			selectLocationScreen = new SelectLocationScreen();
		}
		selectLocationScreen.setType("END");
		selectLocationScreen.setStartLat(40.053209);
		selectLocationScreen.setStartLon(-75.522165);
		CarPoolService_Stub serviceStub = new CarPoolService_Stub();
		try
		{
			User theUser = serviceStub.getUser(SessionSettings.getUserName());
			if (theUser != null)
			{
				Place[] places = theUser.getSavedPlaces();
				// use the START place from the user as the start point
				boolean notFound = true;
				for (int i = 0; i < places.length && notFound; i++)
				{
					Place thePlace = places[i];
					if (thePlace.getCategory().equalsIgnoreCase("end"))
					{
						System.out.println("Found a location for the start place");
						notFound = false;
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming latitude is: " + thePlace.getLatitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming longitude is: " + thePlace.getLongitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
						// screenParameters.put("latitude", thePlace.getLatitude());
						// screenParameters.put("longitude", thePlace.getLongitude());
						selectLocationScreen.setStartLat(thePlace.getLatitude().doubleValue());
						selectLocationScreen.setStartLon(thePlace.getLongitude().doubleValue());
					}
				}
			}
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		
		selectLocationScreen.initialize();
		UiApplication.getUiApplication().pushScreen(selectLocationScreen);
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = menu.getSelectedIndex();
        switch (selected) {
            case 0:
            	this.pushStartLocationScreen();
            break;
            case 1:
            	this.pushEndLocationScreen();
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
