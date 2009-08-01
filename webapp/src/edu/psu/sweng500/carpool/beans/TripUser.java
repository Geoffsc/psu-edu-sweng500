package edu.psu.sweng500.carpool.beans;

import java.io.Serializable;

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
 * Represents a combined trip and user
 * @author Eric Borisow
 */
public class TripUser implements Serializable
{
	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -4038048742582114882L;
	
	private String userId;
	private String tripId;
	// private Set trips;
	// private Set users;
	private User user;
	private Trip trip;
	private String type;
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public String getTripId()
	{
		return tripId;
	}
	public void setTripId(String tripId)
	{
		this.tripId = tripId;
	}
	/*
	public Set getTrips()
	{
		return trips;
	}
	public void setTrips(Set trips)
	{
		this.trips = trips;
	}
	public Set getUsers()
	{
		return users;
	}
	public void setUsers(Set users)
	{
		this.users = users;
	}
	*/
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public Trip getTrip()
	{
		return trip;
	}
	public void setTrip(Trip trip)
	{
		this.trip = trip;
	}
	
}
