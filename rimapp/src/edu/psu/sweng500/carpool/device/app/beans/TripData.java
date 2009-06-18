package edu.psu.sweng500.carpool.device.app.beans;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;
import java.util.Date;

import net.rim.device.api.collection.util.LongHashtableCollection;
import net.rim.device.api.system.EventLogger;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

public class TripData
{
	private static PersistentObject store;
	private static TripData instance;
	private LongHashtableCollection settings;

	public static final long KEY_TRIP_TITLE = 101;
	public static final long KEY_TRIP_START_PLACE = 102;
	public static final long KEY_TRIP_END_PLACE = 103;
	public static final long KEY_TRIP_DATE = 104;
	public static final long KEY_TRIP_START_TIME = 105;
	public static final long KEY_TRIP_END_TIME = 106;
	public static final long KEY_TRIP_USER_TYPE = 107;

	private static TripData getInstance()
	{
		if (null == instance)
		{
			instance = new TripData();
		}
		return instance;
	}

	private TripData()
	{
		store = PersistentStore.getPersistentObject(0x7b72bdedb7fb9c37L);
	}

	public static String getTripTitle()
	{
		String tripTitle = (String) getInstance().get(KEY_TRIP_TITLE);
		return tripTitle;
	}

	public static void setTripTitle(String tripTitle)
	{
		getInstance().set(KEY_TRIP_TITLE, tripTitle);
	}
	
	public static String getTripStartPlace()
	{
		String startPlace = (String) getInstance().get(KEY_TRIP_START_PLACE);
		return startPlace;
	}

	public static void setTripStartPlace(String startPlace)
	{
		getInstance().set(KEY_TRIP_START_PLACE, startPlace);
	}
	
	public static String getTripEndPlace()
	{
		String endPlace = (String) getInstance().get(KEY_TRIP_END_PLACE);
		return endPlace;
	}

	public static void setTripEndPlace(String endPlace)
	{
		getInstance().set(KEY_TRIP_END_PLACE, endPlace);
	}
	
	public static Date getTripDate()
	{
		String tripDateString = (String) getInstance().get(KEY_TRIP_DATE);
		Date tripDate = new Date(Long.parseLong(tripDateString));
		return tripDate;
	}

	public static void setTripDate(Date tripDate)
	{
		Long tripDateLong = new Long(tripDate.getTime());
		getInstance().set(KEY_TRIP_DATE, tripDateLong.toString());
	}
	
	public static Date getTripStartTime()
	{
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Starting getTripStartTime").getBytes(), EventLogger.ALWAYS_LOG);
		String startLongString = (String) getInstance().get(KEY_TRIP_START_TIME);
		Date startDate = new Date(Long.parseLong(startLongString));
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Returning start time: " + startDate.getTime()).getBytes(), EventLogger.ALWAYS_LOG);
		return startDate;
	}

	public static void setTripStartTime(Date tripStartTime)
	{
		/*
		DateFormat format = SimpleDateFormat.getInstance(DateFormat.DATE_SHORT | DateFormat.TIME_LONG);
		String startTime = format.format(tripStartTime);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Incoming start time is: " + startTime).getBytes(), EventLogger.ALWAYS_LOG);
		*/
		Long startLong = new Long(tripStartTime.getTime());
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Incoming start time is: " + startLong.toString()).getBytes(), EventLogger.ALWAYS_LOG);
		getInstance().set(KEY_TRIP_START_TIME, startLong.toString());
	}
	
	public static Date getTripEndTime()
	{
		String endLongString = (String) getInstance().get(KEY_TRIP_END_TIME);
		Date endDate = new Date(Long.parseLong(endLongString));
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Returning end time: " + endDate.getTime()).getBytes(), EventLogger.ALWAYS_LOG);
		return endDate;
	}

	public static void setTripEndTime(Date tripEndTime)
	{
		/*
		DateFormat format = SimpleDateFormat.getInstance(DateFormat.DATE_SHORT | DateFormat.TIME_LONG);
		String endTime = format.format(tripEndTime);
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Incoming end time is: " + endTime).getBytes(), EventLogger.ALWAYS_LOG);
		*/
		Long endLong = new Long(tripEndTime.getTime());
		EventLogger.logEvent(0xe0328a32a1c0f8ddL, ("Incoming end time is: " + endLong.toString()).getBytes(), EventLogger.ALWAYS_LOG);
		getInstance().set(KEY_TRIP_END_TIME, endLong.toString());
	}
	
	public static String getTripUserType()
	{
		String tripUserType = (String) getInstance().get(KEY_TRIP_USER_TYPE);
		return tripUserType;
	}

	public static void setTripUserType(String tripUserType)
	{
		getInstance().set(KEY_TRIP_USER_TYPE, tripUserType);
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
