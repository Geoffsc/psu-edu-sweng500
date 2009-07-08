package edu.psu.sweng500.carpool.device.app.beans;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import java.util.Date;

import net.rim.device.api.collection.util.LongHashtableCollection;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

public class SavedAddressData
{
	private static PersistentObject store;
	private static SavedAddressData instance;
	private LongHashtableCollection settings;

	public static final long KEY_ADDRESS_TITLE = 201;
	public static final long KEY_ADDRESS_LINE_1 = 202;
	public static final long KEY_ADDRESS_LINE_2 = 203;
	public static final long KEY_LATITUDE = 204;
	public static final long KEY_LONGITUDE = 205;
	public static final long KEY_TYPE = 206;

	private static SavedAddressData getInstance()
	{
		if (null == instance)
		{
			instance = new SavedAddressData();
		}
		return instance;
	}

	private SavedAddressData()
	{
		store = PersistentStore.getPersistentObject(0x7b72bdedb7fb9c37L);
	}
	
	public static void clear()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Deleting Saved Address instance").getBytes(), EventLogger.ALWAYS_LOG);
		instance = null;
	}

	public static String getAddressTitle()
	{
		String tripTitle = (String) getInstance().get(KEY_ADDRESS_TITLE);
		return tripTitle;
	}

	public static void setAddressTitle(String tripTitle)
	{
		getInstance().set(KEY_ADDRESS_TITLE, tripTitle);
	}
	
	public static String getAddressLatitude()
	{
		String latitude = (String) getInstance().get(KEY_LATITUDE);
		return latitude;
	}

	public static void setAddressLatitude(String latitude)
	{
		getInstance().set(KEY_LATITUDE, latitude);
	}
	
	public static String getAddressLongitude()
	{
		String longitude = (String) getInstance().get(KEY_LONGITUDE);
		return longitude;
	}

	public static void setAddressLongitude(String longitude)
	{
		getInstance().set(KEY_LONGITUDE, longitude);
	}
	
	public static String getType()
	{
		String type = (String) getInstance().get(KEY_TYPE);
		return type;
	}

	public static void setType(String type)
	{
		getInstance().set(KEY_TYPE, type);
	}

	private void set(long key, Object value)
	{
		synchronized (store)
		{
			settings = (LongHashtableCollection) store.getContents();
			if (null == settings)
			{
				settings = new LongHashtableCollection();
			}
			settings.put(key, value);
			store.setContents(settings);
			store.commit();
		}
	}

	private Object get(long key)
	{
		synchronized (store)
		{
			settings = (LongHashtableCollection) store.getContents();
			if (null != settings && settings.size() != 0)
			{
				return settings.get(key);
			}
			else
			{
				return null;
			}
		}
	}
}
