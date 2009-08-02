package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import edu.psu.sweng500.carpool.device.app.fields.DocumentObjectListField;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

public class ShowAllResultsScreen extends MainScreen
{
	private Trip[] trips;
	private DocumentObjectListField tripList;
	DetailsScreen detailsScreen = null;
	private String userName;
	
	private Date searchStartDate;
    
	public void setSearchStartDate(Date searchStartDate)
	{
		this.searchStartDate = searchStartDate;
	}
	
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public ShowAllResultsScreen()
	{
		TitleBar titleBar = new TitleBar(3);
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		this.setTitle(titleBar);
		
		tripList = new DocumentObjectListField();
		tripList.setRowHeight(25);
        verticalManager.add(tripList);
        add(verticalManager);
	}
	
	public void searchTrips()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting searchTrips").getBytes(), EventLogger.ALWAYS_LOG);
		/*
		CarPoolService_Stub service = new CarPoolService_Stub();
		try
		{
			trips = service.getTripsByUser(SessionSettings.getUserName());
			
			tripList.set(trips);
			UiApplication.getUiApplication().getActiveScreen().invalidate();
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		*/
		
		Vector parameterVec = new Vector();
		
		if (this.userName != null && this.userName.trim().length() != 0)
		{
			Parameter param1 = new Parameter();
			param1.setName("userName");
			param1.setValue(this.userName);
			parameterVec.addElement(param1);
			// parameters[0] = param1;
		}
		
		if (searchStartDate != null)
		{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			Parameter param2 = new Parameter();
			param2.setName("startDate");
			param2.setValue(format.format(searchStartDate));
			// parameters[1] = param2;
			parameterVec.addElement(param2);
		}
		
		Parameter[] parameters = new Parameter[parameterVec.size()];
		Enumeration paramEnum = parameterVec.elements();
		int paramCount = 0;
		while (paramEnum.hasMoreElements())
		{
			Parameter param = (Parameter) paramEnum.nextElement();
			parameters[paramCount++] = param;
		}
		
		try
		{
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Searching for trips").getBytes(), EventLogger.ALWAYS_LOG);
			trips = serviceStub.findTrips(parameters);
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Done searching for trips").getBytes(), EventLogger.ALWAYS_LOG);
			
			tripList.set(trips);
			UiApplication.getUiApplication().getActiveScreen().invalidate();
		}
		catch (RemoteException rme)
		{	
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = tripList.getSelectedIndex();
        EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Selected item was " + selected).getBytes(), EventLogger.ALWAYS_LOG);
        Trip selectedTrip = trips[selected];
        
        if (selectedTrip != null)
        {
	        if (detailsScreen == null)
	        {
	        	detailsScreen = new DetailsScreen();
	        }
	        detailsScreen.setTripId(selectedTrip.getId());
	        detailsScreen.searchTrips();
			UiApplication.getUiApplication().pushScreen(detailsScreen);
        }
        
        return true;
    }
	
}
