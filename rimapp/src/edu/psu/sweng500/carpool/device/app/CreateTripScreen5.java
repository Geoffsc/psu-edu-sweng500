package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.List;

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

public class CreateTripScreen5 extends MainScreen
{
	private CreateTripScreen6 createTripScreen6;
	private CreateTripScreen7 createTripScreen7;
	private ObjectChoiceField tripUserType;
	
	private String[] USER_TYPES = {"Driver", "Passenger"};
	
	public CreateTripScreen5()
	{
		this.setTitle("Create Trip - Person Type");
        
		LabelField label1 = new LabelField("Are you going to be:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		tripUserType = new ObjectChoiceField("", USER_TYPES, 0, Field.FIELD_LEFT | Field.FIELD_VCENTER);
		
		add(label1);
		add(tripUserType);
		
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
		TripData.setTripUserType((String) (tripUserType.getChoice(tripUserType.getSelectedIndex())));
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting performSearch: ").getBytes(), EventLogger.ALWAYS_LOG);
		// check to see if there are any trips that match this one
		CarPoolService_Stub service = new CarPoolService_Stub();
		String id = UniqueIDGeneratorUtil.getUniqueId();
		String title = TripData.getTripTitle();
		String startAddress = TripData.getTripStartPlace();
		String endAddress = TripData.getTripEndPlace();
		
		// format the start and end date for the search
		Date startDate = TripData.getTripStartTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date endDate = TripData.getTripEndTime();
		int numMiles = 0;
		String latitude = "";
		String longitude = "";
		Hashtable parameters = new Hashtable();
		try
		{
			Trip[] trips = service.findSimilarTrips("", "", startAddress, endAddress, dateFormat.format(startDate), dateFormat.format(endDate), latitude, longitude, "12345", numMiles);
			// trips = null;
			if (trips != null)
			{
				// add the Create New option
				BigVector tripVector = new BigVector();
				Trip createTrip = new Trip();
				createTrip.setId("create");
				createTrip.setTitle("* Create New *");
				
				tripVector.addElement(createTrip);
				tripVector.addElements(trips);
				
				trips = new Trip[tripVector.size()];
				tripVector.copyInto(0, tripVector.size(), trips, 0);
	        	
				// createTripScreen6.setTrips(trips);
				parameters.put("foundMatchingTrip", "true");
				parameters.put("trips", trips);
				
				/*
				if (null == createTripScreen6)
	            {
	        		createTripScreen6 = new CreateTripScreen6();
	            }
	        	UiApplication.getUiApplication().pushScreen(createTripScreen6);
	        	*/ 
	        	// return;
			}
			else
			{
			}
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
			return;
		}
		// otherwise, create the new trip
		
		/*
		if (createTripScreen7 == null)
		{
			createTripScreen7 = new CreateTripScreen7();
		}
		UiApplication.getUiApplication().pushScreen(createTripScreen7);
		*/
		
		CreateTripController createTripController = CreateTripController.getInstance();
		createTripController.execute(this, parameters);
	}
	
	
}
