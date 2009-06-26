package edu.psu.sweng500.carpool.tests;

import junit.framework.TestSuite;

public class DatabaseTestSuite extends TestSuite
{
	public static void main(String[] args)
	{
		DatabaseTestSuite suite = new DatabaseTestSuite();
		suite.addTest(new DatabaseTests("testGetTripsByUser"));
		
		junit.textui.TestRunner.run(suite);

	}
}
