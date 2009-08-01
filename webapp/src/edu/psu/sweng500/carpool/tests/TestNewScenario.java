package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindSimilarTripsRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Organization;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Participant;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Place;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Trip;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.TripUser;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripResponse;
import edu.psu.sweng500.carpool.services.CarPoolLocationService;
import edu.psu.sweng500.carpool.services.CarPoolTripService;
import edu.psu.sweng500.carpool.services.CarPoolTripUserService;
import edu.psu.sweng500.carpool.services.CarPoolUserService;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.utilities.LocationUtils;

public class TestNewScenario extends TestCase{

	
	public void testFindMatchingTrip()
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
	

	public void testFindSimilarTrips()
	{
		FindSimilarTripsRequest findSimilarTripsRequest = new FindSimilarTripsRequest();
		// findSimilarTripsRequest.setStartAddress("357847589");
		// findSimilarTripsRequest.setStartDate("2009-05-31");
		findSimilarTripsRequest.setOrganizationId("12345");
		findSimilarTripsRequest.setLatitude("40.123343");
		findSimilarTripsRequest.setLongitude("-75.284260");
		findSimilarTripsRequest.setNumMiles(5);
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.findSimilarTrips(findSimilarTripsRequest);
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
		
		TestCase.assertEquals(true, carPoolService.createOrUpdateTrip(trip));
	}
	
	//@Test
	// test the CarPoolTripService::createOrUpdateTrip creating fail
	public void createTripFail()
	{
		System.out.println("Executing createTrip test");
		CarPoolTripService carPoolService = new CarPoolTripService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		Trip trip = new Trip();
		trip.setId(UniqueIDGeneratorUtil.getUniqueId());
		TestCase.assertEquals(false, carPoolService.createOrUpdateTrip(trip));
	}
	
	//@Test
	// test the CarPoolTripUserService::createOrUpdate method with success result
	public void testCreateOrUpdateSuccess()
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
		
		TestCase.assertEquals(true, carPoolTripService.createOrUpdate(tripUser));
	}
	//@Test
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
	
	public void testUpdateTrip()
	{
		Trip trip = new Trip();
		trip.setId(UniqueIDGeneratorUtil.getUniqueId());
		
		Organization organization = new edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Organization();
		organization.setId("12345");		
		trip.setOrganization(organization);
		trip.setTitle("Trip Title #" + UniqueIDGeneratorUtil.getRandomInt(new Random(), 0, 200));
		
		Place startPlace = new Place();
		startPlace.setId("357847589");
		trip.setStartAddress(startPlace);
		
		Place endPlace = new Place();
		endPlace.setId("859085052");
		trip.setEndAddress(endPlace);
		
		Date startDate = Calendar.getInstance().getTime();
		Date endDate = Calendar.getInstance().getTime();
		// DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		trip.setStartDate(format.format(startDate));
		trip.setEndDate(format.format(endDate));
		
		Participant[] participants = new Participant[1];
		Participant participant = new Participant();
		TripUser user1 = new TripUser();
		user1.setId("11111");
		participant.setUser(user1);
		participant.setType("DRIVER");
		participants[0] = participant;
		
		trip.setParticipants(participants);
		
		UpdateTripRequest request = new UpdateTripRequest();
		request.setTrip(trip);
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			UpdateTripResponse response = serviceStub.updateTrip(request);
			
			TestCase.assertEquals(true, response.getIsSuccess());
		}
		catch (AxisFault afe)
		{
			afe.printStackTrace();
			TestCase.fail(afe.getMessage());
		}
		catch (CarPoolServiceException sse)
		{
			sse.printStackTrace();
			
			// TestCase.assertEquals(6000, errorCode);
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
			TestCase.fail(re.getMessage());
		}
	}
	

	//@Test
	// test the CarPoolTripService::createOrUpdateTrip where update trip fails
	public void updateTripFails()
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
		
		Trip notCompletetrip = new Trip();
		notCompletetrip.setId("3443202757641022279");
		TestCase.assertEquals(false, carPoolService.createOrUpdateTrip(notCompletetrip));
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

		System.out.println("The distance (in Miles) is: " + LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M'));
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

		System.out.println("The distance (in Miles) is: " + LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M'));
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

		System.out.println("The distance (in Miles) is: " + LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M'));
	}
	
}
