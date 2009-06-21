package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;

import net.rim.blackberry.api.mail.Address;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;

import com.nutiteq.MapComponent;
import com.nutiteq.components.Place;
import com.nutiteq.components.Placemark;
import com.nutiteq.components.WgsPoint;
import com.nutiteq.controls.ControlKeys;
import com.nutiteq.controls.UserDefinedKeysMapping;
import com.nutiteq.kml.KmlService;
import com.nutiteq.kml.KmlUrlReader;
import com.nutiteq.listeners.MapListener;
import com.nutiteq.listeners.PlaceListener;
import com.nutiteq.maps.NutiteqStreamedMap;
import com.nutiteq.net.DefaultDownloadStreamOpener;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.User;

class CreateTripScreen2 extends MainScreen implements MapListener, PlaceListener
{
	private static final int SCREEN_WIDTH = Graphics.getScreenWidth();
	private static final int SCREEN_HEIGHT = Graphics.getScreenHeight();
	private MapComponent mapComponent;

	private CreateTripScreen4 nextTripScreen;
	private Graphics wrapped;
	private com.nutiteq.wrappers.rimui.Graphics graphicsWrapper;

	private double startLat;
	private double startLon;
	private Place testPlace;
	private String type;

	private KmlService pano;
	
	public void setStartLat(double startLat)
	{
		this.startLat = startLat;
	}
	
	public void setStartLon(double startLon)
	{
		this.startLon = startLon;
	}
	
	public double getStartLat()
	{
		return this.startLat;
	}
	
	public double getStartLon()
	{
		return this.startLon;
	}

	public CreateTripScreen2()
	{
		this.setTitle("Create Trip - Select a start location");

	}
	
	public void initialize()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Starting initialize".getBytes(), EventLogger.ALWAYS_LOG);
		final DefaultDownloadStreamOpener opener = new DefaultDownloadStreamOpener(";deviceside=true");
		if (mapComponent == null)
		{
			mapComponent = new MapComponent("abcdtrial", "Nutiteq",
				"WrappedRIMUIDemo", SCREEN_WIDTH, SCREEN_HEIGHT, new WgsPoint(
						-74.0, 40.717), 15);
						// -75.3715, 40.2799), 15);
			
		}
		final UserDefinedKeysMapping keysMapping = new UserDefinedKeysMapping();
		
		keysMapping.defineKey(ControlKeys.MOVE_UP_KEY, 84);
		keysMapping.defineKey(ControlKeys.MOVE_UP_KEY, 69);
		
		keysMapping.defineKey(ControlKeys.MOVE_DOWN_KEY, 66);
		keysMapping.defineKey(ControlKeys.MOVE_DOWN_KEY, 88);
		keysMapping.defineKey(ControlKeys.MOVE_LEFT_KEY, 83);
		keysMapping.defineKey(ControlKeys.MOVE_RIGHT_KEY, 70);
		keysMapping.defineKey(ControlKeys.MOVE_RIGHT_KEY, 74);
		
		keysMapping.defineKey(ControlKeys.ZOOM_OUT_KEY, 20);
		keysMapping.defineKey(ControlKeys.ZOOM_OUT_KEY, 65);
		keysMapping.defineKey(ControlKeys.ZOOM_OUT_KEY, 79);
		
		keysMapping.defineKey(ControlKeys.ZOOM_IN_KEY, 261);
		keysMapping.defineKey(ControlKeys.ZOOM_IN_KEY, 81);
		keysMapping.defineKey(ControlKeys.ZOOM_IN_KEY, 73);
		keysMapping.defineKey(ControlKeys.SELECT_KEY, 65);
		
		mapComponent.setControlKeysHandler(keysMapping);
		
		mapComponent.setDownloadStreamOpener(opener);
		mapComponent.setMapListener(this);
		mapComponent.setPlaceListener(this);
		mapComponent.showZoomLevelIndicator(true);
		pano = new KmlUrlReader(
				"http://www.panoramio.com/panoramio.kml?LANG=en_US.utf8&", true);
		mapComponent.addKmlService(pano);
		
