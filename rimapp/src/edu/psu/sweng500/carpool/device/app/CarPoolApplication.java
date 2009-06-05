package edu.psu.sweng500.carpool.device.app;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import net.rim.device.api.ui.UiApplication;

public class CarPoolApplication extends UiApplication
{
	public CarPoolApplication()
	{
		SessionSettings.setUserName("");
		CarPoolMainScreen mainScreen = new CarPoolMainScreen();
		
		LoginScreen loginScreen = new LoginScreen(mainScreen);
		this.pushScreen(loginScreen);
	}
	
	public static void main(String[] args)
	{
		CarPoolApplication carPoolApp = new CarPoolApplication();
		carPoolApp.enterEventDispatcher();
		carPoolApp.enableKeyUpEvents(true);
	}
}
