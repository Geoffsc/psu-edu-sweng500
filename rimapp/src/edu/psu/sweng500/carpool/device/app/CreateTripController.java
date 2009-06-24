package edu.psu.sweng500.carpool.device.app;

import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.services.generated.Trip;

import net.rim.device.api.collection.util.BigVector;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripController
{
	private CreateTripScreen1 createTripScreen1;
	private CreateTripScreen2 createTripScreen2;
	private CreateTripScreen4 createTripScreen4;
	private CreateTripScreen5 createTripScreen5;
	private CreateTripScreen6 createTripScreen6;
	private CreateTripScreen7 createTripScreen7;
	private CreateTripScreen8 createTripScreen8;
	
	private static CreateTripController INSTANCE;
	
	private CreateTripController()
	{}
	
	public static CreateTripController getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new CreateTripController();
		}
		return INSTANCE;
	}
	
	public void execute(MainScreen currentScreen, Hashtable parameters)
	{
		if (currentScreen instanceof CarPoolMainScreen)
		{
			if (createTripScreen1 == null)
			{
				createTripScreen1 = new CreateTripScreen1();
			}
			UiApplication.getUiApplication().pushScreen(createTripScreen1);
			return;
		}
		
		String type = CreateTripController.getStringParameter(parameters, "type");
		if (currentScreen instanceof CreateTripScreen1)
		{
			if (createTripScreen2 == null)
			{
				createTripScreen2 = new CreateTripScreen2();
			}
			
			Float latitude = (Float) parameters.get("latitude");
			createTripScreen2.setStartLat(latitude.doubleValue());
			Float longitude = (Float) parameters.get("longitude");
			createTripScreen2.setStartLon(longitude.doubleValue());
			createTripScreen2.setType("START");
			createTripScreen2.initialize();
			
			UiApplication.getUiApplication().popScreen(createTripScreen1);
			UiApplication.getUiApplication().pushScreen(createTripScreen2);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen2 && type != null && type.equalsIgnoreCase("start"))
		{
			if (createTripScreen2 == null)
			{
				createTripScreen2 = new CreateTripScreen2();
			}
			
			Float latitude = (Float) parameters.get("latitude");
			createTripScreen2.setStartLat(latitude.doubleValue());
			Float longitude = (Float) parameters.get("longitude");
			createTripScreen2.setStartLon(longitude.doubleValue());
			createTripScreen2.setType("END");
			
			createTripScreen2.initialize();
			
			// UiApplication.getUiApplication().pushScreen(createTripScreen2);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen2 && type != null && type.equalsIgnoreCase("end"))
		{
			if (createTripScreen4 == null)
			{
				createTripScreen4 = new CreateTripScreen4();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen2);
			UiApplication.getUiApplication().pushScreen(createTripScreen4);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen4)
		{
			if (createTripScreen5 == null)
			{
				createTripScreen5 = new CreateTripScreen5();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen4);
			UiApplication.getUiApplication().pushScreen(createTripScreen5);
			return;
		}
		
		String foundMatchingTrip = CreateTripController.getStringParameter(parameters, "foundMatchingTrip");
		if (currentScreen instanceof CreateTripScreen5 && foundMatchingTrip != null && foundMatchingTrip.equalsIgnoreCase("true"))
		{
			if (createTripScreen6 == null)
			{
				createTripScreen6 = new CreateTripScreen6();
			}
			if (CreateTripController.hasParameter(parameters, "trips"))
			{
				Trip[] trips = (Trip[]) parameters.get("trips");
				createTripScreen6.setTrips(trips);
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen5);
			UiApplication.getUiApplication().pushScreen(createTripScreen6);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen5)
		{
			if (createTripScreen7 == null)
			{
				createTripScreen7 = new CreateTripScreen7();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen5);
			UiApplication.getUiApplication().pushScreen(createTripScreen7);
			return;
		}
		
		String tripSaved = CreateTripController.getStringParameter(parameters, "tripSaved");
		if (currentScreen instanceof CreateTripScreen6 && tripSaved != null && tripSaved.equalsIgnoreCase("true"))
		{
			if (createTripScreen6 == null)
			{
				createTripScreen6 = new CreateTripScreen6();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen6);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen6)
		{
			if (createTripScreen7 == null)
			{
				createTripScreen7 = new CreateTripScreen7();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen6);
			UiApplication.getUiApplication().pushScreen(createTripScreen7);
			return;
		}
		
		String searchSimilar = CreateTripController.getStringParameter(parameters, "searchSimilar");
		if (currentScreen instanceof CreateTripScreen7 && searchSimilar != null && searchSimilar.equalsIgnoreCase("true"))
		{
			if (createTripScreen6 == null)
			{
				createTripScreen6 = new CreateTripScreen6();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen7);
			UiApplication.getUiApplication().pushScreen(createTripScreen6);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen7)
		{
			if (createTripScreen8 == null)
			{
				createTripScreen8 = new CreateTripScreen8();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen7);
			UiApplication.getUiApplication().pushScreen(createTripScreen8);
			return;
		}
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
