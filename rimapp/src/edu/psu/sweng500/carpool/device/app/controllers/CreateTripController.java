package edu.psu.sweng500.carpool.device.app.controllers;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.screens.CarPoolMainScreen;
import edu.psu.sweng500.carpool.device.app.screens.CreateTripScreen2;
import edu.psu.sweng500.carpool.device.app.screens.CreateTripScreen4;
import edu.psu.sweng500.carpool.device.app.screens.CreateTripScreen5;
import edu.psu.sweng500.carpool.device.app.screens.CreateTripScreen6;
import edu.psu.sweng500.carpool.device.app.screens.CreateTripScreen8;
import edu.psu.sweng500.carpool.device.app.screens.DirectionsScreen;
import edu.psu.sweng500.carpool.device.app.utilities.ApplicationConstants;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Parameter;
import edu.psu.sweng500.carpool.device.services.generated.Place;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import edu.psu.sweng500.carpool.device.services.generated.User;

import net.rim.device.api.collection.util.BigVector;
import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

public class CreateTripController
{
	private CreateTripScreen2 createTripScreen2;
	private CreateTripScreen4 createTripScreen4;
	private CreateTripScreen5 createTripScreen5;
	private CreateTripScreen6 createTripScreen6;
	private CreateTripScreen8 createTripScreen8;
	private DirectionsScreen directionsScreen;
	
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
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CarPoolMainScreen to CreateTripScreen2: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen2 == null)
			{
				createTripScreen2 = new CreateTripScreen2();
			}
			
			// set the default latitude and longitude
			double latitude = ApplicationConstants.DEFAULT_LATITUDE;
			double longitude = ApplicationConstants.DEFAULT_LONGITUDE;
			
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			try
			{
				User theUser = serviceStub.getUser(SessionSettings.getUserName());
				if (theUser != null)
				{
					Place[] places = theUser.getSavedPlaces();
					if (places != null)
					{
						// use the START place from the user as the start point
						boolean notFound = true;
						for (int i = 0; i < places.length && notFound; i++)
						{
							Place thePlace = places[i];
							if (thePlace.getCategory().equalsIgnoreCase("start"))
							{
								System.out.println("Found a location for the start place");
								notFound = false;
								EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming latitude is: " + thePlace.getLatitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
								EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming longitude is: " + thePlace.getLongitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
								latitude = thePlace.getLatitude().doubleValue();
								longitude = thePlace.getLongitude().doubleValue();
							}
						}
					}
				}
			}
			catch (RemoteException rme)
			{
				Dialog.alert("A remote exception occurred: " + rme.getMessage());
			}
			
			createTripScreen2.setStartLat(latitude);
			createTripScreen2.setStartLon(longitude);
			createTripScreen2.setType("START");
			createTripScreen2.initialize();
			
			UiApplication.getUiApplication().pushScreen(createTripScreen2);
			return;
		}
		
		String type = CreateTripController.getStringParameter(parameters, "type");
		if (currentScreen instanceof CreateTripScreen2 && type != null && type.equalsIgnoreCase("start"))
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen2 (start) to CreateTripScreen2 (end): ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen2 == null)
			{
				createTripScreen2 = new CreateTripScreen2();
			}
			
			// set the default latitude and longitude
			double latitude = ApplicationConstants.DEFAULT_LATITUDE;
			double longitude = ApplicationConstants.DEFAULT_LONGITUDE;
			
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			try
			{
				User theUser = serviceStub.getUser(SessionSettings.getUserName());
				if (theUser != null)
				{
					edu.psu.sweng500.carpool.device.services.generated.Place[] places = theUser.getSavedPlaces();
					if (places != null)
					{
						// use the END place from the user as the start point
						boolean notFound = true;
						for (int i = 0; i < places.length && notFound; i++)
						{
							edu.psu.sweng500.carpool.device.services.generated.Place thePlace = places[i];
							if (thePlace.getCategory().equalsIgnoreCase("end"))
							{
								System.out.println("Found a location for the end place");
								notFound = false;
								EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming latitude is: " + thePlace.getLatitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
								EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming longitude is: " + thePlace.getLongitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
								latitude = thePlace.getLatitude().doubleValue();
								longitude = thePlace.getLongitude().doubleValue();
							}
						}
					}
				}
			}
			catch (RemoteException rme)
			{
				Dialog.alert("A remote exception occurred: " + rme.getMessage());
			}
			
			createTripScreen2.setStartLat(latitude);
			createTripScreen2.setStartLon(longitude);
			createTripScreen2.setType("END");
			createTripScreen2.initialize();
			
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen2 && type != null && type.equalsIgnoreCase("end"))
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen2 (end) to CreateTripScreen4: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen4 == null)
			{
				createTripScreen4 = new CreateTripScreen4();
			}
			createTripScreen4.initialize();
			
			UiApplication.getUiApplication().popScreen(createTripScreen2);
			UiApplication.getUiApplication().pushScreen(createTripScreen4);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen4)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen4 to CreateTripScreen5: ").getBytes(), EventLogger.ALWAYS_LOG);
			
			CarPoolService_Stub service = new CarPoolService_Stub();
			String startAddress = TripData.getTripStartPlace();
			String endAddress = TripData.getTripEndPlace();
			
			// format the start and end date for the search
			Date startDate = TripData.getTripStartTime();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date endDate = TripData.getTripEndTime();
			int numMiles = 0;
			String latitude = "";
			String longitude = "";
			try
			{
				User theUser = service.getUser(SessionSettings.getUserName());
				if (theUser != null)
				{
					numMiles = theUser.getPreferredDistance().intValue();
				}
				
				boolean tripsFound = false;
				dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				
				Parameter[] params = new Parameter[5];
				Parameter param = new Parameter();
				param.setName("startAddress");
				param.setValue(startAddress);
				params[0] = param;
				
				param = new Parameter();
				param.setName("endAddress");
				param.setValue(endAddress);
				params[1] = param;
				
				param = new Parameter();
				param.setName("startDate");
				param.setValue(dateFormat.format(startDate));
				params[2] = param;
				
				param = new Parameter();
				param.setName("endDate");
				param.setValue(dateFormat.format(endDate));
				params[3] = param;
				
				param = new Parameter();
				param.setName("organization");
				param.setValue("12345");
				params[4] = param;
				
				Trip[] trips = service.findTrips(params);
				// Trip[] trips = service.findSimilarTrips("", "", startAddress, endAddress, dateFormat.format(startDate), dateFormat.format(endDate), latitude, longitude, "12345", numMiles);
				if (trips != null && trips.length > 0)
				{
					tripsFound = true;
					if (createTripScreen6 == null)
					{
						createTripScreen6 = new CreateTripScreen6();
					}
					createTripScreen6.setState("");
				}
				
				if (!tripsFound)
				{
					if (Dialog.YES == Dialog.ask(Dialog.D_YES_NO, "Would you like to check for similar trips on the same day?", Dialog.YES))
					{
						if (createTripScreen6 == null)
						{
							createTripScreen6 = new CreateTripScreen6();
						}
						createTripScreen6.setState("similarSearch");
						
						dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						
						trips = service.findSimilarTrips("", "", startAddress, endAddress, dateFormat.format(startDate), dateFormat.format(startDate), latitude, longitude, "12345", numMiles);
						if (trips != null && trips.length > 0)
						{
							tripsFound = true;
						}
					}
				}
				
				if (tripsFound)
				{
					// add the Create New option
					BigVector tripVector = new BigVector();
					Trip createTrip = new Trip();
					createTrip.setId("create");
					createTrip.setTitle("* Create New *");
					
					tripVector.addElement(createTrip);
					tripVector.addElements(trips);
					
					trips = new Trip[tripVector.size()];
					tripVector.copyInto(0, tripVector.size(), trips, 0);
		        	
					if (createTripScreen6 == null)
					{
						createTripScreen6 = new CreateTripScreen6();
					}
					createTripScreen6.setTrips(trips);
					
					UiApplication.getUiApplication().popScreen(createTripScreen4);
					UiApplication.getUiApplication().pushScreen(createTripScreen6);
					return;
				}
			}
			catch (RemoteException rme)
			{
				Dialog.alert("A remote exception occurred: " + rme.getMessage());
				return;
			}
			
			if (createTripScreen5 == null)
			{
				createTripScreen5 = new CreateTripScreen5();
			}
			createTripScreen5.initialize();
			
			UiApplication.getUiApplication().popScreen(createTripScreen4);
			UiApplication.getUiApplication().pushScreen(createTripScreen5);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen5)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen5 to CreateTripScreen8: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen8 == null)
			{
				createTripScreen8 = new CreateTripScreen8();
			}
			createTripScreen8.initialize();
			
			UiApplication.getUiApplication().popScreen(createTripScreen5);
			UiApplication.getUiApplication().pushScreen(createTripScreen8);
			return;
		}
		
		String tripSaved = CreateTripController.getStringParameter(parameters, "tripSaved");
		if (currentScreen instanceof CreateTripScreen6 && tripSaved != null && tripSaved.equalsIgnoreCase("true"))
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen6 to CarPoolMainScreen: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen6 == null)
			{
				createTripScreen6 = new CreateTripScreen6();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen6);
			TripData.clear();
			// clearScreen();
			return;
		}
		
		String screenState = CreateTripController.getStringParameter(parameters, "state");
		if (currentScreen instanceof CreateTripScreen6 && screenState != null && !screenState.equalsIgnoreCase("similarSearch"))
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen6 to CreateTripScreen5: ").getBytes(), EventLogger.ALWAYS_LOG);
			
			CarPoolService_Stub service = new CarPoolService_Stub();
			String startAddress = TripData.getTripStartPlace();
			String endAddress = TripData.getTripEndPlace();
			
			// format the start and end date for the search
			Date startDate = TripData.getTripStartTime();
			
			Date endDate = TripData.getTripEndTime();
			int numMiles = 0;
			String latitude = "";
			String longitude = "";
			
			if (Dialog.YES == Dialog.ask(Dialog.D_YES_NO, "Would you like to check for similar trips on the same day?", Dialog.YES))
			{
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				try
				{
					User theUser = service.getUser(SessionSettings.getUserName());
					if (theUser != null)
					{
						numMiles = theUser.getPreferredDistance().intValue();
					}
					
					Trip[] trips = service.findSimilarTrips("", "", startAddress, endAddress, dateFormat.format(startDate), dateFormat.format(endDate), latitude, longitude, "12345", numMiles);
					if (trips != null && trips.length > 0)
					{
						if (createTripScreen6 == null)
						{
							createTripScreen6 = new CreateTripScreen6();
						}
						createTripScreen6.setState("similarSearch");
						
						// add the Create New option
						BigVector tripVector = new BigVector();
						Trip createTrip = new Trip();
						createTrip.setId("create");
						createTrip.setTitle("* Create New *");
						
						tripVector.addElement(createTrip);
						tripVector.addElements(trips);
						
						trips = new Trip[tripVector.size()];
						tripVector.copyInto(0, tripVector.size(), trips, 0);
			        	
						if (createTripScreen6 == null)
						{
							createTripScreen6 = new CreateTripScreen6();
						}
						createTripScreen6.setTrips(trips);
						
						return;
					}
				}
				catch (RemoteException rme)
				{
					Dialog.alert("A remote exception occurred: " + rme.getMessage());
					return;
				}
			}
			
			if (createTripScreen5 == null)
			{
				createTripScreen5 = new CreateTripScreen5();
			}
			createTripScreen5.initialize();
			
			UiApplication.getUiApplication().popScreen(createTripScreen6);
			UiApplication.getUiApplication().pushScreen(createTripScreen5);
			return;
		}
		
		// String searchSimilar = CreateTripController.getStringParameter(parameters, "searchSimilar");
		if (currentScreen instanceof CreateTripScreen6 && screenState != null && screenState.equalsIgnoreCase("similarSearch"))
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen6 to CreateTripScreen5: ").getBytes(), EventLogger.ALWAYS_LOG);
			if (createTripScreen5 == null)
			{
				createTripScreen5 = new CreateTripScreen5();
			}
			
			UiApplication.getUiApplication().popScreen(createTripScreen6);
			UiApplication.getUiApplication().pushScreen(createTripScreen5);
			return;
		}
		
		if (currentScreen instanceof CreateTripScreen8)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Moving from CreateTripScreen8 to CarPoolMainScreen: ").getBytes(), EventLogger.ALWAYS_LOG);
			UiApplication.getUiApplication().popScreen(createTripScreen8);
			// clearScreen();
			
			// display the directions screen
			if (directionsScreen == null)
			{
				directionsScreen = new DirectionsScreen();
			}
			
			String tripId = null;
			if (parameters.containsKey("tripId"))
			{
				tripId = (String) parameters.get("tripId");
			}
			if (tripId != null && tripId.trim().length() != 0)
			{
				try
				{
					CarPoolService_Stub service = new CarPoolService_Stub();
					Trip trip = service.getTrip(tripId);
					
					Place startAddress = trip.getStartAddress();
					Place endAddress = trip.getEndAddress();
					
					if (startAddress != null && endAddress != null)
					{
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting start place: " + startAddress.getName()).getBytes(), EventLogger.ALWAYS_LOG);
						EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting end place: " + endAddress.getName()).getBytes(), EventLogger.ALWAYS_LOG);
						directionsScreen.setStartPlace(startAddress);
						directionsScreen.setEndPlace(endAddress);
						directionsScreen.initialize();
						UiApplication.getUiApplication().pushScreen(directionsScreen);
					}
				}
				catch (RemoteException rme)
				{
					Dialog.alert("A remote exception occurred: " + rme.getMessage());
					return;
				}
			}
			
			return;
		}
	}
	
	/*
	private void clearScreen()
	{
		createTripScreen4.clearFields();
		createTripScreen5.clearFields();
	}
	*/
	
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
