package edu.psu.sweng500.carpool.services.utilities;

import java.util.Random;

/* 
 * This program is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 2 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

/**
 * Class that provides some methods for generating unique ids
 * @author Eric Borisow
 *
 */
public class UniqueIDGeneratorUtil
{
	private UniqueIDGeneratorUtil() {}
	
	/**
	 * Return a unique id that can be used as a primary key
	 * @return
	 */
	public static String getUniqueId()
	{
		Random rnd = new Random();
		long uniqueId = ((System.currentTimeMillis()>>>16)<<16)+rnd.nextLong();
		uniqueId = Math.abs(uniqueId);
	
		String id = Long.toString(uniqueId);
		return id;
	}
	
	/**
	 * Generate a random number between two bounds
	 * @param r A random object
	 * @param lower The lower bound of the unique value
	 * @param higher The upper bound of the unique value
	 * @return
	 */
	public static int getRandomInt(Random r, int lower, int higher) 
	{ 
		return r.nextInt((higher-lower+1))+lower;
	}
}
