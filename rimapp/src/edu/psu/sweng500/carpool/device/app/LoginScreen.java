package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.SimpleMessage;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.container.MainScreen;

public class LoginScreen extends MainScreen
{
	private EditField userNameField;
	private PasswordEditField passwordField;
	
	private static PersistentObject store;
	
	private MenuItem searchMenu = new MenuItem("Login",100,10) {
       public void run() {
           login();
       } 
    };
	
	public LoginScreen()
	{
		this.setTitle("Login");
		userNameField = new EditField("User name:" ,"" ,256 ,EditField.USE_ALL_WIDTH);
		passwordField = new PasswordEditField("Password:", "", 256, PasswordEditField.USE_ALL_WIDTH);
        this.add(userNameField);
        this.add(passwordField);
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
	
	protected boolean onSavePrompt() {
        return true;    // Ignore the onSave prompt.
    }
}
