package edu.psu.sweng500.carpool.device.app.screens;

import java.rmi.RemoteException;
import java.util.Hashtable;

import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

import com.nutiteq.MapComponent;
import com.nutiteq.components.Place;
import com.nutiteq.components.PlaceIcon;
import com.nutiteq.components.Placemark;
import com.nutiteq.components.WgsPoint;
import com.nutiteq.listeners.MapListener;
import com.nutiteq.listeners.PlaceListener;
import com.nutiteq.utils.Utils;
import com.nutiteq.wrappers.rimui.Image;

import edu.psu.sweng500.carpool.device.app.SharedMap;
import edu.psu.sweng500.carpool.device.app.beans.SavedAddressData;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.app.controllers.CreateTripController;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;

public class CreateTripScreen2 extends MainScreen implements MapListener, PlaceListener
{
	private static final int SCREEN_WIDTH = Graphics.getScreenWidth();
	private static final int SCREEN_HEIGHT = Graphics.getScreenHeight();
	private MapComponent mapComponent;

	private Graphics wrapped;
	private com.nutiteq.wrappers.rimui.Graphics graphicsWrapper;

	private double startLat;
	private double startLon;
	private String type;
	
	private final Placemark yellow;
	
	private edu.psu.sweng500.carpool.device.services.generated.Place[] places;
	
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
		//this.setTitle("Create Trip - Select a start location");

		final Image tmp = Utils.createImage("/markers.png");
		yellow = new PlaceIcon(Image.createImage(tmp, 20, 0, 10, 17, 0), 5, 17);
	}
	
	public void initialize()
	{
		SharedMap sharedMap = SharedMap.getInstance();
		mapComponent = sharedMap.getMap();
		mapComponent.removeAllPlaces();
		mapComponent.setZoom(17);
		
		mapComponent.setMapListener(this);
		mapComponent.setPlaceListener(this);
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Locating places".getBytes(), EventLogger.ALWAYS_LOG);
		float floatLat = new Float(startLat).floatValue();
		float floatLon = new Float(startLon).floatValue();
		
		this.places = this.getPlaces(floatLat, floatLon, 20);
		if (this.places != null)
		{
			for (int i = 0; i < places.length; i++)
			{
				edu.psu.sweng500.carpool.device.services.generated.Place thisPlace = places[i];
				
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Found place: " + thisPlace.getId()).getBytes(), EventLogger.ALWAYS_LOG);
				Place thisMapPlace = new Place(i, thisPlace.getName(), yellow,
						new WgsPoint(thisPlace.getLongitude().doubleValue(), thisPlace.getLatitude().doubleValue()));
				mapComponent.addPlace(thisMapPlace);
			}
		}
		
		mapComponent.setScreenBounds(0, 20, SCREEN_WIDTH, SCREEN_HEIGHT-20);
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Setting middle point: " + this.getStartLat() + "/" + this.getStartLon()).getBytes(), EventLogger.ALWAYS_LOG);
		WgsPoint startPoint = new WgsPoint(this.getStartLon(), this.getStartLat());
		if (startPoint != null)
		{
			mapComponent.moveMap(startPoint);
		}
		else
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The start point is null").getBytes(), EventLogger.ALWAYS_LOG);
		}
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Finished initialize method".getBytes(), EventLogger.ALWAYS_LOG);
	}
	
	private MenuItem moveToAddressItem = new MenuItem("Go to address", 110, 10) {
		public void run()
		{
			SavedAddressData.setAddressLatitude("");
			SavedAddressData.setAddressLongitude("");
			
			GeocodePopupScreen popupScreen = new GeocodePopupScreen();
			UiApplication.getUiApplication().pushModalScreen(popupScreen);
			
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Finished with popup screen").getBytes(), EventLogger.ALWAYS_LOG);
			
			if (SavedAddressData.getAddressLatitude() != null && SavedAddressData.getAddressLatitude().trim().length() != 0 &&
					SavedAddressData.getAddressLongitude() != null && SavedAddressData.getAddressLongitude().trim().length() != 0)
			{
				double latitude = Double.parseDouble(SavedAddressData.getAddressLatitude());
				double longitude = Double.parseDouble(SavedAddressData.getAddressLongitude());
				
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The latitude selected is: " + latitude).getBytes(), EventLogger.ALWAYS_LOG);
				EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The longitude selected is: " + longitude).getBytes(), EventLogger.ALWAYS_LOG);
				
				WgsPoint startPoint = new WgsPoint(longitude, latitude);
				if (startPoint != null)
				{
					// mapComponent.setMiddlePoint(startPoint);
					mapComponent.moveMap(startPoint);
					mapComponent.repaint();
				}
			}
		}
	};
	
	protected void makeMenu(Menu menu, int instance)
	{
        menu.add(moveToAddressItem);
        menu.add(MenuItem.separator(moveToAddressItem.getOrdinal() + 1));
        super.makeMenu(menu,instance);
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
			wrapped.setColor(Color.GREEN);
			wrapped.setBackgroundColor(Color.BLACK);
			wrapped.drawText("Select a start location", 0, 0);
		}
		if (this.getType().equalsIgnoreCase("end"))
		{
			wrapped.setColor(Color.RED);
			wrapped.setBackgroundColor(Color.BLACK);
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
		if (Dialog.NO == Dialog.ask(Dialog.D_YES_NO, "Are you sure you want to exit?", Dialog.YES))
		{
			return false;
		}
		UiApplication.getUiApplication().popScreen(this);
		return true;
	}

	public void needRepaint(final boolean screenComplete)
	{
		invalidate();
	}

	public void mapClicked(final WgsPoint pointClicked)
	{
		System.out.println("mapClicked " + pointClicked);
	}

	public void mapMoved()
	{
	}

	public void placeClicked(final Place p)
	{
		System.out.println("place clicked " + p.getName());
		Dialog.alert("You selected " + p.getName());
		
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The type is: " + this.type).getBytes(), EventLogger.ALWAYS_LOG);
		
		if (this.type.equalsIgnoreCase("end"))
		{
			edu.psu.sweng500.carpool.device.services.generated.Place placeClicked = places[p.getId()];
			TripData.setTripEndPlace(placeClicked.getId());
			
			Hashtable parameters = new Hashtable();
			parameters.put("type", "END");
			
			CreateTripController createTripController = CreateTripController.getInstance();
			createTripController.execute(this, parameters);
		}
		
		if (this.type.equalsIgnoreCase("start"))
		{
			System.out.println("Saving trip id " + p.getId());
			edu.psu.sweng500.carpool.device.services.generated.Place placeClicked = places[p.getId()];
			TripData.setTripStartPlace(placeClicked.getId());
			System.out.println("Done saving trip id");
			
			Hashtable parameters = new Hashtable();
			parameters.put("type", "START");
			
			CreateTripController createTripController = CreateTripController.getInstance();
			createTripController.execute(this, parameters);
		}
		
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