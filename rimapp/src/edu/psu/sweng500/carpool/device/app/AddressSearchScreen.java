package edu.psu.sweng500.carpool.device.app;

import java.io.InputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.system.EventLogger;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.component.BasicEditField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class AddressSearchScreen extends MainScreen
{
	private BasicEditField streetField;
	
	public AddressSearchScreen()
	{
		this.setTitle("Search By Address");
        
		LabelField label1 = new LabelField("Street:", Field.FIELD_LEFT | Field.FIELD_VCENTER);
		streetField = new BasicEditField();
		
		add(label1);
		add(streetField);
		
		ButtonField buttonField1 = new ButtonField("Find >>", Field.FIELD_HCENTER | ButtonField.CONSUME_CLICK);
		add(buttonField1);
		
		FieldChangeListener findListener = new FieldChangeListener() 
		{
	         public void fieldChanged(Field field, int context) 
	         {
	             // ButtonField buttonField = (ButtonField) field;
	             performSearch();
	         }
	     };
	     
	     buttonField1.setChangeListener(findListener);
	}
	
	private void performSearch()
	{
		try
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("About to execute search").getBytes(), EventLogger.ALWAYS_LOG);
			String locationText = getTextFromGoogle(streetField.getText());
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Done executing search").getBytes(), EventLogger.ALWAYS_LOG);
			// Dialog.alert("locationText: " + locationText);
		}
		catch (Exception e)
		{
			EventLogger.logEvent(0xe0328a32a1c0f8ddL, (e.getMessage()).getBytes(), EventLogger.ALWAYS_LOG);
		}
	}
	
	
	private String getTextFromGoogle(String addy) throws Exception {
		HttpConnection conn = null;
		try {
			
			String surl = "http://maps.google.com/maps/geo?q=" + URLUTF8Encoder.encode(addy);
			surl += "&output=csv&key=ABQIAAAApriXyZ82aHkkNAoS4_9sUBS6yqeQ6J_iYeuC8P4LXAESSoW4oxRZMb_0UGhPak0aEsSzSQrCRL2IGw";

			EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("surl is: " + surl).getBytes(), EventLogger.ALWAYS_LOG);
			
			conn = (HttpConnection) Connector.open(surl);
			InputStream is = conn.openInputStream();
			
			byte[] buf = new byte[1024];
			int k = 0;
			StringBuffer text = new StringBuffer();
			String line;
			
			while((k = is.read(buf)) != -1) {
				line =new String(buf, 0, k);
				text.append(line);
			}
			
			return text.toString();
			
		} catch(Exception ex) {
			throw ex;
		} finally {
			try {
				conn.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}	
	
	
}
