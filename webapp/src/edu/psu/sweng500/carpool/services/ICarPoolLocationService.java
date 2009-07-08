package edu.psu.sweng500.carpool.services;

import java.util.List;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.data.FindCriteria;

public interface ICarPoolLocationService
{
	List<Address> getLocationsByUser(FindCriteria criteria);
	List<Address> findLocationsNearLocation(FindCriteria criteria);
	Address getAddress(String id);
}
