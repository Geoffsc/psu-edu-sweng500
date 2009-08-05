package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Participant;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.NullField;
import net.rim.device.api.ui.container.FlowFieldManager;
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
		//Do Nothing
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
		    TitleBar titleBar = new TitleBar(4);
		    this.add(titleBar);
			
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			trip = serviceStub.getTrip(tripId);
			// UiApplication.getUiApplication().getActiveScreen().invalidate();
			
			BackgroundFieldManager backgroupLayOut = new BackgroundFieldManager(titleBar.getPreferredHeight());
			// Manager flowFieldManager = new FlowFieldManager(Manager.VERTICAL_SCROLLBAR); 
			this.setFieldWithFocus(backgroupLayOut);
			backgroupLayOut.add(new NullField());
			
			// Row 1
			backgroupLayOut.add(
				new LabelField("Title:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			backgroupLayOut.add(new LabelField(trip.getTitle(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 2
			backgroupLayOut.add(
				new LabelField("Start Address:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			backgroupLayOut.add(new LabelField(trip.getStartAddress().getName(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 3
			backgroupLayOut.add(
				new LabelField("End Address:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			backgroupLayOut.add(new LabelField(trip.getEndAddress().getName(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 4
			backgroupLayOut.add(
				new LabelField("Start Date:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// backgroupLayOut.add(new LabelField("Start Date:", Field.USE_ALL_WIDTH));
			backgroupLayOut.add(new LabelField(trip.getStartDate(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 5
			backgroupLayOut.add(
				new LabelField("End Date:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// backgroupLayOut.add(new LabelField("End Date:", Field.USE_ALL_WIDTH));
			backgroupLayOut.add(new LabelField(trip.getEndDate(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 6
			backgroupLayOut.add(
				new LabelField("Organization:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			backgroupLayOut.add(new LabelField(trip.getOrganization().getName(), Field.USE_ALL_WIDTH)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.YELLOW);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			// Row 7
			backgroupLayOut.add(
				new LabelField("Members:", LabelField.READONLY)
				{
					public void paint(Graphics graphics)
		            {
		                graphics.setColor(Color.BLACK);
		                graphics.clear();
		                super.paint(graphics);
		            }
				}
			);
			Participant[] tripParticipants = trip.getParticipants();
			for (int i = 0; i < tripParticipants.length; i++) {
				Participant participant = tripParticipants[i];
				backgroupLayOut.add(new LabelField(participant.getUser().getGivenName() + " " + participant.getUser().getSurName() + " - " + 
						participant.getType(), Field.USE_ALL_WIDTH)
					{
						public void paint(Graphics graphics)
			            {
			                graphics.setColor(Color.YELLOW);
			                graphics.clear();
			                super.paint(graphics);
			            }
					}
				);
			}
			
			backgroupLayOut.add(new NullField());
			
			// backgroupLayOut.add(flowFieldManager);
			this.add(backgroupLayOut);
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
	}
    
/*	
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
*/
	
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
