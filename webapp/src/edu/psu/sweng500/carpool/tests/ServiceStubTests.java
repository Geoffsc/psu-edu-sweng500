package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.junit.Test;


import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindPlacesRequest;
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
}
