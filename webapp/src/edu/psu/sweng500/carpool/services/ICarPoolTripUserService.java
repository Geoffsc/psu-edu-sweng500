package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.TripUser;

public interface ICarPoolTripUserService
{
	boolean createOrUpdate(TripUser tripUser);
}
