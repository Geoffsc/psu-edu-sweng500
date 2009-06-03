package edu.psu.sweng500.carpool.beans;

import java.io.Serializable;
import java.util.Set;

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
