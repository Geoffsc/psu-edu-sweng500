package edu.psu.sweng500.carpool.device.app;

import edu.psu.sweng500.carpool.device.app.beans.SessionSettings;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.UiApplication;

public class CarPoolApplication extends UiApplication
{
        public CarPoolApplication()
        {
        	EventLogger.register(0xe0328a32a1c0f8ddL, "CarPoolApplication", EventLogger.VIEWER_STRING);
        	EventLogger.logEvent(0xe0328a32a1c0f8ddL, "Starting CarPool Application".getBytes(), EventLogger.ALWAYS_LOG);
                SessionSettings.setUserName("");
                
                LoginScreen loginScreen = new LoginScreen();
                this.pushScreen(loginScreen);
        }
        
        public static void main(String[] args)
        {
                CarPoolApplication carPoolApp = new CarPoolApplication();
                carPoolApp.enterEventDispatcher();
                carPoolApp.enableKeyUpEvents(true);
        }
}
