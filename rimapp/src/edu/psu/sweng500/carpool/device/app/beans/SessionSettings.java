package edu.psu.sweng500.carpool.device.app.beans;

import net.rim.device.api.collection.util.LongHashtableCollection;
import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;

public class SessionSettings
{
	private static PersistentObject store;
	private static SessionSettings instance;
	private LongHashtableCollection settings;

	public static final long KEY_USER_NAME = 0;

	private static SessionSettings getInstance()
	{
		if (null == instance)
		{
			instance = new SessionSettings();
		}
		return instance;
	}

	private SessionSettings()
	{
		store = PersistentStore.getPersistentObject(0x7b72bdedb7fb9c37L);
	}

	public static String getUserName()
	{
		String userName = (String) getInstance().get(KEY_USER_NAME);
		return userName;
	}

	public static void setUserName(String userName)
	{
		getInstance().set(KEY_USER_NAME, userName);
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
