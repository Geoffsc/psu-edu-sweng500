package edu.psu.sweng500.carpool.services.utilities;

import java.util.Random;

public class UniqueIDGeneratorUtil
{
	private UniqueIDGeneratorUtil() {}
	
	public static String getUniqueId()
	{
		Random rnd = new Random();
		long uniqueId = ((System.currentTimeMillis()>>>16)<<16)+rnd.nextLong();
		uniqueId = Math.abs(uniqueId);
	
		String id = Long.toString(uniqueId);
		return id;
	}
	
	public static int getRandomInt(Random r, int lower, int higher) 
	{ 
		return r.nextInt((higher-lower+1))+lower;
	}
}
