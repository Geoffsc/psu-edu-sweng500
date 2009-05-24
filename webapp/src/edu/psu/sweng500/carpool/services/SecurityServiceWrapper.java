package edu.psu.sweng500.carpool.services;

public class SecurityServiceWrapper implements ISecurityServiceWrapper
{

	public boolean login(String userName, String password)
			throws SecurityException
	{
		System.out.println("User name: " + userName + "/password: " + password);
		return false;
	}

}
