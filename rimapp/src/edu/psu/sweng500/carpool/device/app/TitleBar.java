package edu.psu.sweng500.carpool.device.app;

import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;
import java.util.*;


/**
 * customized Title Bar
 * background color is black (GRAY_COLOR) 
 * image is placed horizontal center (titleImage)
 * title bar height is same as the height of the title image (fieldHeight)
 */
public class TitleBar extends Field implements DrawStyle
{
    private int fieldWidth;
    private int fieldHeight;
    private int backgroundColor;
    
    private Bitmap titleImage =  Bitmap.getBitmapResource("Login.png"); // default

    private static final int BACKGROUND_COLOR = Color.BLACK;
 
    public TitleBar(int inTitle) 
    {
        super(Field.NON_FOCUSABLE);
        //background color is black
        backgroundColor = BACKGROUND_COLOR;
        
        switch (inTitle) {
        case 0:
        	titleImage =  Bitmap.getBitmapResource("Login.png");
            break;
        case 1: 
        	titleImage =  Bitmap.getBitmapResource("Main.png");
            break;
        case 2:
        	titleImage =  Bitmap.getBitmapResource("Search.png");
        	break;
        case 3:
        	titleImage =  Bitmap.getBitmapResource("Results.png");
        	break;
        case 4:
        	titleImage =  Bitmap.getBitmapResource("Details.png");
            break;
        case 5:
        	titleImage =  Bitmap.getBitmapResource("CreateTrip.png");
            break;
        default:
        	titleImage =  Bitmap.getBitmapResource("Login.png");
        } // end switch 
        
        fieldHeight = titleImage.getHeight();
        fieldWidth = Graphics.getScreenWidth();
    }
    
    public void setBackgroundColour(int _backgroundColour)
    {
        backgroundColor = _backgroundColour;
        invalidate();
    }
    
    protected void layout(int width, int height) 
    {
        setExtent(getPreferredWidth(), getPreferredHeight());
    }
    
    public int getPreferredWidth() 
    {
        return fieldWidth;
    }
    
    public int getPreferredHeight() 
    {
        return fieldHeight;
    }
    
    protected void paint(Graphics graphics) 
    {
        
        int w = this.getPreferredWidth();
        int h = this.getPreferredHeight();
        
        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, w, h);
        
        int marginX = (w- titleImage.getWidth() ) / 2 ; 
        graphics.drawBitmap(marginX, 0, w, h, titleImage, 0, 0);
    }
}