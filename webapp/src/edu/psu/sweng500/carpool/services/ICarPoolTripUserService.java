package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.TripUser;

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
 * Interface for dealing with carpool trips and users
 * @author Eric Borisow
 */
public interface ICarPoolTripUserService
{
	/**
	 * Method to create/update a trip and associated user
	 * @param tripUser The trip and associated user
	 * @return
	 */
	boolean createOrUpdate(TripUser tripUser);
	
}
