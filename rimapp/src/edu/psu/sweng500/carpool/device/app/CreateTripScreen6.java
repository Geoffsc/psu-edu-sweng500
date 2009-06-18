package edu.psu.sweng500.carpool.device.app;

import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripScreen6 extends MainScreen
{
	private Trip[] trips;
	private MainScreenObjectListField tripList;
	private HorizontalFieldManager hfm;
	
	public CreateTripScreen6()
	{
		this.setTitle("Create Trip - Use existing?");
	}
	
	public void setTrips(Trip[] trips)
	{
		this.trips = trips;
		hfm = new HorizontalFieldManager(Manager.HORIZONTAL_SCROLL);
		tripList = new MainScreenObjectListField();
		tripList.set(trips);
        hfm.add(tripList);
        
        add(hfm);
	}
	
	public void initialize()
	{
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