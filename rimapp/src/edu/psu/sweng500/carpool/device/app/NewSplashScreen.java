package edu.psu.sweng500.carpool.device.app;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class NewSplashScreen
{
	MainScreen screen;
	private static final Bitmap _bitmap = Bitmap.getBitmapResource("PennStateNittanyLions_bb.jpg");
	
	public NewSplashScreen()
	{
		screen = new MainScreen();
		screen.add(new BitmapField(_bitmap));
		UiApplication.getUiApplication().pushScreen(screen);
		
		TimerTask tt = new TimerTask(){
            public void run(){
                close();
            }
        };
        Timer t = new Timer();
        t.schedule(tt, 2000); // run after 2 seconds.
	}
	
	public void close(){
        UiApplication.getUiApplication().invokeLater(new Runnable(){
            public void run(){
                UiApplication.getUiApplication().popScreen(screen);
                
                CarPoolPreMainScreen initialScreen = new CarPoolPreMainScreen();
                UiApplication.getUiApplication().pushScreen(initialScreen);
            }
        });
        
    }
}
