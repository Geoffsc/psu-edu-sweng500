package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import com.thinkingblackberry.GridFieldManager;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.collection.List;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ActiveRichTextField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.DialogFieldManager;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class DetailsScreen extends MainScreen
{
	private String tripId;
	private DirectionsScreen directionsScreen;
	private Trip trip;
	
	VerticalFieldManager vfm;
    VerticalFieldManager v1;
    VerticalFieldManager v2;
    VerticalFieldManager v3;

	
	public DetailsScreen()
	{
		this.setTitle("Trip Details");
	}
	
	public void setTripId(String tripId)
	{
		this.tripId = tripId;
	}
	
	public void searchTrips()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting searchTrips").getBytes(), EventLogger.ALWAYS_LOG);
		this.deleteAll();
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Finished removing all items").getBytes(), EventLogger.ALWAYS_LOG);
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Adding " + tripId + " to the parameters").getBytes(), EventLogger.ALWAYS_LOG);
		/*
		Vector parameterVec = new Vector();
		Parameter param1 = new Parameter();
		param1.setName("id");
		param1.setValue(tripId);
		parameterVec.addElement(param1);
		
		Parameter[] parameters = new Parameter[parameterVec.size()];
		Enumeration paramEnum = parameterVec.elements();
		int paramCount = 0;
		while (paramEnum.hasMoreElements())
		{
			Parameter param = (Parameter) paramEnum.nextElement();
			parameters[paramCount++] = param;
		}
		*/
		
		try
		{
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			trip = serviceStub.getTrip(tripId);
			// UiApplication.getUiApplication().getActiveScreen().invalidate();

			Manager flowFieldManager = new FlowFieldManager(); 
			this.setFieldWithFocus(flowFieldManager);
			
			// Row 1
			flowFieldManager.add(new LabelField("Title:" + " " + trip.getTitle(), Field.USE_ALL_WIDTH));
			// Row 2
			flowFieldManager.add(new LabelField("Start Address:" + " " + trip.getStartAddress().getName(), Field.USE_ALL_WIDTH));
			// Row 3
			flowFieldManager.add(new LabelField("End Address:" + " " + trip.getEndAddress().getName(), Field.USE_ALL_WIDTH));
			// Row 4
			flowFieldManager.add(new LabelField("Start Date:", Field.USE_ALL_WIDTH));
			flowFieldManager.add(new LabelField(trip.getStartDate(), Field.USE_ALL_WIDTH));
			// Row 5
			flowFieldManager.add(new LabelField("End Date:", Field.USE_ALL_WIDTH));
			flowFieldManager.add(new LabelField(trip.getEndDate(), Field.USE_ALL_WIDTH));
			// Row 6
			flowFieldManager.add(new LabelField("Organization:" + " " + trip.getOrganization().getName(), Field.USE_ALL_WIDTH));
			
			add(flowFieldManager);
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
	}
	
	class TestFieldManager extends VerticalFieldManager
    {
        TestFieldManager()
        {
            super(Manager.VERTICAL_SCROLL);
        }
        protected void subLayout(int w,int height){
            int h = 5;
            for(int i=0;i<this.getFieldCount();i++){
                Field  f = this.getField(i);
                layoutChild(f,w-10,height);
                setPositionChild(f,w-5,h);
                h+=f.getHeight();
            }
            setExtent(w,h);
        }
        protected void paint(Graphics g){
            g.clear();
            int w = this.getWidth();
            int h = this.getHeight();
            //g.setColor(BLACK);
            g.drawRoundRect(1,1,w-2,h-2,10,10);
            super.paint(g);
        }
    }

	
	private MenuItem showDirectionsItem = new MenuItem("Map it!", 110, 10) {
		public void run() {
			pushDirectionsScreen();
		}
	};
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(showDirectionsItem);
        menu.add(MenuItem.separator(showDirectionsItem.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void pushDirectionsScreen()
	{
		if (directionsScreen == null)
		{
			directionsScreen = new DirectionsScreen();
		}
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting start place: " + trip.getStartAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting end place: " + trip.getEndAddress().getName()).getBytes(), EventLogger.ALWAYS_LOG);
		directionsScreen.setStartPlace(trip.getStartAddress());
		directionsScreen.setEndPlace(trip.getEndAddress());
		directionsScreen.initialize();
		UiApplication.getUiApplication().pushScreen(directionsScreen);
	}

}
