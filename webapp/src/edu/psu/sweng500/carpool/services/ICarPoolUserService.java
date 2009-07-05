package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;

public interface ICarPoolUserService
{
	User getUser(String id);
	User getUserById(String id);
	boolean createOrUpdateUser(User user);
	boolean createOrUpdateAddress(Address address);
	boolean deleteAddress(String id);
}
