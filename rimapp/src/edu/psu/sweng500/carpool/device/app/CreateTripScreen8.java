package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Organization;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Participant;
import edu.psu.sweng500.carpool.device.services.generated.Place;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import edu.psu.sweng500.carpool.device.services.generated.TripUser;
import edu.psu.sweng500.carpool.device.services.generated.User;

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

public class CreateTripScreen8 extends MainScreen
{
	
	public CreateTripScreen8()
	{
		this.setTitle("Review details");
        
		LabelField label1 = new LabelField("Create this car pool?", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		
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
	             performUpdate();
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
		// UiApplication.getUiApplication().popScreen(screen);
	}
	
	private void performUpdate()
	{
		Trip trip = new Trip();
		trip.setId(UniqueIDGeneratorUtil.getUniqueId());
		trip.setTitle(TripData.getTripTitle());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		trip.setStartDate(dateFormat.format(TripData.getTripStartTime()));
		trip.setEndDate(dateFormat.format(TripData.getTripEndTime()));
		
		Place startAddress = new Place();
		startAddress.setId(TripData.getTripStartPlace());
		trip.setStartAddress(startAddress);
		
		Place endAddress = new Place();
		endAddress.setId(TripData.getTripEndPlace());
		trip.setEndAddress(endAddress);
		
		Organization org = new Organization();
		org.setId("12345");
		trip.setOrganization(org);
		
		Participant[] participants = new Participant[1];
		Participant participant = new Participant();
		TripUser user = new TripUser();
		
		CarPoolService_Stub service = new CarPoolService_Stub();		
		try
		{
			User theUser = service.getUser(SessionSettings.getUserName());
			
			user.setId(theUser.getId());
			participant.setUser(user);
			participant.setType(TripData.getTripUserType());
			participants[0] = participant;
			trip.setParticipants(participants);
			service.updateTrip(trip);
		
			Dialog.alert("Trip created!");
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
			return;
		}
	}
	
	
}
