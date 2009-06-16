package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
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
	private CreateTripScreen2 nextTripScreen;
	private ObjectChoiceField tripUserType;
	
	private String[] USER_TYPES = {"DRIVER", "PASSENGER"};
	
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
		// check to see if there are any trips that match this one
		CarPoolService_Stub service = new CarPoolService_Stub();
		String id = UniqueIDGeneratorUtil.getUniqueId();
		String title = TripData.getTripTitle();
		String startAddress = TripData.getTripStartPlace();
		String endAddress = TripData.getTripEndPlace();
		Date startDate = TripData.getTripStartTime();
		Date endDate = TripData.getTripEndTime();
		int numMiles = 10;
		int latitude = -999;
		int longitude = -999;
		Trip[] trips = service.findSimilarTrips(id, title, startAddress, endAddress, startDate, endDate, latitude, longitude, organizationId, numMiles);
		if (trips != null)
		{
			// go to screen 6 - select an existing trip
		}
		// otherwise, create the new trip
		
		if (null == nextTripScreen)
        {
			nextTripScreen = new CreateTripScreen2();
        }
		TripData.setTripUserType(Integer.toString(tripUserType.getSelectedIndex()));
		
		UiApplication.getUiApplication().pushScreen(nextTripScreen); 
	}
	
	
}
