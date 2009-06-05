package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
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

import edu.psu.sweng500.carpool.device.services.generated.SecurityService_Stub;

class MapScreen extends MainScreen implements MapListener, PlaceListener
{
	private static final int SCREEN_WIDTH = Graphics.getScreenWidth();
	private static final int SCREEN_HEIGHT = Graphics.getScreenHeight();
	private MapComponent mapComponent;

	private Graphics wrapped;
	private com.nutiteq.wrappers.rimui.Graphics graphicsWrapper;

	private Place testPlace;

	private KmlService pano;

	public MapScreen()
	{
		final DefaultDownloadStreamOpener opener = new DefaultDownloadStreamOpener(
				";deviceside=true");
		mapComponent = new MapComponent("abcdtrial", "Nutiteq",
				"WrappedRIMUIDemo", SCREEN_WIDTH, SCREEN_HEIGHT, new WgsPoint(
						-74.0, 40.717), 7);
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

		testPlace = new Place(0, "Eric's House", new TestPlacemark(),
				new WgsPoint(-75.3705, 40.2809));
		mapComponent.addPlace(testPlace);

		mapComponent.startMapping();

	}

	public void paint(final Graphics g)
	{
		// wrap the graphics object inside library Graphics
		if (wrapped != g)
		{
			wrapped = g;
			graphicsWrapper = new com.nutiteq.wrappers.rimui.Graphics(g);
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
}

class TestPlacemark implements Placemark
{
	public int getAnchorX(int arg0)
	{
		return 10;
	}

	public int getAnchorY(int arg0)
	{
		return 10;
	}

	public int getHeight(int arg0)
	{
		return 20;
	}

	public int getWidth(int arg0)
	{
		return 20;
	}

	public void paint(com.nutiteq.wrappers.rimui.Graphics g, int x, int y,
			int zoom)
	{
		g.setColor(0xFFFF0000);
		g.fillRect(x, y, 20, 20);
	}
}
