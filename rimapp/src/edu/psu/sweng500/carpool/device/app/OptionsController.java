package edu.psu.sweng500.carpool.device.app;

import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.services.generated.Trip;

import net.rim.device.api.collection.util.BigVector;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

public class OptionsController
{
	private CarPoolOptionsScreen carPoolOptionsScreen;
	private SelectLocationScreen selectLocationScreen;
	private SelectLocationScreen1 selectLocationScreen1;
	private CarPoolMainScreen carPoolMainScreen;
	
	private static OptionsController INSTANCE;
	
	private OptionsController()
	{}
	
	public static OptionsController getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new OptionsController();
		}
		return INSTANCE;
	}
	
	public void execute(MainScreen currentScreen, Hashtable parameters)
	{
		if (currentScreen instanceof CarPoolMainScreen)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CarPoolMainScreen to OptionsScreen: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (carPoolOptionsScreen == null)
			{
				carPoolOptionsScreen = new CarPoolOptionsScreen();
			}
			UiApplication.getUiApplication().pushScreen(carPoolOptionsScreen);
			return;
		}
		
		if (currentScreen instanceof CarPoolOptionsScreen)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CarPoolOptionsScreen to SelectLocationScreen: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (selectLocationScreen != null)
			{
				selectLocationScreen = null;
			}
			if (selectLocationScreen == null)
			{
				selectLocationScreen = new SelectLocationScreen();
			}
			UiApplication.getUiApplication().pushScreen(selectLocationScreen);
			UiApplication.getUiApplication().popScreen(currentScreen);
			return;
		}
		
		if (currentScreen instanceof SelectLocationScreen)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from SelectLocationScreen to SelectLocationScreen1: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (selectLocationScreen1 == null)
			{
				selectLocationScreen1 = new SelectLocationScreen1();
			}
			UiApplication.getUiApplication().pushScreen(selectLocationScreen1);
			UiApplication.getUiApplication().popScreen(currentScreen);
			return;
		}
		
		if (currentScreen instanceof SelectLocationScreen1)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from SelectLocationScreen1 to CarPoolMainScreen: ").getBytes(), EventLogger.ALWAYS_LOG);
			/*
			if (carPoolMainScreen == null)
			{
				carPoolMainScreen = new CarPoolMainScreen();
			}
			UiApplication.getUiApplication().pushScreen(carPoolMainScreen);
			*/
			UiApplication.getUiApplication().popScreen(currentScreen);
			return;
		}
		
	}
	
	private void clearScreen()
	{
	}
	
	private static boolean hasParameter(Hashtable parameters, String parameterName)
	{
		if (parameters == null)
		{
			return false;
		}
		
		if (parameters.containsKey(parameterName))
		{
			return true;
		}
		return false;
	}
	
	private static String getStringParameter(Hashtable parameters, String parameterName)
	{
		if (parameters == null)
		{
			return null;
		}
		if (!parameters.containsKey(parameterName))
		{
			return null;
		}
		return (String) parameters.get(parameterName);
	}
	
}
