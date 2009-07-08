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
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class DetailsScreen extends MainScreen
{
	private String tripId;
	
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
		this.deleteAll();
		
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
		
		try
		{
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			Trip trip = serviceStub.getTrip(tripId);
			// UiApplication.getUiApplication().getActiveScreen().invalidate();
			
			Manager gridFieldManager = new GridFieldManager(new int[] {100, 160}, 30, 0);  
			// Row 1  
			gridFieldManager.add(new LabelField("Title:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
			gridFieldManager.add(new LabelField(trip.getTitle(), Field.FIELD_LEFT | Field.FIELD_VCENTER));
			
			add(gridFieldManager);
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
	}

}
