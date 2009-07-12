package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.thinkingblackberry.GridFieldManager;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.collection.List;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class DetailsScreen extends MainScreen
{
	private String tripId;
	private DirectionsScreen directionsScreen;
	private Trip trip;
	
	public DetailsScreen()
	{
		this.setTitle("Trip Details");
	}
	
	public void setTripId(String tripId)
	{
		this.tripId = tripId;
	}
	
	public void searchTrips()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting searchTrips").getBytes(), EventLogger.ALWAYS_LOG);
		this.deleteAll();
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Finished removing all items").getBytes(), EventLogger.ALWAYS_LOG);
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Adding " + tripId + " to the parameters").getBytes(), EventLogger.ALWAYS_LOG);
		/*
		Vector parameterVec = new Vector();
		Parameter param1 = new Parameter();
		param1.setName("id");
		param1.setValue(tripId);
		parameterVec.addElement(param1);
		
		Parameter[] parameters = new Parameter[parameterVec.size()];
		Enumeration paramEnum = parameterVec.elements();
		int paramCount = 0;
		while (paramEnum.hasMoreElements())
		{
			Parameter param = (Parameter) paramEnum.nextElement();
			parameters[paramCount++] = param;
		}
		*/
		
		try
		{
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			trip = serviceStub.getTrip(tripId);
			// UiApplication.getUiApplication().getActiveScreen().invalidate();
			if (trip != null)
			{
			
				Manager gridFieldManager = new GridFieldManager(new int[] {100, 160}, 30, 0);  
				// Row 1  
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Title: " + trip.getTitle()).getBytes(), EventLogger.ALWAYS_LOG);
				gridFieldManager.add(new LabelField("Title:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getTitle(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				// Row 2
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Start address: " + trip.getStartAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
				gridFieldManager.add(new LabelField("Start Address:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getStartAddress().getName(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				// Row 3
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("End address: " + trip.getEndAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
				gridFieldManager.add(new LabelField("End Address:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getEndAddress().getName(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				// Row 4
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Start date: " + trip.getStartDate()).getBytes(), EventLogger.ALWAYS_LOG);
				gridFieldManager.add(new LabelField("Start Date:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getStartDate(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				// Row 5
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("End date: " + trip.getEndDate()).getBytes(), EventLogger.ALWAYS_LOG);
				gridFieldManager.add(new LabelField("End Date:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getEndDate(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				// Row 6
				gridFieldManager.add(new LabelField("Organization:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
				gridFieldManager.add(new LabelField(trip.getOrganization().getName(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
				
				add(gridFieldManager);
			}
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
	}
	
	private MenuItem showDirectionsItem = new MenuItem("Map it!", 110, 10) {
		public void run() {
			pushDirectionsScreen();
		}
	};
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(showDirectionsItem);
        menu.add(MenuItem.separator(showDirectionsItem.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void pushDirectionsScreen()
	{
		if (directionsScreen == null)
		{
			directionsScreen = new DirectionsScreen();
		}
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting start place: " + trip.getStartAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting end place: " + trip.getEndAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
		directionsScreen.setStartPlace(trip.getStartAddress());
		directionsScreen.setEndPlace(trip.getEndAddress());
		directionsScreen.initialize();
		UiApplication.getUiApplication().pushScreen(directionsScreen);
	}

}