		mapComponent.setMap(new NutiteqStreamedMap(
				"http://aws.nutiteq.com/mapstream.php?ts=128&", "Car Pool",
				128, 0, 19));
		mapComponent.showDefaultOnScreenZoomControls();
		mapComponent.enableDownloadCounter();
		mapComponent.enableDownloadDisplay();
		mapComponent.showZoomLevelIndicator(true);
		mapComponent.showDefaultCursor();
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Locating places".getBytes(), EventLogger.ALWAYS_LOG);
		float floatLat = new Float(startLat).floatValue();
		float floatLon = new Float(startLon).floatValue();
		edu.psu.sweng500.carpool.device.services.generated.Place[] places = this.getPlaces(floatLat, floatLon, 20);
		for (int i = 0; i < places.length; i++)
		{
			edu.psu.sweng500.carpool.device.services.generated.Place thisPlace = places[i];
			
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Found place: " + thisPlace.getId()).getBytes(), EventLogger.ALWAYS_LOG);
			System.out.println("Found place: " + thisPlace.getId());
			int placeId = Integer.parseInt(thisPlace.getId());
			Place thisMapPlace = new Place(placeId, thisPlace.getName(), new TestPlacemark(),
					new WgsPoint(thisPlace.getLongitude().doubleValue(), thisPlace.getLatitude().doubleValue()));
			mapComponent.addPlace(thisMapPlace);
		}
		
		// testPlace = new Place(1234, "Eric's House", new TestPlacemark(),
			// 	new WgsPoint(-75.3705, 40.2809));
		// mapComponent.addPlace(testPlace);
		
		mapComponent.startMapping();
		
