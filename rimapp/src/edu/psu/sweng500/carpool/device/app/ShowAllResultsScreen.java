package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.MainScreen;

public class ShowAllResultsScreen extends MainScreen
{
	private Trip[] trips;
	private MainScreenObjectListField tripList;
	
	public ShowAllResultsScreen()
	{
		this.setTitle("Results");                   
		tripList = new MainScreenObjectListField();
        this.add(tripList); 
	}
	
	public void searchTrips()
	{
		CarPoolService_Stub service = new CarPoolService_Stub();
		try
		{			
			trips = service.getTripsByUser(SessionSettings.getUserName());
			
			tripList.set(trips);
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
                graphics.drawText(trips[index].getTitle() + " " + trips[index].getStartDate(), 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + trips[index].getTitle() + " " + trips[index].getStartDate(), 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
        }
    };

}
