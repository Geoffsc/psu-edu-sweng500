package edu.psu.sweng500.carpool.services;

import java.util.List;

import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.data.FindCriteria;

public interface ICarPoolTripService
{
	boolean createOrUpdateTrip(Trip trip);
	Trip getTrip(String id);
	boolean deleteTrip(String id);
	List<TripUser> findTrips(FindCriteria criteria);
	List<Trip> findTripsOnly(FindCriteria criteria);
}
