package edu.psu.sweng500.carpool.services;

import java.util.List;

import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.data.FindCriteria;

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
 * Interface to provide access to carpool trip service
 * @author Eric Borisow
 */
public interface ICarPoolTripService
{
	/**
	 * Method to create/update a trip
	 * @param trip The trip object to create or update if it already exists
	 * @return
	 */
	boolean createOrUpdateTrip(Trip trip);
	
	/**
	 * Retrieve a Trip object using the specified id
	 * @param id The id of the trip to locate
	 * @return
	 */
	Trip getTrip(String id);
	
	/**
	 * Delete a trip object
	 * @param id The id of the object to delete.
	 * @return
	 */
	boolean deleteTrip(String id);
	
	/**
	 * Find trips based on the selected criteria
	 * This version will get the Trip object and all associated objects
	 * @param criteria The criteria object
	 * @return
	 */
	List<TripUser> findTrips(FindCriteria criteria);
	
	/**
	 * Find trips based on the selected criteria
	 * This version will return only the Trip itself and no additional objects
	 * @param criteria The criteria object
	 * @return
	 */
	List<Trip> findTripsOnly(FindCriteria criteria);
}
