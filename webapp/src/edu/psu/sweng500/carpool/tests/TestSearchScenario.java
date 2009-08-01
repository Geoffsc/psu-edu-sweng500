package edu.psu.sweng500.carpool.tests;

import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.services.CarPoolTripService;
import junit.framework.TestCase;

public class TestSearchScenario //extends TestCase
{

	private static final Log log = LogFactory.getLog(DatabaseTests.class);
	
	@Test
	// test the CarPoolTripService::findTrips method with matching user
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
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(edu.psu.sweng500.carpool.beans.TripUser.class);
		detachedCriteria.setFetchMode("trip.startAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.endAddress", FetchMode.JOIN);
		// detachedCriteria.setFetchMode("trip.members", FetchMode.JOIN);
		criteria.setDetachedCriteria(detachedCriteria);
		
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
		TestCase.assertEquals(false, tripUsers.isEmpty());
	}
	
	@Test
	// test the CarPoolTripService::findTrips method no matching user
	public void testGetTripsByUserNoMatch()
	{
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		
		Criterion userCriterion = Restrictions.eq("user.userName", "badUser");
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		List tripUsers = carPoolService.findTrips(criteria);
		TestCase.assertEquals(true, tripUsers.isEmpty());
	}

	// Scholl 20090625 
	@Test
	public void testGetTrips()
	{
		System.out.println("Executing getTrip test");
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		
		Criterion userCriterion = Restrictions.eq("user.userName", "eborisow");
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		
		Trip trip = new Trip();
		// String id = UniqueIDGeneratorUtil.getUniqueId();
		String id = "2301554351142306714";
		trip = carPoolService.getTrip(id);
		
		TestCase.assertNotNull(trip);
		
		if (trip != null)
		{
			System.out.println("Trip id: " + trip.getId());
			System.out.println("Trip title: " + trip.getTitle());
		}
	}
	

	@Test
	public void testGetNoTrips()
	{
		System.out.println("Executing getTrip test");
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		
		Criterion userCriterion = Restrictions.eq("user.userName", "eborisow");
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		
		Trip trip = new Trip();
		// String id = UniqueIDGeneratorUtil.getUniqueId();
		String id = "12334";
		trip = carPoolService.getTrip(id);
		
		TestCase.assertNull(trip);
		
		if (trip != null)
		{
			System.out.println("Trip id: " + trip.getId());
			System.out.println("Trip title: " + trip.getTitle());
		}
	}
		
	@Test
	// test the CarPoolTripService::findTrips method with no match found
	public void findTripsNoMatch()
	{
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[3];
		Criterion userCriterion = Restrictions.eq("user.userName", "badUser");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
		try
		{
			Date theDate = dateFormat.parse("5/1/09 00:00:00");
			Date theDate2 = dateFormat.parse("7/1/09 11:59:59");
			dateFormat.applyPattern("yyyy-MM-dd");
			log.debug("First date: " + dateFormat.format(theDate));
			log.debug("Second date: " + dateFormat.format(theDate2));
			// Criterion startDateCriterion = Restrictions.ge("trip.startDate", dateFormat.format(theDate) + " 00:00:00");
			// Criterion endDateCriterion = Restrictions.le("trip.startDate", dateFormat.format(theDate2) + " 11:59:59");
			Criterion startDateCriterion = Restrictions.ge("trip.startDate", theDate);
			Criterion endDateCriterion = Restrictions.le("trip.startDate", theDate2);
			allCriterion[0] = userCriterion;
			allCriterion[1] = startDateCriterion;
			allCriterion[2] = endDateCriterion;
			criteria.setCriterion(allCriterion);
			
			criteria.setSortField("trip.title");
			criteria.setAscending(true);
			
			criteria.setCriterion(allCriterion);
			
			List tripUsers = carPoolService.findTrips(criteria);
			TestCase.assertEquals(true, tripUsers.isEmpty());
		}
		catch (ParseException pe)
		{}
		catch (Exception e)
		{
			e.printStackTrace();
			TestCase.fail(e.getMessage());
		}
	}
	
	@Test
	// test the CarPoolTripService::findTrips method with match found
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
			TestCase.assertEquals(false, tripUsers.isEmpty());
		}
		catch (ParseException pe)
		{}
		catch (Exception e)
		{
			e.printStackTrace();
			TestCase.fail(e.getMessage());
		}
	}	
}
