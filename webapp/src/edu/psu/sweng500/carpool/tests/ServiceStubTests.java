package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.Address;

import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindSimilarTripsRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.GetTripsByUserRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.GetUserRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.GetUserResponse;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Organization;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Parameter;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Participant;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Place;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SearchTripsRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Trip;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.TripUser;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripResponse;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripUserRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.User;
import edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.carpool.utilities.LocationUtils;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceException;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.LoginElementRequest;

public class ServiceStubTests
{
	
	public void testFindPlaces()
	{
		FindPlacesRequest request = new FindPlacesRequest();
		request.setUserName("eborisow");
		request.setType("Starting");
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.findPlaces(request);
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
	
	
	public void testLoginSuccess()
	{
		LoginElementRequest loginRequest = new LoginElementRequest();
		loginRequest.setUserName("eborisow");
		loginRequest.setPassword("password");
		
		try
		{
			SecurityServiceStub serviceStub = new SecurityServiceStub();
			serviceStub.login(loginRequest);
		}
		catch (AxisFault afe)
		{
			afe.printStackTrace();
			TestCase.fail(afe.getMessage());
		}
		catch (SecurityServiceException sse)
		{
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getErrorCode());
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getErrorDescription());
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getTraceMessage());
			sse.printStackTrace();
			TestCase.fail(sse.getMessage());
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
			TestCase.fail(re.getMessage());
		}
	}
	
	
	public void testLoginFailure()
	{
		LoginElementRequest loginRequest = new LoginElementRequest();
		loginRequest.setUserName("eborisow");
		loginRequest.setPassword("test");
		
		try
		{
			SecurityServiceStub serviceStub = new SecurityServiceStub();
			serviceStub.login(loginRequest);
		}
		catch (AxisFault afe)
		{
			afe.printStackTrace();
			TestCase.fail(afe.getMessage());
		}
		catch (SecurityServiceException sse)
		{
			int errorCode = sse.getFaultMessage().getSecurityServiceException().getErrorCode();
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getErrorCode());
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getErrorDescription());
			System.out.println(sse.getFaultMessage().getSecurityServiceException().getTraceMessage());
			sse.printStackTrace();
			
			TestCase.assertEquals(6000, errorCode);
		}
		catch (RemoteException re)
		{
			re.printStackTrace();
			TestCase.fail(re.getMessage());
		}
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
	
	
	public void testGetTripsByUser()
	{
		GetTripsByUserRequest request = new GetTripsByUserRequest();
		request.setUserId("eborisow");
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.getTripsByUser(request);
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
	
	
	public void testFindTrips()
	{
		SearchTripsRequest request = new SearchTripsRequest();
		
		Parameter[] parameters = new Parameter[2];
		Parameter param1 = new Parameter();
		param1.setName("userName");
		param1.setValue("eborisow");
		parameters[0] = param1;
		
		Parameter param2 = new Parameter();
		param2.setName("startDate");
		param2.setValue("2009-05-31");
		parameters[1] = param2;
		
		// SearchCriteria searchCriteria = new SearchCriteria();
		// searchCriteria.setSearchParameters(parameters);
		// request.setCriteria(searchCriteria);
		request.setSearchParameters(parameters);
		try
		{
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			serviceStub.findTrips(request);
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
	
	@Test
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
	
	public void testGetUser()
	{
		try
		{
			GetUserRequest userRequest = new GetUserRequest();
			userRequest.setUserId("eborisow");
			CarPoolServiceStub serviceStub = new CarPoolServiceStub();
			GetUserResponse userResponse = serviceStub.getUser(userRequest);
			if (userResponse != null)
			{
				User user = userResponse.getUser();
				if (user != null)
				{
					System.out.println("Given name: " + user.getGivenName());
				}
			}
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
	
	
	public void testUpdateTripUser()
	{
		try
		{
			CarPoolServiceStub service = new CarPoolServiceStub();
			UpdateTripUserRequest request = new UpdateTripUserRequest();
			request.setTripId("238912391");
			Participant participant = new Participant();
			participant.setType("PASSENGER");
			TripUser tripUser = new TripUser();
			tripUser.setId("11111");
			participant.setUser(tripUser);
			request.setParticipant(participant);
			
			edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SimpleMessageResponse response = service.updateTripUser(request);
			System.out.println("Response is: " + response.getMessage().getIsSuccess());
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
	
	
	public void testDistance()
	{
		float lat1 = 40.282016f;
		float lon1 = -75.377212f;
		
		float lat2 = 40.2809f;
		float lon2 = -75.3705f;
		
		System.out.println("The distance is: " + LocationUtils.getDistance(lat1, lon1, lat2, lon2, 'M'));
	}
}
