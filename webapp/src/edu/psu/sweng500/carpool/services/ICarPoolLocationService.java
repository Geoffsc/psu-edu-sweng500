package edu.psu.sweng500.carpool.services;

import java.util.List;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.data.FindCriteria;

public interface ICarPoolLocationService
{
	List getLocationsByUser(FindCriteria criteria);
	List findLocationsNearLocation(FindCriteria criteria);
}
