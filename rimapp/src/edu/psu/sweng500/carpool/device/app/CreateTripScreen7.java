package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Trip;

import net.rim.device.api.collection.util.BigVector;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ChoiceField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripScreen7 extends MainScreen
{
	private CreateTripScreen6 createTripScreen6;
	private CreateTripScreen8 createTripScreen8;
	
	public CreateTripScreen7()
	{
		this.setTitle("Check nearby");
        
		LabelField label1 = new LabelField("Would you like to search the local areas for a nearby car pool?", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		
		add(label1);
		
		ButtonField buttonField1 = new ButtonField("Yes", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		ButtonField buttonField2 = new ButtonField("No", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		add(buttonField1);
		add(buttonField2);
		
		FieldChangeListener yesListener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             performSearch();
	         }
	     };
	     
	     FieldChangeListener noListener = new FieldChangeListener() 
			{
		         public void fieldChanged(Field field, int context) 
		         {
		             // ButtonField buttonField = (ButtonField) field;
		             goToEnd();
		         }
		     };
	     buttonField1.setChangeListener(yesListener);
	     buttonField2.setChangeListener(noListener);
	}
	
	private void goToEnd()
	{
		if (createTripScreen8 == null)
		{
			createTripScreen8 = new CreateTripScreen8();
		}
		UiApplication.getUiApplication().pushScreen(createTripScreen8);
	}
	
	public void performSearch()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting performSearch: ").getBytes(), EventLogger.ALWAYS_LOG);
		// check to see if there are any trips that match this one
		CarPoolService_Stub service = new CarPoolService_Stub();
		String id = UniqueIDGeneratorUtil.getUniqueId();
		String title = TripData.getTripTitle();
		String startAddress = TripData.getTripStartPlace();
		String endAddress = TripData.getTripEndPlace();
		
		// format the start and end date for the search
		Date startDate = TripData.getTripStartTime();
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date endDate = TripData.getTripEndTime();
		int numMiles = 0;
		String latitude = "";
		String longitude = "";
		try
		{
			Trip[] trips = service.findSimilarTrips("", "", startAddress, endAddress, dateFormat.format(startDate), dateFormat.format(endDate), latitude, longitude, "12345", numMiles);
			if (trips != null)
			{
				// go to screen 6 - select an existing trip
				if (null == createTripScreen6)
	            {
	        		createTripScreen6 = new CreateTripScreen6();
	            }
	        	
				// add the Create New option
				BigVector tripVector = new BigVector();
				Trip createTrip = new Trip();
				createTrip.setId("create");
				createTrip.setTitle("* Create New *");
				
				tripVector.addElement(createTrip);
				tripVector.addElements(trips);
				
				trips = new Trip[tripVector.size()];
				tripVector.copyInto(0, tripVector.size(), trips, 0);
				
				createTripScreen6.setTrips(trips);
	        	UiApplication.getUiApplication().pushScreen(createTripScreen6); 
	        	return;
			}
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
			return;
		}
		// otherwise, create the new trip
		
		if (createTripScreen8 == null)
		{
			createTripScreen8 = new CreateTripScreen8();
		}
		UiApplication.getUiApplication().pushScreen(createTripScreen8);
	}
	
	
}
