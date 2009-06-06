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
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Organization;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Participant;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Place;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Trip;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateTripResponse;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.User;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceException;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.LoginElementRequest;

public class ServiceStubTests
{
	@Test
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
	
	@Test
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
	
	@Test
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
	
	@Test
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
		DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		
		trip.setStartDate(format.format(startDate));
		trip.setEndDate(format.format(endDate));
		
		Participant[] participants = new Participant[1];
		Participant participant = new Participant();
		User user1 = new User();
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
}
