package edu.psu.sweng500.carpool.services;

import java.util.List;

import edu.psu.sweng500.carpool.beans.Address;
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
 * This interface is used to provide location-based services
 * @author Eric Borisow
 */
public interface ICarPoolLocationService
{
	/**
	 * Retrieve Address objects for a particular user using the specified criteria
	 * @param criteria The query object
	 * @return
	 */
	List<Address> getLocationsByUser(FindCriteria criteria);
	
	/**
	 * Find locations near a specified location
	 * @param criteria The criteria object containing the location data
	 * @return
	 */
	List<Address> findLocationsNearLocation(FindCriteria criteria);
	
	/**
	 * Get an Address using the specified id
	 * @param id The id of the Address object
	 * @return
	 */
	Address getAddress(String id);
}
