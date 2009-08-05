package edu.psu.sweng500.carpool.device.app.screens;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.StreamConnection;

import edu.psu.sweng500.carpool.device.app.beans.SavedAddressData;
import edu.psu.sweng500.carpool.device.app.utilities.URLUTF8Encoder;

import net.mypapit.java.StringTokenizer;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.FlowFieldManager;
import net.rim.device.api.ui.container.PopupScreen;

public final class GeocodePopupScreen extends PopupScreen implements FieldChangeListener {
    
	private static String HEADER_CONTENTTYPE = "content-type";
    private static String CONTENTTYPE_TEXTHTML = "text/html";
    
    ButtonField okButton;
    ButtonField cancelButton;
    EditField   streetField;
    
    ConnectionThread _connectionThread = new ConnectionThread();
        
    public GeocodePopupScreen() {                
    super(new FlowFieldManager());        
    streetField=new EditField("Address:","");
    add(streetField);
    add(new SeparatorField());
    okButton = new ButtonField("Ok");
    okButton.setChangeListener(this);
    add(okButton);
        
    cancelButton = new ButtonField("Cancel");
    cancelButton.setChangeListener(this);
    add(cancelButton);       
    
    _connectionThread.start();
    }
    
     public void fieldChanged(Field field, int context)
    {
        if (field == okButton)
        {
        	String surl = "http://maps.google.com/maps/geo?q=" + URLUTF8Encoder.encode(streetField.getText());
			surl += "&output=csv&key=ABQIAAAApriXyZ82aHkkNAoS4_9sUBS6yqeQ6J_iYeuC8P4LXAESSoW4oxRZMb_0UGhPak0aEsSzSQrCRL2IGw";

			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("surl is: " + surl).getBytes(), EventLogger.ALWAYS_LOG);
			
        	_connectionThread.fetch(surl);
        	/*
        	try
    		{
    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("About to execute search").getBytes(), EventLogger.ALWAYS_LOG);
    			String locationText = getTextFromGoogle(streetField.getText());
    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Done executing search").getBytes(), EventLogger.ALWAYS_LOG);
    			Dialog.alert("locationText: " + locationText);
    			
    			String[] locationData = convertToArray(locationText);
    			SavedAddressData.setAddressLatitude(locationData[2]);
    			SavedAddressData.setAddressLongitude(locationData[3]);
    			
    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The latitude picked is: " + locationData[2]).getBytes(), EventLogger.ALWAYS_LOG);
    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The longitude picked is: " + locationData[3]).getBytes(), EventLogger.ALWAYS_LOG);
    		}
    		catch (Exception e)
    		{
    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, (e.getMessage()).getBytes(), EventLogger.ALWAYS_LOG);
    		}
               
            UiApplication.getUiApplication().invokeLater(new Runnable()
            {                
             public void run()
              {
                   close();
               }
             });
             */
        }
        
        
        if (field == cancelButton)
        {
        	UiApplication.getUiApplication().invokeLater(new Runnable()
            {                
             public void run()
              {
                   close();
               }
             });
         }
    }
        
    public void paint(Graphics graphics) {        
        graphics.clear();
        super.paint(graphics);
    }
    
    public boolean onClose() {
    	onExit();
        return true;
    }
    
    protected void onExit()
    {
        _connectionThread.stop();
    }
    
    private String[] convertToArray(String text) {
		StringTokenizer st = new StringTokenizer(text, ",");
		
		String[] vals = new String[st.countTokens()];	
		
		int index = -1;
		
		while(st.hasMoreElements()) {
			String val = (String) st.nextElement();
			
			vals[++index] = val;
		}
		
		// make sure we got correct stuff
		if(null != vals[0] && vals[0].equals("200") && vals.length == 4) {
			return vals;
		 } else {
		 	return null;
		 }
	}
    
    private void updateContent(final String text)
    {
        //This will create significant garbage, but avoids threading issues
        // (compared with creating a static Runnable and setting the text)
        UiApplication.getUiApplication().invokeLater(new Runnable() {
            public void run()
            {
            	EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Done executing search").getBytes(), EventLogger.ALWAYS_LOG);
    			// Dialog.alert("locationText: " + text);
    			
    			if (text != null)
    			{
	    			String[] locationData = convertToArray(text);
	    			if (locationData != null)
	    			{
		    			SavedAddressData.setAddressLatitude(locationData[2]);
		    			SavedAddressData.setAddressLongitude(locationData[3]);
		    			
		    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The latitude picked is: " + locationData[2]).getBytes(), EventLogger.ALWAYS_LOG);
		    			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("The longitude picked is: " + locationData[3]).getBytes(), EventLogger.ALWAYS_LOG);
	    			}
    			}
               
    			close();
            }
        });
    }
    
    /**
     * The ConnectionThread class manages the HTTP connection
     * Fetch operations are not queued, however, if a fetch call
     * is made and, while active, another request made,
     * the 2nd request will stall until the previous request completes.
     */
    private class ConnectionThread extends Thread
    {
        private static final int TIMEOUT = 500; //ms

        private String _theUrl;

        private volatile boolean _start = false;
        private volatile boolean _stop = false;

        //retrieve the URL
        public synchronized String getUrl()
        {
            return _theUrl;
        }

        //fetch a page
        // synchronized so that i don't miss requests
        public void fetch(String url)
        {
            if ( _start )
            {
                Dialog.alert("Request in progress");
            }
            else
            {
                synchronized(this)
                {
                    if ( _start )
                    {
                        Dialog.alert("Request in progress");
                    }
                    else
                    {
                        _start = true;
                        _theUrl = url;
                    }
                }
            }
        }

        //shutdown the thread
        public void stop()
        {
            _stop = true;
        }

        public void run()
        {
            for(;;)
            {
                //Thread control
                while( !_start && !_stop)
                {
                    //sleep for a bit so we don't spin
                    try {
                        sleep(TIMEOUT);
                    } catch (InterruptedException e) {
                        System.err.println(e.toString());
                    }
                }
                //exit condition
                if ( _stop )
                {
                    return;
                }
                //This entire block is synchronized, this ensures I won't miss fetch requests made while i process a page
                synchronized(this)
                {
                    //open the connection and extract the data
                    StreamConnection s = null;
                    try {
                        s = (StreamConnection)Connector.open(getUrl());
                        HttpConnection httpConn = (HttpConnection)s;

                        int status = httpConn.getResponseCode();
                        if (status == HttpConnection.HTTP_OK)
                        {
                            //is this html?
                            String ctype = httpConn.getHeaderField(HEADER_CONTENTTYPE);
                            boolean htmlContent = (ctype != null && ctype.equals(CONTENTTYPE_TEXTHTML));

                            InputStream input = s.openInputStream();

                            byte[] data = new byte[256];
                            int len = 0;
                            int size = 0;
                            StringBuffer raw = new StringBuffer();
                            while ( -1 != (len = input.read(data)) )
                            {
                                raw.append(new String(data, 0, len));
                                size += len;
                            }
                            
                            /*
                            raw.insert(0, "bytes received]\n");
                            raw.insert(0, size);
                            raw.insert(0, '[');
                            */
                            String content = raw.toString();
                            if ( htmlContent )
                            {
                                content = raw.toString();
                            }
                            //The long operation is the parsing above,
                            // after the parsing is complete, shutdown the status thread
                            // before setting the text (since both threads modify the content
                            // pane, we want to make sure we don't have the status thread
                            // overwriting our data)
                            updateContent(content);
                            input.close();
                        } else {
                            updateContent("response code = " + status);
                        }
                        s.close();

                    } catch (IOException e) {
                        System.err.println(e.toString());
                        updateContent(e.toString());
                    }
                    //we're done one connection so reset the start state
                    _start = false;
                }
            }
        }

    }
}
