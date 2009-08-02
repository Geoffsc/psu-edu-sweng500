package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;
import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.app.beans.SavedAddressData;
import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.controllers.OptionsController;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.app.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class SelectLocationScreen1 extends MainScreen
{
	private BasicEditField titleField;
	private BasicEditField address1Field;
	private BasicEditField address2Field;
	
	public SelectLocationScreen1()
	{
		TitleBar titleBar = new TitleBar(7);
        this.add(titleBar);
        BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
        
		LabelField label1 = new LabelField("Location Title:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		titleField = new BackgroundEditField();
		
		verticalManager.add(label1);
		verticalManager.add(titleField);
		
		SeparatorField seperator1 = new SeparatorField();
		verticalManager.add(seperator1);
		
		LabelField lblAddress1 = new LabelField("Address (Line 1):", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		address1Field = new BackgroundEditField();
		
		verticalManager.add(lblAddress1);
		verticalManager.add(address1Field);
		
		SeparatorField seperator2 = new SeparatorField();
		verticalManager.add(seperator2);
		
		LabelField lblAddress2 = new LabelField("Address (Line 2):", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		address2Field = new BackgroundEditField();
		
		verticalManager.add(lblAddress2);
		verticalManager.add(address2Field);
		
		SeparatorField seperator3 = new SeparatorField();
		verticalManager.add(seperator3);
		
		ButtonField buttonField = new ButtonField("Next >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		verticalManager.add(buttonField);
		
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             performSearch();
	         }
	     };
	     buttonField.setChangeListener(listener);
	     
	     this.add(verticalManager);
	}
	
	
	private class BackgroundEditField extends BasicEditField
	{
		BackgroundEditField()
		{
	        super("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER | BasicEditField.NO_NEWLINE);
	    }
		
		public void paint(Graphics graphics)
	    {
	        graphics.setColor(Color.BLUE);
	        graphics.clear();
	        super.paint(graphics);
	    }
	}
	
	
	public void performSearch()
	{
		Hashtable screenParameters = new Hashtable();
		CarPoolService_Stub serviceStub = new CarPoolService_Stub();
		
		edu.psu.sweng500.carpool.device.services.generated.Place mapPlace = new edu.psu.sweng500.carpool.device.services.generated.Place();
		mapPlace.setId(UniqueIDGeneratorUtil.getUniqueId());
		mapPlace.setAddress1(address1Field.getText());
		mapPlace.setAddress2(address2Field.getText());
		mapPlace.setName(titleField.getText());
		
		if (SavedAddressData.getType().equalsIgnoreCase("end"))
		{
			mapPlace.setCategory("END");
		}
		else
		{
			mapPlace.setCategory("START");
		}
		
		float latFloat = Float.parseFloat(SavedAddressData.getAddressLatitude());
		float lonFloat = Float.parseFloat(SavedAddressData.getAddressLongitude());
		mapPlace.setLatitude(new Float(latFloat));
		mapPlace.setLongitude(new Float(lonFloat));
		
		edu.psu.sweng500.carpool.device.services.generated.Place[] places = new edu.psu.sweng500.carpool.device.services.generated.Place[1];
		places[0] = mapPlace;
		
		try
		{
			serviceStub.updateSavedAddresses(SessionSettings.getUserName(), places);
		}
		catch (RemoteException rme)
		{
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
			return;
		}
		
    	OptionsController controller = OptionsController.getInstance();
		controller.execute(this, screenParameters);
	}
	
	public void clearFields()
	{
		// tripTitleField.setText("");
	}
	
}
