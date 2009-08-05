package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.fields.GreenStarObjectListField;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Place;
import edu.psu.sweng500.carpool.device.services.generated.User;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class CarPoolOptionsScreen extends MainScreen
{
	private String[] mainMenuItems;
	private GreenStarObjectListField menu;
	
	private SelectLocationScreen selectLocationScreen;
	private PreferencesScreen preferencesScreen;
	
	public CarPoolOptionsScreen()
	{	
		TitleBar titleBar = new TitleBar(6);
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		this.add(titleBar);
		
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
        String mnuStartLocation = "Select a default start location";
        String mnuEndLocation = "Select a default end location";
        String mnuPreferences = "Edit Preferences";
        mainMenuItems = new String[]{mnuStartLocation, mnuEndLocation, mnuPreferences};
        menu.set(mainMenuItems);
        verticalManager.add(menu);
        this.add(verticalManager);
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
            case 2:
            	if (null == preferencesScreen)
                {
            		preferencesScreen = new PreferencesScreen();
                }
                
                UiApplication.getUiApplication().pushScreen(preferencesScreen);
            break;
        }
        
        return true;
    }
	
}
