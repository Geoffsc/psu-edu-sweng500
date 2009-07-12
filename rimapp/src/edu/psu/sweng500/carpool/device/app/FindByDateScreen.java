package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.ChoiceField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.MainScreen;

public class FindByDateScreen extends MainScreen
{
	private String[] CHOICE_MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
	private String[] CHOICE_DAYS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
	
	private ShowAllResultsScreen showAllScreen;
	private DateField startDate;
	private String userName;
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public FindByDateScreen()
	{
		this.setTitle("Search");
        
		SimpleDateFormat dateFormat = new SimpleDateFormat(SimpleDateFormat.DATE_SHORT);
		startDate = new DateField("", Calendar.getInstance().getTime().getTime(), dateFormat);
		
		Date theDate = new Date(startDate.getDate());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Dialog.alert(format.format(theDate));
		
		this.add(startDate);
		// ObjectChoiceField monthChoice = new ObjectChoiceField("", CHOICE_MONTHS);
		// ObjectChoiceField dayChoice = new ObjectChoiceField("", CHOICE_DAYS);
		
		// this.add(monthChoice);
		// this.add(dayChoice);
		
		ButtonField buttonField = new ButtonField("Search", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
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
		if (null == showAllScreen)
        {
    		showAllScreen = new ShowAllResultsScreen();
        }
    	
		Date theDate = new Date(startDate.getDate());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Dialog.alert(format.format(theDate));
		
		showAllScreen.setSearchStartDate(theDate);
		showAllScreen.setUserName(this.userName);
    	showAllScreen.searchTrips();
    	UiApplication.getUiApplication().pushScreen(showAllScreen); 
	}
	
	public boolean isDirty()
	{
		return false;
	}
	
	
}
