package edu.psu.sweng500.carpool.device.app.fields;

import java.util.Calendar;
import java.util.Date;

import edu.psu.sweng500.carpool.device.services.generated.Trip;
import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;

public class DocumentObjectListField extends ObjectListField
{
	private Bitmap icon = Bitmap.getBitmapResource("edu/psu/sweng500/carpool/device/app/img/document.png");
	DateFormat format = SimpleDateFormat.getInstance(DateFormat.DATE_SHORT | DateFormat.TIME_LONG);
	
	public void set(Trip[] list)
	{
		super.set(list);
	}
    
    // We are going to take care of drawing the item.
    public void drawListRow(ListField listField, Graphics graphics, 
            int index, int y, int width)
    {
    	if (this.get(listField, index) instanceof Trip)
    	{
	    	Trip trip = (Trip) this.get(listField, index);	    	
	    	
	        if (null != icon) {
	            // int offsetY = (this.getRowHeight() - icon.getHeight())/2;
	            // graphics.drawBitmap(1,y + offsetY, icon.getWidth(),
	                    // icon.getHeight(),icon,0,0);
	            graphics.drawBitmap(1,y, icon.getWidth(),
	                    icon.getHeight(),icon,0,0);
	            graphics.drawText(trip.getTitle() + " " + trip.getStartDate(), 
	                icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
	        } else {
	            graphics.drawText("- " + trip.getTitle() + " " + format.format(trip.getStartDate()), 0,
	                y, DrawStyle.ELLIPSIS, width);
	        }
    	}
    	else if (this.get(listField, index) instanceof String)
    	{
    		String menuItem = (String) this.get(listField, index);
    		if (null != icon) {
                // int offsetY = (this.getRowHeight() - icon.getHeight())/2;
                // graphics.drawBitmap(1,y + offsetY, icon.getWidth(),
                        // icon.getHeight(),icon,0,0);
                graphics.drawBitmap(1,y, icon.getWidth(),
                        icon.getHeight(),icon,0,0);
                graphics.drawText(menuItem, 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + menuItem, 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
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
}
