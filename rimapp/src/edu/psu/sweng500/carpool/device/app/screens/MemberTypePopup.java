package edu.psu.sweng500.carpool.device.app.screens;

import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.utilities.URLUTF8Encoder;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class MemberTypePopup extends PopupScreen implements FieldChangeListener
{
	private String[] USER_TYPES = {"Driver", "Passenger"};
	private ObjectChoiceField tripUserType;
	ButtonField okButton;
    ButtonField cancelButton;
	
	public MemberTypePopup()
	{
		super(new FlowFieldManager()); 
		
		VerticalFieldManager vertManager = new VerticalFieldManager();
		LabelField label2 = new LabelField("Are you going to be:", Field.FIELD_LEFT | Field.FIELD_VCENTER | Field.USE_ALL_WIDTH);
		tripUserType = new ObjectChoiceField("", USER_TYPES, 0, Field.FIELD_LEFT | Field.FIELD_VCENTER | Field.USE_ALL_WIDTH)
		{
			public void paint(Graphics graphics)
	        {
	            // Sets the BackgroundColor
	            graphics.setBackgroundColor(Color.GRAY);
	            // Clears the entire graphic area to the current background
	            graphics.clear();
	            super.paint(graphics);  
	        }
		};
		
		vertManager.add(label2);
		vertManager.add(tripUserType);
		
		add(vertManager);
	            
	    okButton = new ButtonField("OK");
	    okButton.setChangeListener(this);
	    add(okButton);
	        
	    cancelButton = new ButtonField("Cancel");
	    cancelButton.setChangeListener(this);
	    add(cancelButton);       
	}
	
	public void fieldChanged(Field field, int context)
    {
        if (field == okButton)
        {
        	if (tripUserType.getSelectedIndex() == 0)
    		{
    			TripData.setTripUserType("DRIVER");
    		}
    		else
    		{
    			TripData.setTripUserType("PASSENGER");
    		}
        }
        
        
        if (field == cancelButton)
        {
        	TripData.setTripUserType("");
        }
        
        UiApplication.getUiApplication().invokeLater(new Runnable()
        {                
         public void run()
          {
               close();
           }
         });
    }
}
