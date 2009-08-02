package edu.psu.sweng500.carpool.device.app.screens;

import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.controllers.CreateTripController;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripScreen4 extends MainScreen
{
	private DateField tripDate;
	private DateField startTime;
	private DateField endTime;
	
	public CreateTripScreen4()
	{
		TitleBar titleBar = new TitleBar(5);
		
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		this.setTitle(titleBar);
		
		LabelField labelTest1 = new LabelField("Trip start: " + TripData.getTripStartPlace() + "/" + TripData.getTripEndPlace(), Field.FIELD_LEFT | Field.FIELD_VCENTER);
		verticalManager.add(labelTest1);
		
		LabelField label1 = new LabelField("Select a trip date:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		SimpleDateFormat dateFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT);
		tripDate = new DateField("", Calendar.getInstance().getTime().getTime(), dateFormat)
		{
			public void paint(Graphics graphics)
	        {
	            // Sets the BackgroundColor
	            graphics.setBackgroundColor(Color.GRAY);
	            // Clears the entire graphic area to the current background
	            graphics.clear();
	            super.paint(graphics);  
	        }
		};
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(SimpleDateFormat.TIME_SHORT);
		LabelField label2 = new LabelField("Start time:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		startTime = new DateField("", Calendar.getInstance().getTime().getTime(), timeFormat)
		{
			public void paint(Graphics graphics)
	        {
	            // Sets the BackgroundColor
	            graphics.setBackgroundColor(Color.GRAY);
	            // Clears the entire graphic area to the current background
	            graphics.clear();
	            super.paint(graphics);  
	        }
		};
		
		LabelField label3 = new LabelField("End time:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		endTime = new DateField("", Calendar.getInstance().getTime().getTime(), timeFormat)
		{
			public void paint(Graphics graphics)
	        {
	            // Sets the BackgroundColor
	            graphics.setBackgroundColor(Color.GRAY);
	            // Clears the entire graphic area to the current background
	            graphics.clear();
	            super.paint(graphics);  
	        }
		};
		
		verticalManager.add(label1);
		verticalManager.add(tripDate);
		
		verticalManager.add(label2);
		verticalManager.add(startTime);
		
		verticalManager.add(label3);
		verticalManager.add(endTime);
		
		ButtonField buttonField = new ButtonField("Next >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		verticalManager.add(buttonField);
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	        	 performProcessing();
	         }
	     };
	     buttonField.setChangeListener(listener);
			
	    this.add(verticalManager);
	}
	
	public void performProcessing()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT | SimpleDateFormat.TIME_SHORT);
		Calendar cal = Calendar.getInstance();
		Date tripDateTime = new Date(tripDate.getDate());
		cal.setTime(tripDateTime);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Raw start date is: " + dateFormat.format(cal)).getBytes(), EventLogger.ALWAYS_LOG);
		TripData.setTripDate(tripDateTime);
		
		SimpleDateFormat timeFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT | SimpleDateFormat.TIME_SHORT);
		Date startDateTime = new Date(startTime.getDate());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDateTime);
		cal1.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		cal1.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal1.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		cal1.set(Calendar.SECOND, 0);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Raw start time is: " + timeFormat.format(cal1)).getBytes(), EventLogger.ALWAYS_LOG);
		TripData.setTripStartTime(cal1.getTime());
		
		Date endDateTime = new Date(endTime.getDate());
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDateTime);
		cal2.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		cal2.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		cal2.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
		cal2.set(Calendar.SECOND, 0);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Raw end time is: " + timeFormat.format(cal2)).getBytes(), EventLogger.ALWAYS_LOG);
		TripData.setTripEndTime(cal2.getTime());
    	
		CreateTripController createTripController = CreateTripController.getInstance();
		createTripController.execute(this, null);
	}
	
	public void clearFields()
	{
		tripDate.setDate(Calendar.getInstance().getTime().getTime());
		startTime.setDate(Calendar.getInstance().getTime().getTime());
		endTime.setDate(Calendar.getInstance().getTime().getTime());
	}
	
}
