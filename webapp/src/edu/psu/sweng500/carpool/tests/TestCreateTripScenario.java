package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FetchMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.Organization;
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.beans.UserAddress;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.generated.beans.xsd.Parameter;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindSimilarTripsRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Participant;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Place;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripResponse;
import edu.psu.sweng500.carpool.services.CarPoolLocationService;
import edu.psu.sweng500.carpool.services.CarPoolTripService;
import edu.psu.sweng500.carpool.services.CarPoolTripUserService;
import edu.psu.sweng500.carpool.services.CarPoolUserService;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.utilities.LocationUtils;

public class TestCreateTripScenario
{
	/*
	@Test
	public void testFindMatchingTrip()
	{
		FindSimilarTripsRequest findSimilarTripsRequest = new FindSimilarTripsRequest();
		// findSimilarTripsRequest.setStartAddress("357847589");
		// findSimilarTripsRequest.setStartDate("2009-05-31");
		findSimilarTripsRequest.setOrganizationId("12345");
		// findSimilarTripsRequest.setLatitude("40.123343");
		// findSimilarTripsRequest.setLongitude("-75.284260");
		findSimilarTripsRequest.setStartAddress("2622376660628567550");
		findSimilarTripsRequest.setEndAddress("678791264");
		findSimilarTripsRequest.setStartDate("2009-06-10 08:15:00");
		findSimilarTripsRequest.setEndDate("2009-06-10 16:30:00");
		findSimilarTripsRequest.setNumMiles(0);
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.findSimilarTrips(findSimilarTripsRequest);
			System.out.println("Response is: " + findSimilarTripsRequest.getTitle()); 
			TestCase.assertNull(findSimilarTripsRequest.getTitle());
		}
		catch (AxisFault afe)
		{
			afe.printStackTrace();
			TestCase.fail(afe.getMessage());
		}
		catch (CarPoolServiceException cpe)
		{
			cpe.printStackTrace();
			TestCase.fail(cpe.getMessage());
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
			TestCase.fail(re.getMessage());
		}
	}
	*/

	
	@Test
	public void testFindNoMatchingTrip()
	{
		FindSimilarTripsRequest findSimilarTripsRequest = new FindSimilarTripsRequest();
		// findSimilarTripsRequest.setStartAddress("357847589");
		// findSimilarTripsRequest.setStartDate("2009-05-31");
		findSimilarTripsRequest.setOrganizationId("12345");
		// findSimilarTripsRequest.setLatitude("40.123343");
		// findSimilarTripsRequest.setLongitude("-75.284260");
		findSimilarTripsRequest.setStartAddress("357847589");
		findSimilarTripsRequest.setEndAddress("745903905");
		findSimilarTripsRequest.setStartDate("2009-06-10 08:15:00");
		findSimilarTripsRequest.setEndDate("2009-06-10 16:30:00");
		findSimilarTripsRequest.setNumMiles(0);
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.findSimilarTrips(findSimilarTripsRequest);
			System.out.println("Response is: " + findSimilarTripsRequest.getTitle()); 
			TestCase.assertNull(findSimilarTripsRequest.getTitle());
		}
		catch (AxisFault afe)
		{
			afe.printStackTrace();
			TestCase.fail(afe.getMessage());
		}
		catch (CarPoolServiceException cpe)
		{
			cpe.printStackTrace();
			TestCase.fail(cpe.getMessage());
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
			TestCase.fail(re.getMessage());
		}
	}
			
	@Test
	// test the CarPoolTripUserService::createOrUpdate method with success result
	public void testCreateOrUpdate()
	{
		CarPoolTripUserService carPoolTripService = new CarPoolTripUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolTripService.setSessionFactory(sessionFactory);
		
		
		Trip trip = new Trip();
		trip.setId("654321");
		
		trip.setTitle("Test Trip #" + UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 200));
		
		Organization organization = new Organization();
		organization.setId("12345");
		trip.setOrganization(organization);
		
		CarPoolLocationService carPoolLocationService = new CarPoolLocationService();
		carPoolLocationService.setSessionFactory(sessionFactory);
		Address startAddress = carPoolLocationService.getAddress("357847589");
		trip.setStartAddress(startAddress);
		
		Address endAddress = carPoolLocationService.getAddress("745903905");
		trip.setEndAddress(endAddress);
		
		Calendar calendar = Calendar.getInstance();
		int randomHour = UniqueIDGeneratorUtil.getRandomInt(new Random(), 5, 9);
		System.out.println("Random hour: " + randomHour);
		calendar.set(Calendar.HOUR_OF_DAY, randomHour);
		
		int randomMinute = UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 59);
		System.out.println("Random minute: " + randomMinute);
		calendar.set(Calendar.MINUTE, randomMinute);
		calendar.set(Calendar.SECOND, 0);
		
		int randomMonth = UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 11);
		System.out.println("Random month: " + randomMonth);
		calendar.set(Calendar.MONTH, randomMonth);
		
		int randomDay = UniqueIDGeneratorUtil.getRandomInt(new Random(), 1, 30);
		System.out.println("Random day: " + randomDay);
		calendar.set(Calendar.DAY_OF_MONTH, randomDay);
		calendar.set(Calendar.YEAR, 2009);

		trip.setStartDate(calendar.getTime());
		
		randomHour = UniqueIDGeneratorUtil.getRandomInt(new Random(), 5, 9);
		System.out.println("Random hour: " + randomHour);
		calendar.set(Calendar.HOUR_OF_DAY, randomHour);
		
		randomMinute = UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 59);
		System.out.println("Random minute: " + randomMinute);
		calendar.set(Calendar.MINUTE, randomMinute);
		
		trip.setEndDate(calendar.getTime());
		
		CarPoolUserService carPoolUserService = new CarPoolUserService();
		carPoolUserService.setSessionFactory(sessionFactory);
		User user = carPoolUserService.getUser("eborisow");
		
		TripUser tripUser = new TripUser();
		tripUser.setTripId(trip.getId());
		tripUser.setUserId(user.getId());
		tripUser.setType("PASSENGER");
		
		TestCase.assertTrue(carPoolTripService.createOrUpdate(tripUser));
	}
	
	@Test
	// test the CarPoolTripUserService::createOrUpdate method with fail result
	public void testCreateOrUpdateFailure()
	{
		CarPoolTripUserService carPoolTripUserService = new CarPoolTripUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolTripUserService.setSessionFactory(sessionFactory);
				
		Trip trip = new Trip();
		trip.setId("654321");
			
		TripUser tripUser = new TripUser();
		TestCase.assertEquals(false, carPoolTripUserService.createOrUpdate(tripUser));
	}
	
	@Test
	public void testDistanceKOPtoConshohocken()
	{
		// King of Prussia, PA
		float lat1 = 40.097062f;
		float lon1 = -75.385494f;
		
		// Conshohocken, PA
		float lat2 = 40.079556f;
		float lon2 = -75.303068f;

		double result = LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M');
		System.out.println("The distance (in Miles) is: " + result);
		
		double googleDistance = 7.0f;
		// distance plotted in google map
		TestCase.assertTrue(googleDistance > result);
	}
	
	@Test
	public void testDistanceKOPtoPhilly()
	{
		// King of Prussia, PA
		float lat1 = 40.097062f;
		float lon1 = -75.385494f;

		// Philadelphia, PA
		float lat2 = 39.956675f;
		float lon2 = -75.164051f;
		double result = LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M');
		System.out.println("The distance (in Miles) is: " + result);
		
		// distance plotted in google map
		double googleDistance = 19.7;
		
		TestCase.assertTrue(googleDistance > result);
	}
	
	@Test
	public void testDistanceKOPtoHarrisburg()
	{
		// King of Prussia, PA
		float lat1 = 40.097062f;
		float lon1 = -75.385494f;

		// Harrisburg, PA
		float lat2 = 40.276422f;
		float lon2 = -76.884928f;

		double result = LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M');
		
		System.out.println("The distance (in Miles) is: " + result);
		
		double googleDistance = 88.5;

		TestCase.assertTrue(googleDistance > result);
	}
}
