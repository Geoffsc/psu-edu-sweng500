package edu.psu.sweng500.carpool.services;

public interface ISecurityServiceWrapper
{
	boolean login (String userName, String password) throws SecurityException; 
}
