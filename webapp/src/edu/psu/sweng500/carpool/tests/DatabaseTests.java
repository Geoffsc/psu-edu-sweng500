package edu.psu.sweng500.carpool.tests;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.Organization;
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.services.CarPoolLocationService;
import edu.psu.sweng500.carpool.services.CarPoolTripService;
import edu.psu.sweng500.carpool.services.CarPoolTripUserService;
import edu.psu.sweng500.carpool.services.CarPoolUserService;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;

public class DatabaseTests
{
	private static final Log log = LogFactory.getLog(DatabaseTests.class);
	
	
	public void testGetUser()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUser("11111");
		Set savedAddresses = user.getSavedAddresses();
		Iterator addrIter = savedAddresses.iterator();
		while (addrIter.hasNext())
		{
			Address address = (Address) addrIter.next();
			if (address != null)
			{
				System.out.println("Address line 1: " + address.getAddress1());
			}
		}
	}
	
	
	public void testFindLocations()
	{
		CarPoolLocationService carPoolService = new CarPoolLocationService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[4];
		
		Criterion latLECriterion = Restrictions.le("latitude", 180f);
		Criterion latGECriterion = Restrictions.ge("latitude", -180f);
		Criterion longLECriterion = Restrictions.le("longitude", 180f);
		Criterion longGECriterion = Restrictions.ge("longitude", -180f);
		
		allCriterion[0] = latLECriterion;
		allCriterion[1] = latGECriterion;
		allCriterion[2] = longLECriterion;
		allCriterion[3] = longGECriterion;
		criteria.setCriterion(allCriterion);
		
		List addresses = carPoolService.findLocationsNearLocation(criteria);
		Iterator addrIter = addresses.iterator();
		while (addrIter.hasNext())
		{
			Address address = (Address) addrIter.next();
			if (address != null)
			{
				System.out.println("Address line 1: " + address.getAddress1());
			}
		}
	}
	
	
	public void testGetTripsByUser()
	{
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		
		Criterion userCriterion = Restrictions.eq("user.userName", "eborisow");
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		List tripUsers = carPoolService.findTrips(criteria);
		Iterator tripUsersIter = tripUsers.iterator();
		while (tripUsersIter.hasNext())
		{
			TripUser tripUser = (TripUser) tripUsersIter.next();
			if (tripUser != null)
			{
				Trip trip = tripUser.getTrip();
				System.out.println("Trip id: " + trip.getId());
				
				Address startAddress = trip.getStartAddress();
				System.out.println("Start address: " + startAddress.getAddress1());
				
				Address endAddress = trip.getEndAddress();
				System.out.println("End address: " + endAddress.getAddress1());
			}
		}
	}
	
	
	public void createTrip()
	{
		System.out.println("Executing createTrip test");
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		Trip trip = new Trip();
		trip.setId(UniqueIDGeneratorUtil.getUniqueId());
		
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
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MONTH, 5);
		calendar.set(Calendar.DAY_OF_MONTH, 12);
		calendar.set(Calendar.YEAR, 2009);

		trip.setStartDate(calendar.getTime());
		
		calendar.set(Calendar.HOUR_OF_DAY, 17);
		calendar.set(Calendar.MINUTE, 45);
		
		trip.setEndDate(calendar.getTime());
		
		carPoolService.createOrUpdateTrip(trip);
	}
	
	@Test
	public void updateTrip()
	{
		System.out.println("Executing updateTrip test");
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		Trip trip = new Trip();
		trip.setId("3443202757641022279");
		
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
		
		CarPoolTripUserService carPoolTripUserService = new CarPoolTripUserService();
		carPoolTripUserService.setSessionFactory(sessionFactory);
		carPoolTripUserService.createOrUpdate(tripUser);
		
		/*
		Set users = new HashSet();
		users.add(user);
		trip.setMembers(users);
		*/
		
		carPoolService.createOrUpdateTrip(trip);
	}
	
	public static void main(String[] args)
	{
		DatabaseTests tests = new DatabaseTests();
		for (int i = 0; i < 12; i++)
		{
			System.out.println(UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 11));
		}
	}
}
