package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.User;
import edu.psu.sweng500.security.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.security.device.services.generated.SimpleMessage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.util.NumberUtilities;

public class PreferencesScreen extends MainScreen
{
	private BasicEditField preferredDistanceField;
	
	private MenuItem searchMenu = new MenuItem("Save",100,10) {
       public void run() {
           saveUser();
       } 
    };
	
    
	public PreferencesScreen()
	{
		this.deleteAll();
		
		String initialPreferredDistance = "";
		CarPoolService_Stub serviceStub = new CarPoolService_Stub();
		try
		{
			User theUser = serviceStub.getUser(SessionSettings.getUserName());
			if (theUser != null)
			{
				initialPreferredDistance = theUser.getPreferredDistance().toString();
			}
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
        
        //title Bar added here
	    TitleBar titleBar = new TitleBar(0);
        this.add(titleBar);
        BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
        
        // Row 1  
        verticalManager.add(new LabelField("Preferred distance for searching:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
        preferredDistanceField = new BasicEditField("", initialPreferredDistance, 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		
		SeparatorField seperator1 = new SeparatorField();
		verticalManager.add(seperator1);
		verticalManager.add(preferredDistanceField);
		
		ButtonField buttonField = new ButtonField("Save", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
        verticalManager.add(buttonField);
        
        //and at last add  verticalManager in screen   
        this.add(verticalManager);
	
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             saveUser();
	         }
	     };
	     buttonField.setChangeListener(listener);
	 
	}
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(searchMenu);
        menu.add(MenuItem.separator(searchMenu.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void saveUser()
	{
		String preferredDistance = preferredDistanceField.getText().trim();
		// Dialog.alert("User name: " + userName + "/Password: " + password);
		System.out.println("distance: " + preferredDistance);
		
		int distance = 0;
		StringBuffer errorMessages = new StringBuffer();
		try
		{
			distance = Integer.parseInt(preferredDistance);
			if (distance < 0)
			{
				errorMessages.append("- Preferred distance is invalid (must be > 0)\n");
			}
		}
		catch (NumberFormatException nfe)
		{
			errorMessages.append("- Preferred distance is invalid\n");
		}
		
		if (errorMessages != null)
		{
			String errorMessagesString = errorMessages.toString();
			if (errorMessagesString.trim().length() > 0)
			{
				Dialog.alert("The following error(s) occurred:\n\n" + errorMessagesString);
				return;
			}
		}
		
		CarPoolService_Stub serviceStub = new CarPoolService_Stub();
		try
		{
			User theUser = serviceStub.getUser(SessionSettings.getUserName());
			if (theUser != null)
			{
				theUser.setPreferredDistance(new Integer(distance));
				edu.psu.sweng500.carpool.device.services.generated.SimpleMessage message = serviceStub.updateUser(theUser.getId(), theUser);
				if (message.getIsSuccess().booleanValue())
				{
					Dialog.alert("Preferences updated!");
					UiApplication.getUiApplication().popScreen(this);
				}
				else
				{
					Dialog.alert("Preferences not updated!");
				}
			}
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
	}
	
	public boolean trackwheelClick(int status,int time){
        saveUser();
        return true;
    }
	
	protected boolean onSavePrompt() {
        return true;    // Ignore the onSave prompt.
    }

}
