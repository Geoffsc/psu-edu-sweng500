package edu.psu.sweng500.carpool.device.app;

import net.rim.device.api.system.*;

public class SplashScreenListener implements KeyListener, 
					TrackwheelListener {
    
    private SplashScreen screen;
    
    public SplashScreenListener (SplashScreen splash) {
            screen = splash;
    }
    
    /** Invoked when the trackwheel is clicked */
    public boolean trackwheelClick(int status, int time) {
        screen.dismiss();
        return true;
    }

    /** Invoked when the trackwheel is released */
    public boolean trackwheelUnclick(int status, int time) {
        return false;
    }

    /** Invoked when the trackwheel is rolled.*/
    public boolean trackwheelRoll(int amount, int status, int time) {
        return false;
    }

    public boolean keyChar(char key, int status, int time) {
        //intercept the ESC key - exit the splash screen
        boolean retval = false;

        switch (key) {
        case Characters.ESCAPE:
            screen.dismiss();
            retval = true;
            break;
        }

        return retval;
    }

    /** Implementation of KeyListener.keyDown */
    public boolean keyDown(int keycode, int time) {
        return false;
    }

    /** Implementation of KeyListener.keyRepeat */
    public boolean keyRepeat(int keycode, int time) {
        return false;
    }

    /** Implementation of KeyListener.keyStatus */
    public boolean keyStatus(int keycode, int time) {
        return false;
    }

    /** Implementation of KeyListener.keyUp */
    public boolean keyUp(int keycode, int time) {
        return false;
    }
}