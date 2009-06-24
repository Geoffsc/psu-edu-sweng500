package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Place;
import edu.psu.sweng500.carpool.device.services.generated.User;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ChoiceField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripScreen1 extends MainScreen
{
	private CreateTripScreen2 nextTripScreen;
	private DateField startDate;
	private BasicEditField tripTitleField;
	
	public CreateTripScreen1()
	{
		this.setTitle("Create Trip - Enter Title");
        
		LabelField label1 = new LabelField("Trip Title:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		tripTitleField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER);
		
		add(label1);
		add(tripTitleField);
		
		ButtonField buttonField = new ButtonField("Next >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		add(buttonField);
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             performSearch();
	         }
	     };
	     buttonField.setChangeListener(listener);
	     
	}
	
	public void performSearch()
	{
		Hashtable screenParameters = new Hashtable();
		if (null == nextTripScreen)
        {
			nextTripScreen = new CreateTripScreen2();
        }
		TripData.setTripTitle(tripTitleField.getText());
		
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
						screenParameters.put("latitude", thePlace.getLatitude());
						screenParameters.put("longitude", thePlace.getLongitude());
						screenParameters.put("type", "START");
						
						// nextTripScreen.setStartLat(thePlace.getLatitude().doubleValue());
						// nextTripScreen.setStartLon(thePlace.getLongitude().doubleValue());
						// nextTripScreen.setType("START");
					}
				}
			}
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		
    	// nextTripScreen.initialize();
		// UiApplication.getUiApplication().pushScreen(nextTripScreen);
		CreateTripController controller = CreateTripController.getInstance();
		controller.execute(this, screenParameters);
	}
	
}
