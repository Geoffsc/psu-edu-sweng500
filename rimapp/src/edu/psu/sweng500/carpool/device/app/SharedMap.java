package edu.psu.sweng500.carpool.device.app;

import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Graphics;

import com.nutiteq.MapComponent;
import com.nutiteq.components.WgsPoint;
import com.nutiteq.controls.ControlKeys;
import com.nutiteq.controls.UserDefinedKeysMapping;
import com.nutiteq.kml.KmlService;
import com.nutiteq.kml.KmlUrlReader;
import com.nutiteq.maps.CloudMade;
import com.nutiteq.maps.NutiteqStreamedMap;
import com.nutiteq.net.DefaultDownloadStreamOpener;

public class SharedMap
{
	private MapComponent mapComponent;
	private static SharedMap INSTANCE;
	private static final int SCREEN_WIDTH = Graphics.getScreenWidth();
	private static final int SCREEN_HEIGHT = Graphics.getScreenHeight();
	private KmlService pano;
	
	private SharedMap()
	{}
	
	public static SharedMap getInstance()
	{
		if (INSTANCE == null)
		{
			INSTANCE = new SharedMap();
			INSTANCE.initialize();
		}
		return INSTANCE;
	}
	
	public MapComponent getMap()
	{
		return this.mapComponent;
	}
	
	private void initialize()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Starting initialize".getBytes(), EventLogger.ALWAYS_LOG);
		final DefaultDownloadStreamOpener opener = new DefaultDownloadStreamOpener(";deviceside=false");
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
		// mapComponent.setMapListener(this);
		// mapComponent.setPlaceListener(this);
		mapComponent.showZoomLevelIndicator(true);
		pano = new KmlUrlReader(
				"http://www.panoramio.com/panoramio.kml?LANG=en_US.utf8&", true);
		mapComponent.addKmlService(pano);
		
		// mapComponent.setMap(new NutiteqStreamedMap(
			//	"http://aws.nutiteq.com/mapstream.php?ts=128&", "Car Pool",
			//	128, 0, 19));
		mapComponent.setMap(new CloudMade("856e4c9f90305c4183dc62a12944cadb", 64, 1));
		
		mapComponent.showDefaultOnScreenZoomControls();
		mapComponent.enableDownloadCounter();
		mapComponent.enableDownloadDisplay();
		mapComponent.showZoomLevelIndicator(true);
		mapComponent.showDefaultCursor();
		
		mapComponent.startMapping();
	}
}