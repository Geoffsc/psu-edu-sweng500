package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.junit.Test;


import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.FindPlacesRequest;

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
}
