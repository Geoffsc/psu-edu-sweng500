package edu.psu.sweng500.carpool.device.app.screens;

import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.controllers.CreateTripController;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripScreen5 extends MainScreen
{
	private ObjectChoiceField tripUserType;
	private BasicEditField tripTitleField;
	
	private String[] USER_TYPES = {"Driver", "Passenger"};
	
	public CreateTripScreen5()
	{
	}
	
	public void initialize()
	{
		this.deleteAll();
		//this.setTitle("Create Trip - Person Type");
		TitleBar titleBar = new TitleBar(5);
		this.setTitle(titleBar);
		
		BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
		
		LabelField mainLabel = new LabelField("Trip Details:", LabelField.READONLY)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.BLUE);
                graphics.clear();
                super.paint(graphics);
            }
		};
		verticalManager.add(mainLabel);
		verticalManager.add(new SeparatorField());
		
		LabelField label1 = new LabelField("Trip Title:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		tripTitleField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER | BasicEditField.NO_NEWLINE)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		
		verticalManager.add(label1);
		verticalManager.add(tripTitleField);
		
		LabelField label2 = new LabelField("Are you going to be:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		tripUserType = new ObjectChoiceField("", USER_TYPES, 0, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
	        {
	            // Sets the BackgroundColor
	            graphics.setBackgroundColor(Color.GRAY);
	            graphics.setColor(Color.YELLOW);
	            // Clears the entire graphic area to the current background
	            graphics.clear();
	            super.paint(graphics);  
	        }
		};
		
		verticalManager.add(label2);
		verticalManager.add(tripUserType);
		
		ButtonField buttonField = new ButtonField("Next >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		verticalManager.add(buttonField);
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             performSearch();
	         }
	     };
	     buttonField.setChangeListener(listener);
	     
	     this.add(verticalManager);
	}
	
	public void performSearch()
	{
		TripData.setTripTitle(tripTitleField.getText());
		if (tripUserType.getSelectedIndex() == 0)
		{
			TripData.setTripUserType("DRIVER");
		}
		else
		{
			TripData.setTripUserType("PASSENGER");
		}
		Hashtable parameters = new Hashtable();
		
		CreateTripController createTripController = CreateTripController.getInstance();
		createTripController.execute(this, parameters);
	}
	
	public void clearFields()
	{
		tripTitleField.setText("");
		tripUserType.setSelectedIndex(0);
	}
	
	public boolean onClose()
	{
		if (Dialog.NO == Dialog.ask(Dialog.D_YES_NO, "Are you sure you want to exit?", Dialog.YES))
		{
			return false;
		}
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}
	
}