		mapComponent.setScreenBounds(0, 20, SCREEN_WIDTH, SCREEN_HEIGHT-20);
		// mapComponent.setSize(SCREEN_WIDTH, SCREEN_HEIGHT-10);
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting middle point: " + this.getStartLat() + "/" + this.getStartLon()).getBytes(), EventLogger.ALWAYS_LOG);
		WgsPoint startPoint = new WgsPoint(this.getStartLon(), this.getStartLat());
		if (startPoint != null)
		{
			// mapComponent.setMiddlePoint(startPoint);
			mapComponent.moveMap(startPoint);
		}
		else
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The start point is null").getBytes(), EventLogger.ALWAYS_LOG);
		}
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Finished initialize method".getBytes(), EventLogger.ALWAYS_LOG);
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
	}

	public void paint(final Graphics g)
	{
		// wrap the graphics object inside library Graphics
		if (wrapped != g)
		{
			wrapped = g;
			graphicsWrapper = new com.nutiteq.wrappers.rimui.Graphics(g);
		}
		if (this.getType().equalsIgnoreCase("start"))
		{
			wrapped.drawText("Select a start location", 0, 0);
		}
		if (this.getType().equalsIgnoreCase("end"))
		{
			wrapped.drawText("Select an end location", 0, 0);
		}

		// paint on wrapper (in effect painting on native graphics)
		mapComponent.paint(graphicsWrapper);
		// remove pushContext() done inside library
		graphicsWrapper.popAll();
	}

	protected boolean navigationMovement(int dx, int dy, int status, int time)
	{
		mapComponent.panMap(dx * 10, dy * 10);
		invalidate();
		return true;
	}

	protected boolean navigationClick(int status, int time)
	{

		mapComponent.keyPressed(65);
		mapComponent.keyReleased(65);

		return super.navigationClick(status, time);
	}

	protected boolean keyDown(final int keyCode, final int time)
	{
		System.out.println("keyDown = " + keyCode);

		mapComponent.keyPressed(Keypad.key(keyCode));
		return super.keyDown(keyCode, time);
	}

	protected boolean keyUp(final int keyCode, final int time)
	{
		System.out.println("keyUp = " + keyCode);
		mapComponent.keyReleased(Keypad.key(keyCode));
		return super.keyUp(keyCode, time);
	}

	protected boolean keyRepeat(final int keyCode, final int time)
	{
		System.out.println("keyRepeat = " + keyCode);
		mapComponent.keyRepeated(Keypad.key(keyCode));
		return true;
	}

	public boolean onClose()
	{
		mapComponent.stopMapping();
		Dialog.alert("Done mapping");
		System.exit(0);
		return true;
	}

	public void needRepaint(final boolean screenComplete)
	{
		invalidate();
	}

	public void mapClicked(final WgsPoint pointClicked)
	{
		System.out.println("mapClicked " + pointClicked);
		Dialog.alert("mapClicked " + pointClicked);
	}

	public void mapMoved()
	{
	}

	public void placeClicked(final Place p)
	{
		System.out.println("place clicked " + p.getName());
		Dialog.alert("place clicked " + p.getName());
		
		if (this.type.equalsIgnoreCase("end"))
		{
			TripData.setTripEndPlace(Integer.toString(p.getId()));
			
			if (nextTripScreen == null)
			{
				nextTripScreen = new CreateTripScreen4();
			}
			System.out.println("Finished initializing screen 4");
			
			UiApplication.getUiApplication().pushScreen(nextTripScreen);
		}
		
		// mapComponent.stopMapping();
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The type is: " + this.type).getBytes(), EventLogger.ALWAYS_LOG);
		if (this.type.equalsIgnoreCase("start"))
		{
			System.out.println("Saving trip id " + p.getId());
			TripData.setTripStartPlace(Integer.toString(p.getId()));
			System.out.println("Done saving trip id");
			
			// re-display the map for the end position
			/*
			if (nextTripScreen == null)
			{
				nextTripScreen = new CreateTripScreen4();
			}
			System.out.println("Finished initializing screen 3");
			
			UiApplication.getUiApplication().pushScreen(nextTripScreen);
			*/
			
			CarPoolService_Stub serviceStub = new CarPoolService_Stub();
			try
			{
				User theUser = serviceStub.getUser(SessionSettings.getUserName());
				if (theUser != null)
				{
					edu.psu.sweng500.carpool.device.services.generated.Place[] places = theUser.getSavedPlaces();
					// use the START place from the user as the start point
					boolean notFound = true;
					for (int i = 0; i < places.length && notFound; i++)
					{
						edu.psu.sweng500.carpool.device.services.generated.Place thePlace = places[i];
						if (thePlace.getCategory().equalsIgnoreCase("end"))
						{
							System.out.println("Found a location for the start place");
							notFound = false;
							EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming latitude is: " + thePlace.getLatitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
							EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The incoming longitude is: " + thePlace.getLongitude().toString()).getBytes(), EventLogger.ALWAYS_LOG);
							this.setStartLat(thePlace.getLatitude().doubleValue());
							this.setStartLon(thePlace.getLongitude().doubleValue());
							this.setType("END");
						}
					}
				}
			}
			catch (RemoteException rme)
			{
				Dialog.alert("A remote exception occurred: " + rme.getMessage());
			}
			
			this.initialize();
		}
		
		
		
		/*
		UiApplication.getUiApplication().invokeLater(new Runnable(){

		  public void run(){

			  System.out.println("Finished closing screen");
				
				if (nextTripScreen == null)
				{
					nextTripScreen = new CreateTripScreen4();
				}
				System.out.println("Finished initializing screen 3");
				
				UiApplication.getUiApplication().pushScreen(nextTripScreen);
		  }
		});
		this.close();
		*/
		
		/*
		SecurityService_Stub serviceStub = new SecurityService_Stub();
		try
		{
			serviceStub.login("eborisow", "password");
			Dialog.alert("Login worked!");
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());

			return;
		}
		*/
	}

	public void placeEntered(final Place p)
	{

		System.out.println("place entered " + p.getName());
	}

	public void placeLeft(final Place p)
	{

		System.out.println("place left " + p.getName());

	}
	
	public edu.psu.sweng500.carpool.device.services.generated.Place[] getPlaces(float latitude, float longitude, int numMiles)
	{
		CarPoolService_Stub carPoolService = new CarPoolService_Stub();
		try
		{
			edu.psu.sweng500.carpool.device.services.generated.Place[] places = carPoolService.findPlacesNearLocation(latitude, longitude, numMiles);
			return places;
		}
		catch (RemoteException rme)
		{
			rme.printStackTrace();
			Dialog.alert("A remote exception occurred: " + rme.getMessage());

			return null;
		}
	}
}