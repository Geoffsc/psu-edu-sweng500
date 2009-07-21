package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.collection.List;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class ShowAllResultsScreen extends MainScreen
{
	private Trip[] trips;
	private MainScreenObjectListField tripList;
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
		this.setTitle(titleBar);
		
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		tripList = new MainScreenObjectListField();
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
	
	private class MainScreenObjectListField extends ObjectListField {
        
        private Bitmap icon = Bitmap.getBitmapResource("edu/psu/sweng500/carpool/device/app/img/document.png");                
        
        // We are going to take care of drawing the item.
        public void drawListRow(ListField listField, Graphics graphics, 
                int index, int y, int width) {
            if (null != icon) {
                int offsetY = (this.getRowHeight() - icon.getHeight())/2;
                graphics.drawBitmap(1,y + offsetY, icon.getWidth(),
                        icon.getHeight(),icon,0,0);
                graphics.drawText(trips[index].getTitle() + " " + trips[index].getStartDate(), 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + trips[index].getTitle() + " " + trips[index].getStartDate(), 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
        }
        
        public void paint(Graphics graphics)
        {
            // Sets the BackgroundColor
            graphics.setBackgroundColor(Color.GRAY);
            // Clears the entire graphic area to the current background
            graphics.clear();
            super.paint(graphics);  
        }
    };
}
