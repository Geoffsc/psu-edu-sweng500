package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;

import com.thinkingblackberry.GridFieldManager;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.SimpleMessage;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.MainScreen;

public class LoginScreen extends MainScreen
{
	private BasicEditField userNameField;
	private PasswordEditField passwordField;
	// private ButtonField loginButton;
	
	private static PersistentObject store;
	
	private MenuItem searchMenu = new MenuItem("Login",100,10) {
       public void run() {
           login();
       } 
    };
	
	public LoginScreen()
	{
		this.setTitle("Login");
		
		Manager gridFieldManager = new GridFieldManager(new int[] {100, 160}, 30, 0);  
		// Row 1  
		gridFieldManager.add(new LabelField("User name:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		userNameField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER);
		gridFieldManager.add(userNameField);  
		   
		// Row 2  
		gridFieldManager.add(new LabelField("Password:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		passwordField = new PasswordEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER);
		gridFieldManager.add(passwordField);  
		  
		add(gridFieldManager); 
		
		ButtonField buttonField = new ButtonField("Login", Field.FIELD_HCENTER);
		add(buttonField);
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             ButtonField buttonField = (ButtonField) field;
	             login();
	         }
	     };
	     buttonField.setChangeListener(listener);
	 
		// loginButton = new ButtonField("Login", ButtonField.FIELD_HCENTER);
        // this.add(userNameField);
        // this.add(passwordField);
        // this.add(buttonField);
	}
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(searchMenu);
        menu.add(MenuItem.separator(searchMenu.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void login()
	{
		String userName = userNameField.getText().trim();
		String password = passwordField.getText().trim();
		Dialog.alert("User name: " + userName + "/Password: " + password);
		System.out.println("login: " + userName + "/" + password);
		
		SecurityService_Stub serviceStub = new SecurityService_Stub();
		try
		{
			// SimpleMessage message = serviceStub.login(userName, password);
			SimpleMessage message = serviceStub.login(userName, password);
			if (message != null)
			{
				Boolean isSuccess = message.getIsSuccess();
				if (isSuccess.booleanValue())
				{
					SessionSettings.setUserName(userName);
					System.out.println("Session user name: " + SessionSettings.getUserName());
					
					CarPoolMainScreen mainScreen = new CarPoolMainScreen();
					UiApplication.getUiApplication().pushScreen(mainScreen);
					return;
				}
			}
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		Dialog.alert("Login failed!");
	}
	
	public boolean trackwheelClick(int status,int time){
        login();
        return true;
    }
	
	protected boolean onSavePrompt() {
        return true;    // Ignore the onSave prompt.
    }

}
