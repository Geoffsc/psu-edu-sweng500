package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;

import edu.psu.sweng500.carpool.device.app.fields.TitleBar;
import edu.psu.sweng500.carpool.device.app.managers.BackgroundFieldManager;
import edu.psu.sweng500.carpool.device.app.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.security.device.services.generated.Organization;
import edu.psu.sweng500.security.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.security.device.services.generated.SimpleMessage;
import edu.psu.sweng500.security.device.services.generated.User;
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

public class RegisterUserScreen extends MainScreen
{
	private BasicEditField givenNameField;
	private BasicEditField surNameField;
	private BasicEditField userNameField;
	private PasswordEditField passwordField;
	private PasswordEditField rePasswordField;
	// private ButtonField loginButton;
	
	private MenuItem searchMenu = new MenuItem("Login",100,10) {
       public void run() {
           registerUser();
       } 
    };
	
    
	public RegisterUserScreen()
	{        
        //title Bar added here
	    TitleBar titleBar = new TitleBar(0);
        this.add(titleBar);
        BackgroundFieldManager verticalManager = new BackgroundFieldManager(titleBar.getPreferredHeight());
        
        verticalManager.add(new LabelField("First name:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		givenNameField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		verticalManager.add(givenNameField);
		verticalManager.add(new SeparatorField());
		
		verticalManager.add(new LabelField("Last name:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		surNameField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		verticalManager.add(surNameField);
		verticalManager.add(new SeparatorField());
        
        // Row 1  
        verticalManager.add(new LabelField("User name:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		userNameField = new BasicEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
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
		verticalManager.add(userNameField);
		
		SeparatorField seperator2 = new SeparatorField();
		verticalManager.add(seperator2);
		
		// Row 2  
		verticalManager.add(new LabelField("Password:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		passwordField = new PasswordEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		SeparatorField seperator4 = new SeparatorField();
		verticalManager.add(seperator4);
		verticalManager.add(passwordField);
		
		verticalManager.add(new LabelField("Re-enter password:", Field.FIELD_LEFT | Field.FIELD_VCENTER));
		rePasswordField = new PasswordEditField("", "", 200, Field.FIELD_LEFT | Field.FIELD_VCENTER)
		{
			public void paint(Graphics graphics)
            {
                graphics.setColor(Color.YELLOW);
                graphics.clear();
                super.paint(graphics);
            }
		};
		
		verticalManager.add(new SeparatorField());
		verticalManager.add(rePasswordField);
		
        ButtonField buttonField = new ButtonField("Login", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
        verticalManager.add(buttonField);
        
        //and at last add  verticalManager in screen   
        this.add(verticalManager);
	
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             registerUser();
	         }
	     };
	    buttonField.setChangeListener(listener);
	 
		givenNameField.setFocus();
	}
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(searchMenu);
        menu.add(MenuItem.separator(searchMenu.getOrdinal() + 1));
        super.makeMenu(menu,instance);
    }
	
	private void registerUser()
	{
		String givenName = givenNameField.getText().trim();
		String surName = surNameField.getText().trim();
		String userName = userNameField.getText().trim();
		String password = passwordField.getText().trim();
		String rePassword = rePasswordField.getText().trim();
		// Dialog.alert("User name: " + userName + "/Password: " + password);
		System.out.println("login: " + userName + "/" + password);
		
		StringBuffer errorMessages = new StringBuffer();
		if (givenName == null || givenName.equals(""))
		{
			errorMessages.append("- First name is required\n");
		}
		if (surName == null || surName.equals(""))
		{
			errorMessages.append("- Last name is required\n");
		}
		if (userName == null || userName.equals(""))
		{
			errorMessages.append("- User name is required\n");
		}
		if (password == null || password.equals(""))
		{
			errorMessages.append("- Password is required\n");
		}
		if (rePassword == null || rePassword.equals(""))
		{
			errorMessages.append("- Re-entered password is required\n");
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
		
		if (!password.equals(rePassword))
		{
			Dialog.alert("The password and re-entered password must match!");
			return;
		}
		
		SecurityService_Stub serviceStub = new SecurityService_Stub();
		try
		{
			User serviceUser = new User();
			serviceUser.setGivenName(givenName);
			serviceUser.setSurName(surName);
			serviceUser.setId(UniqueIDGeneratorUtil.getUniqueId());
			serviceUser.setUserName(userName);
			serviceUser.setPassword(password);
			
			Organization org = new Organization();
			org.setId("12345");
			serviceUser.setOrganization(org);
			
			SimpleMessage message = serviceStub.registerUser(serviceUser);
			if (message != null)
			{
				Boolean isSuccess = message.getIsSuccess();
				if (isSuccess.booleanValue())
				{
					Dialog.alert("Account created!  Please login.");
					UiApplication.getUiApplication().popScreen(this);
					
					return;
				}
				else
				{
					Dialog.alert("The following error(s) occurred:\n\n" + message.getErrorDescription());
					return;
				}
			}
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());
		}
		Dialog.alert("Registration failed!");
	}
	
	public boolean trackwheelClick(int status,int time){
        registerUser();
        return true;
    }
	
	protected boolean onSavePrompt() {
        return true;    // Ignore the onSave prompt.
    }

}
