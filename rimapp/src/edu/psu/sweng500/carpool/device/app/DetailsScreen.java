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
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class DetailsScreen extends MainScreen
{
	private Trip trips;
	private MainScreenObjectListField tripList;
	private HorizontalFieldManager hfm;
	private String id;
	
	public DetailsScreen()
	{
		this.setTitle("Trip Details");
		hfm = new HorizontalFieldManager(Manager.HORIZONTAL_SCROLL);
		tripList = new MainScreenObjectListField();
        hfm.add(tripList);
        
        add(hfm);
	}
	
	public void searchTrips()
	{
		Vector parameterVec = new Vector();
		Parameter param1 = new Parameter();
		param1.setName("id");
		param1.setValue(trips.getId());
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
			trips = serviceStub.getTrip(param1.toString());
			UiApplication.getUiApplication().getActiveScreen().invalidate();
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
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
                graphics.drawText(trips.getTitle() + "\n" + trips.getStartDate() + "\n" + trips.getEndDate() + "\n" + trips.getStartAddress() + "\n" + trips.getOrganization() + "\n" + trips.getOrganization(), 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + trips.getTitle() + " " + trips.getStartDate() + "\n" + trips.getEndDate() + "\n" + trips.getStartAddress() + "\n" + trips.getOrganization() + "\n" + trips.getOrganization(), 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
        }
    };

}
