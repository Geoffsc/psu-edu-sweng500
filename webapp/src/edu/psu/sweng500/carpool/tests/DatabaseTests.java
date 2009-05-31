package edu.psu.sweng500.carpool.tests;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.services.CarPoolLocationService;
import edu.psu.sweng500.carpool.services.CarPoolUserService;

public class DatabaseTests
{
	private static final Log log = LogFactory.getLog(DatabaseTests.class);
	
	@Test
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
	
	@Test
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
}
