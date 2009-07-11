package edu.psu.sweng500.carpool.device.app;

import com.nutiteq.MapComponent;
import com.nutiteq.components.Line;
import com.nutiteq.components.Place;
import com.nutiteq.components.PlaceIcon;
import com.nutiteq.components.Placemark;
import com.nutiteq.components.Route;
import com.nutiteq.components.RouteInstruction;
import com.nutiteq.components.WgsPoint;
import com.nutiteq.listeners.MapListener;
import com.nutiteq.maps.CloudMade;
import com.nutiteq.services.CloudMadeDirections;
import com.nutiteq.services.DirectionsService;
import com.nutiteq.services.DirectionsWaiter;
import com.nutiteq.utils.Utils;
import com.nutiteq.wrappers.rimui.Image;

import edu.psu.sweng500.carpool.device.app.utilities.ApplicationConstants;

import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.container.MainScreen;

public class DirectionsScreen extends MainScreen implements DirectionsWaiter, MapListener
{
	private Graphics wrapped;
	private com.nutiteq.wrappers.rimui.Graphics graphicsWrapper;
	
	private MapComponent mapComponent;
	
	private Line directionsLine;
	private Place[] instructionPlaces;
	private WgsPoint[] points;
	
	private final Placemark yellow;
	private final Placemark green;
	private final Placemark red;
	
	private edu.psu.sweng500.carpool.device.services.generated.Place startPlace;
	private edu.psu.sweng500.carpool.device.services.generated.Place endPlace;
	
	public DirectionsScreen()
	{
		final Image tmp = Utils.createImage("/markers.png");
		yellow = new PlaceIcon(Image.createImage(tmp, 20, 0, 10, 17, 0), 5, 17);
		green = new PlaceIcon(Image.createImage(tmp, 0, 0, 10, 17, 0), 5, 17);
		red = new PlaceIcon(Image.createImage(tmp, 10, 0, 10, 17, 0), 5, 17);
	}
	
	public void initialize()
	{
		SharedMap sharedMap = SharedMap.getInstance();
		mapComponent = sharedMap.getMap();
		mapComponent.removeAllPlaces();
		
		mapComponent.setMap(new CloudMade("856e4c9f90305c4183dc62a12944cadb", 64, 1));
		
		WgsPoint startPoint = new WgsPoint(this.getStartPlace().getLongitude().doubleValue(), this.getStartPlace().getLatitude().doubleValue());
		Place startMapPlace = new Place(0, this.getStartPlace().getName(), green,
				startPoint);
		mapComponent.addPlace(startMapPlace);
		
		WgsPoint endPoint = new WgsPoint(this.getEndPlace().getLongitude().doubleValue(), this.getEndPlace().getLatitude().doubleValue()); 
		Place endMapPlace = new Place(1, this.getEndPlace().getName(), red,
				endPoint);
		mapComponent.addPlace(endMapPlace);
		
		WgsPoint[] thePoints = new WgsPoint[2];
		thePoints[0] = startPoint;
		thePoints[1] = endPoint;
		
		mapComponent.setMapListener(this);
		
		mapComponent.moveMap(startPoint);
		
	    DirectionsService directionsService = new CloudMadeDirections(this,
	    		startPoint, endPoint, CloudMadeDirections.ROUTE_TYPE_CAR, "856e4c9f90305c4183dc62a12944cadb");
	    directionsService.execute();
		
		mapComponent.repaint();
	}
	
	public void routeFound(final Route route) {
	    directionsLine = route.getRouteLine();
	    points = directionsLine.getPoints();
	    mapComponent.addLine(directionsLine);
	    final RouteInstruction[] instructions = route.getInstructions();
	    instructionPlaces = new Place[instructions.length];
	    for (int i = 0; i < instructions.length; i++) {
	      instructionPlaces[i] = new Place(i, instructions[i].getInstruction(), yellow, instructions[i]
	          .getPoint());
	    }
	    mapComponent.addPlaces(instructionPlaces);
	    if (instructionPlaces.length > 0) {
	      //TODO jaanus : check this
	      // mapComponent.removePlace(start);
	    }
	    // addCommand(cleanMap);
	    // show(this);
	  }

	  public void routingErrors(final int errorCodes) {
	    final StringBuffer errors = new StringBuffer("Errors: ");
	    if ((errorCodes & DirectionsService.ERROR_DESTINATION_ADDRESS_NOT_FOUND) != 0) {
	      errors.append("destination not found,");
	    }
	    if ((errorCodes & DirectionsService.ERROR_FROM_ADDRESS_NOT_FOUND) != 0) {
	      errors.append("from not found,");
	    }
	    if ((errorCodes & DirectionsService.ERROR_FROM_AND_DESTINATION_ADDRESS_SAME) != 0) {
	      errors.append("from and destination same,");
	    }
	    if ((errorCodes & DirectionsService.ERROR_ROUTE_NOT_FOUND) != 0) {
	      errors.append("route not found,");
	    }
	    showError(errors.toString());
	  }

	  public void routingParsingError(final String message) {
	    showError(message);
	  }
	  
	  public void networkError() {
	    showError("Network error!");
	  }
	  
	  private void showError(String message)
	  {
		  Dialog.alert(message);
	  }
	  
	  public void setDirectionsService(final DirectionsService directions) {
	    directions.execute();
	  }
	  
	  public void needRepaint(boolean arg0)
	{
		  invalidate();
	}
	  
	  public void mapClicked(WgsPoint arg0)
	{
		// TODO Auto-generated method stub
		
	}
	  
	  public void mapMoved()
	{
		// TODO Auto-generated method stub
		
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
		
		public void setStartPlace(edu.psu.sweng500.carpool.device.services.generated.Place startPlace)
		{
			this.startPlace = startPlace;
		}
		
		public edu.psu.sweng500.carpool.device.services.generated.Place getStartPlace()
		{
			return startPlace;
		}
		
		public void setEndPlace(edu.psu.sweng500.carpool.device.services.generated.Place endPlace)
		{
			this.endPlace = endPlace;
		}
		
		public edu.psu.sweng500.carpool.device.services.generated.Place getEndPlace()
		{
			return endPlace;
		}
}
