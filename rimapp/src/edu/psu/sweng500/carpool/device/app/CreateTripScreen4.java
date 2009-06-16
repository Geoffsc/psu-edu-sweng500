package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
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

public class CreateTripScreen4 extends MainScreen
{
	private CreateTripScreen5 nextTripScreen;
	private DateField startDate;
	private DateField tripDate;
	private DateField startTime;
	private DateField endTime;
	
	public CreateTripScreen4()
	{
		this.setTitle("Create Trip - Trip Info");
        
		LabelField labelTest1 = new LabelField("Trip start: " + TripData.getTripStartPlace() + "/" + TripData.getTripEndPlace(), Field.FIELD_LEFT | Field.FIELD_VCENTER);
		add(labelTest1);
		
		LabelField label1 = new LabelField("Select a trip date:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		SimpleDateFormat dateFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT);
		tripDate = new DateField("", Calendar.getInstance().getTime().getTime(), dateFormat);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(SimpleDateFormat.TIME_SHORT);
		LabelField label2 = new LabelField("Start time:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		startTime = new DateField("", Calendar.getInstance().getTime().getTime(), timeFormat);
		
		LabelField label3 = new LabelField("End time:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		endTime = new DateField("", Calendar.getInstance().getTime().getTime(), timeFormat);
		
		add(label1);
		add(tripDate);
		
		add(label2);
		add(startTime);
		
		add(label3);
		add(endTime);
		
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
		if (null == nextTripScreen)
        {
			nextTripScreen = new CreateTripScreen5();
        }
		SimpleDateFormat dateFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT);
		TripData.setTripDate(new Date(tripDate.getDate()));
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(SimpleDateFormat.TIME_SHORT);
		TripData.setTripStartTime(new Date(startTime.getDate()));
		TripData.setTripEndTime(new Date(startTime.getDate()));
    	
		UiApplication.getUiApplication().pushScreen(nextTripScreen); 
	}
	
	
}
