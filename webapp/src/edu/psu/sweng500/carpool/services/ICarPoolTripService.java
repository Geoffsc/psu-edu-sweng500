package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.Trip;

public interface ICarPoolTripService
{
	boolean createOrUpdateTrip(Trip trip);
	Trip getTrip(String id);
	boolean deleteTrip(String id);
}
