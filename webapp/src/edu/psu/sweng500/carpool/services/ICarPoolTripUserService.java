package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.data.FindCriteria;

public interface ICarPoolTripUserService
{
	boolean createOrUpdate(TripUser tripUser);
	boolean find(FindCriteria criteria);
}
