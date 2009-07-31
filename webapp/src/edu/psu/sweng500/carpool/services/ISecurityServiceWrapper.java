package edu.psu.sweng500.carpool.services;

import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.exceptions.SecurityException;

public interface ISecurityServiceWrapper
{
	boolean login (String userName, String password) throws SecurityException; 
	boolean createOrUpdate (User user) throws SecurityException;
}
