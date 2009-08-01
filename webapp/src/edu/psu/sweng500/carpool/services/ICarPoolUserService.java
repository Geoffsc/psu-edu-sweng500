package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;

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
 * Interface for working with user objects
 * @author Eric Borisow
 *
 */
public interface ICarPoolUserService
{
	/**
	 * Get a user using the specified userName
	 * @param userName The user name to use to locate the User
	 * @return
	 */
	User getUser(String userName);
	
	/**
	 * Get a user using the specified id
	 * @param id The id of the user
	 * @return
	 */
	User getUserById(String id);
	
	/**
	 * Create or update a user based on the passed user object
	 * @param user If the user is new, it will be created.  If the user exists, it will be updated
	 * @return
	 */
	boolean createOrUpdateUser(User user);
	
	/**
	 * Create or update an Address object using the passed address
	 * @param address The address object to update in the system
	 * @return
	 */
	boolean createOrUpdateAddress(Address address);
	
	/**
	 * Delete the Address from the system
	 * @param id The id of the Address object
	 * @return
	 */
	boolean deleteAddress(String id);
}
