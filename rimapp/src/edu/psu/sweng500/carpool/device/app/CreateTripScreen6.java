package edu.psu.sweng500.carpool.device.app;

import java.rmi.RemoteException;
import java.util.Hashtable;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import edu.psu.sweng500.carpool.device.app.beans.TripData;
import edu.psu.sweng500.carpool.device.services.generated.CarPoolService_Stub;
import edu.psu.sweng500.carpool.device.services.generated.Participant;
import edu.psu.sweng500.carpool.device.services.generated.Trip;
import edu.psu.sweng500.carpool.device.services.generated.TripUser;
import edu.psu.sweng500.carpool.device.services.generated.User;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ObjectListField;
import net.rim.device.api.ui.component.RadioButtonField;
import net.rim.device.api.ui.component.RadioButtonGroup;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class CreateTripScreen6 extends MainScreen
{
	private Trip[] trips;
	private VerticalFieldManager vfm;
	RadioButtonGroup radioGroup;
	private String state;
	DetailsScreen detailsScreen = null;
	
	public CreateTripScreen6()
	{
		this.setTitle("Create Trip - Use existing?");
	}
	
	public void setState(String state)
	{
		this.state = state;
	}
	
	public void setTrips(Trip[] trips)
	{
		this.deleteAll();
		this.trips = trips;
		
		vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLLBAR);

		radioGroup = new RadioButtonGroup();
		for (int i = 0; i < trips.length; i++)
		{
			Trip trip = this.trips[i];
			if (trip != null)
			{
				RadioButtonField buttonField = new RadioButtonField(trip.getTitle(), radioGroup, false);
				if (i == 0)
				{
					buttonField.setSelected(true);
				}
				vfm.add(buttonField);
			}
		}
		add(vfm);
		
		ButtonField buttonField = new ButtonField("Next >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		add(buttonField);
		
		FieldChangeListener listener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             performSearch();
	         }
	     };
	     buttonField.setChangeListener(listener);
        
	}
	
	public void initialize()
	{
	}
	
	private void performSearch()
	{
		Hashtable parameters = new Hashtable();
		int selectedTripIndex = radioGroup.getSelectedIndex();
		// the user selected the Create New option
		parameters.put("state", this.state);
		if (selectedTripIndex == 0)
		{
		}
		else
		{
			// otherwise, figure out which trip the user selected and add
			// the current user as a member of the trip
			Trip selectedTrip = this.trips[selectedTripIndex];
			if (selectedTrip != null)
			{
				Participant participant = new Participant();
				participant.setType(TripData.getTripUserType());
				
				TripUser user = new TripUser();
				
				CarPoolService_Stub service = new CarPoolService_Stub();		
				try
				{
					User theUser = service.getUser(SessionSettings.getUserName());
					
					user.setId(theUser.getId());
					participant.setUser(user);
					
					service.updateTripUser(selectedTrip.getId(), participant);
					
					/*
					Participant[] existingParticipants = selectedTrip.getParticipants();
					BigVector participantVec = new BigVector();
					participantVec.addElements(existingParticipants);
					participantVec.addElement(participant);
					existingParticipants = new Participant[participantVec.size()];
					participantVec.copyInto(0, participantVec.size(), existingParticipants, 0);
					
					selectedTrip.setParticipants(existingParticipants);
					
					service.updateTrip(selectedTrip);
					*/
					parameters.put("tripSaved", "true");
					
					TripData.clear();
					Dialog.alert("Trip updated!");
				}
				catch (RemoteException rme)
				{
					Dialog.alert("A remote exception occurred: " + rme.getMessage());
					return;
				}
			}
		}
		CreateTripController createTripController = CreateTripController.getInstance();
		createTripController.execute(this, parameters);
	}
	
	protected boolean navigationClick(int status, int time)
	{
        // Determine which menu item was clicked.
        int selected = radioGroup.getSelectedIndex();
        EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Selected item was " + selected).getBytes(), EventLogger.ALWAYS_LOG);
        Trip selectedTrip = trips[selected];
        
        if (detailsScreen == null)
        {
        	detailsScreen = new DetailsScreen();
        }
        detailsScreen.setTripId(selectedTrip.getId());
        detailsScreen.searchTrips();
		UiApplication.getUiApplication().pushScreen(detailsScreen);
        
        return true;
    }

	private class MainScreenObjectListField extends ObjectListField {
        
        private Bitmap icon = Bitmap.getBitmapResource("edu/psu/sweng500/carpool/device/app/img/document.png");                
        
        // We are going to take care of drawing the item.
        public void drawListRow(ListField listField, Graphics graphics, 
                int index, int y, int width) {
        	String startDate = "";
        	if (trips[index].getStartDate() != null)
        	{
        		startDate = trips[index].getStartDate();
        	}
            if (null != icon) {
                int offsetY = (this.getRowHeight() - icon.getHeight())/2;
                graphics.drawBitmap(1,y + offsetY, icon.getWidth(),
                        icon.getHeight(),icon,0,0);
                graphics.drawText(trips[index].getTitle() + " " + startDate, 
                    icon.getWidth() + 2, y, DrawStyle.ELLIPSIS, width);
            } else {
                graphics.drawText("- " + trips[index].getTitle() + " " + startDate, 0,
                    y, DrawStyle.ELLIPSIS, width);
            }
        }
        
    };
}