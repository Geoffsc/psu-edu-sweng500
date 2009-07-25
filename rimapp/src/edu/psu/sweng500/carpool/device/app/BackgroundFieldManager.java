package edu.psu.sweng500.carpool.device.app;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class BackgroundFieldManager extends VerticalFieldManager
{
	private int titleBarHeight;
	
	BackgroundFieldManager(int inTitleBarHeight)
	{
        super(Manager.VERTICAL_SCROLL);
        titleBarHeight = inTitleBarHeight;
    }
	
	public void paint(Graphics graphics)
    {
        graphics.setBackgroundColor(Color.GRAY);
        graphics.setColor(Color.BLACK);
        graphics.clear();
        super.paint(graphics);
    }
	
    protected void sublayout( int maxWidth, int maxHeight )
    {
        int displayWidth = Display.getWidth();
        int displayHeight = Display.getHeight() - titleBarHeight;

        super.sublayout( displayWidth, displayHeight);
        setExtent( displayWidth, displayHeight);
    }
    
    /*
    protected void paintBackground( Graphics g ){
        g.clear();
        Bitmap bgImage = Bitmap.getBitmapResource("road.jpg");
        g.drawBitmap(0, 0, 360, 480, bgImage, 0, 0);     
        super.paint(g);
    }
    */
};