package edu.psu.sweng500.carpool.tests;

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
		
		User user = carPoolService.getUser("eborisow");
		Set savedAddresses = user.getSavedAddresses();
		Iterator addrIter = savedAddresses.iterator();
		while (addrIter.hasNext())
		{
			UserAddress userAddress = (UserAddress) addrIter.next();
			if (userAddress != null)
			{
				Address address = userAddress.getAddress();
				if (address != null)
				{
					System.out.println("Address line 1: " + address.getAddress1());
				}
				System.out.println("Type: " + userAddress.getType());
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
	
	@Test
	public void findTrips()
	{
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[5];
		// Criterion userCriterion = Restrictions.eq("user.userName", "eborisow");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		try
		{
			Date theDate = dateFormat.parse("6/10/09 08:15:00");
			Date theDate2 = dateFormat.parse("6/10/09 16:30:00");
			dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
			log.debug("First date: " + dateFormat.format(theDate));
			log.debug("Second date: " + dateFormat.format(theDate2));
			// Criterion startDateCriterion = Restrictions.ge("trip.startDate", dateFormat.format(theDate) + " 00:00:00");
			// Criterion endDateCriterion = Restrictions.le("trip.startDate", dateFormat.format(theDate2) + " 11:59:59");
			// Criterion startDateCriterion = Restrictions.ge("trip.startDate", theDate);
			// Criterion endDateCriterion = Restrictions.le("trip.startDate", theDate2);
			
			Criterion startDateCriterion = Restrictions.eq("startDate", theDate);
			Criterion endDateCriterion = Restrictions.eq("endDate", theDate2);
			Criterion orgCriterion = Restrictions.eq("organization.id", "12345");
			Criterion startAddrCriterion = Restrictions.eq("startAddress.id", "357847589");
			Criterion endAddrCriterion = Restrictions.eq("endAddress.id", "745903905");
			
			// allCriterion[0] = userCriterion;
			allCriterion[0] = startDateCriterion;
			allCriterion[1] = endDateCriterion;
			allCriterion[2] = orgCriterion;
			allCriterion[3] = startAddrCriterion;
			allCriterion[4] = endAddrCriterion;
			criteria.setCriterion(allCriterion);
			
			criteria.setSortField("title");
			criteria.setAscending(true);
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(edu.psu.sweng500.carpool.beans.Trip.class);
			detachedCriteria.setFetchMode("startAddress", FetchMode.JOIN);
			detachedCriteria.setFetchMode("endAddress", FetchMode.JOIN);
			detachedCriteria.setFetchMode("organization", FetchMode.JOIN);
			criteria.setDetachedCriteria(detachedCriteria);
			
			criteria.setCriterion(allCriterion);
			
			List tripUsers = carPoolService.findTripsOnly(criteria);
			
			Iterator tripUsersIter = tripUsers.iterator();
			while (tripUsersIter.hasNext())
			{
				Trip trip = (Trip) tripUsersIter.next();
				log.debug("Start address: " + trip.getStartAddress().getAddress1());
			}
		}
		catch (ParseException pe)
		{}
		catch (Exception e)
		{
			e.printStackTrace();
			TestCase.fail(e.getMessage());
		}
	}
	
	/*
	public void testSimilarTripSearch()
	{
		Trip trip = new Trip();
		String startAddress = "357847589";
		String endAddress = "745903905";
		String startDate = "2009-05-31";
		String orgId = "12345";
		
		List criterionList = new ArrayList();
		if (startAddress != null)
		{
			Criterion criterion = Restrictions.eq("trip.startAddress.id", startAddress);
			criterionList.add(criterion);
		}
		
		if (endAddress != null)
		{
			Criterion criterion = Restrictions.eq("trip.endAddress.id", trip.getStartAddress());
			criterionList.add(criterion);
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (trip.getStartDate() != null)
		{
			try
			{
				Date theDateBegin = format.parse(trip.getStartDate() + " 00:00:00");
				Date theDateEnd = format.parse(trip.getStartDate() + " 23:59:59");
				Criterion startDateCriterion = Restrictions.ge("trip.startDate", theDateBegin);
				Criterion endDateCriterion = Restrictions.le("trip.startDate", theDateEnd);
				
				criterionList.add(startDateCriterion);
				criterionList.add(endDateCriterion);
			}
			catch (ParseException pe)
			{
				pe.printStackTrace();
			}
		}
		
		if (trip.getEndDate() != null)
		{
			try
			{
				Date theDateBegin = format.parse(trip.getEndDate() + " 00:00:00");
				Date theDateEnd = format.parse(trip.getEndDate() + " 23:59:59");
				Criterion startDateCriterion = Restrictions.ge("trip.endDate", theDateBegin);
				Criterion endDateCriterion = Restrictions.le("trip.endDate", theDateEnd);
				
				criterionList.add(startDateCriterion);
				criterionList.add(endDateCriterion);
			}
			catch (ParseException pe)
			{
				pe.printStackTrace();
			}
		}
		
		if (trip.getOrganizationId() != null)
		{
			Criterion criterion = Restrictions.eq("trip.organization.id", trip.getOrganizationId());
			criterionList.add(criterion);
		}
		
		Criterion[] criterion = new Criterion[criterionList.size()];
		Iterator criterionListIter = criterionList.iterator();
		int count = 0;
		while (criterionListIter.hasNext())
		{
			criterion[count++] = (Criterion) criterionListIter.next();
		}
		FindCriteria criteria = new FindCriteria();
		criteria.setCriterion(criterion);
		
		CarPoolTripService carPoolTripService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory();
		carPoolTripService.setSessionFactory(sessionFactory);
		List trips = carPoolTripService.findTrips(criteria);
		Iterator tripsIter = trips.iterator();
		while (tripsIter.hasNext())
		{
			TripUser resultTripUser = (TripUser) tripsIter.next();
			Trip resultTrip = resultTripUser.getTrip();
			log.debug("Trip id: " + resultTrip.getId());
		}
		-- comment
	}
*/
	
	public static void main(String[] args)
	{
		DatabaseTests tests = new DatabaseTests();
		for (int i = 0; i < 12; i++)
		{
			System.out.println(UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 11));
		}
	}
}
