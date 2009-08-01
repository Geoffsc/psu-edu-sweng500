package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.exceptions.SecurityException;

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
 * Interface for Security Services
 * @author Eric Borisow
 *
 */
public interface ISecurityServiceWrapper
{
	/**
	 * Method for logging in
	 * @param userName The user name to test for login
	 * @param password The password of the user to test for login
	 * @return
	 * @throws SecurityException
	 */
	boolean login (String userName, String password) throws SecurityException;
	
	/**
	 * Create or update a user object
	 * @param user The user object to create or update
	 * @return
	 * @throws SecurityException
	 */
	boolean createOrUpdate (User user) throws SecurityException;
}
