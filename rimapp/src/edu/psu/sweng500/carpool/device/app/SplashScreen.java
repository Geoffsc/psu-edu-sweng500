package edu.psu.sweng500.carpool.device.app;

import com.thinkingblackberry.GridFieldManager;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.i18n.*;
import net.rim.device.api.system.*;

import java.rmi.RemoteException;
import java.util.*;

public class SplashScreen extends MainScreen {
    
    private Timer timer = new Timer();
    private UiApplication application;
    private static final Bitmap _bitmap = Bitmap.getBitmapResource("psu.jpg");
    private int seconds = 5;
    

    public SplashScreen() {
        super (Field.FIELD_VCENTER|Field.FIELD_HCENTER);         
        this.add(new BitmapField(_bitmap));
        
        SplashScreenListener listener =  
			new SplashScreenListener (this);
        
        this.addKeyListener(listener);
        this.addTrackwheelListener(listener);
        
        timer.schedule(new CountDown(), 5000);
        //timer.schedule (new ToDoTask(), seconds*3 ) ;
    }
    
    class ToDoTask extends TimerTask {
        public void run() {
          timer.cancel () ; //Terminate the thread
          LoginScreen loginScreen = new LoginScreen();
          UiApplication.getUiApplication().pushScreen(loginScreen);
        }
      }

        
    public void dismiss() {
        timer.cancel();
        LoginScreen loginScreen = new LoginScreen();
        UiApplication.getUiApplication().pushScreen(loginScreen);
    }

    private class CountDown extends TimerTask {          
        public void run() {
           DismissThread dThread = new DismissThread();
           try
           {
        	   application.invokeLater (dThread);
           }
           		catch (NullPointerException npe)
           		{
           			npe.printStackTrace();
           		}
        	timer.cancel () ;
        	
           }
    }
    
    private class DismissThread implements Runnable {
        public void run() {
             dismiss();
        	}   
    	}  
    }

