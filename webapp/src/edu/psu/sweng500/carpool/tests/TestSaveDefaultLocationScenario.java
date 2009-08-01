package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.junit.Test;

import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.Place;
import edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.UpdateSavedAddressesRequest;
import edu.psu.sweng500.carpool.services.utilities.UniqueIDGeneratorUtil;

public class TestSaveDefaultLocationScenario {

	@Test
	public void testSaveDefaultStartAddresses()
	{
		try
		{
			CarPoolServiceStub service = new CarPoolServiceStub();
			UpdateSavedAddressesRequest request = new UpdateSavedAddressesRequest();
			request.setUserId("gscholl");
			
			Place place = new Place();
			place.setId(UniqueIDGeneratorUtil.getUniqueId());
			place.setAddress1("100 Arrow Lane");
			// place.setCity("Philadelphia");
			place.setName("Geoff Home");
			// place.setPostalCode("19462");
			// place.setStateOrProvince("PA");
			place.setCategory("START");
			place.setLatitude(40.281266f);
			place.setLongitude(-75.36733f);
			
			Place[] places = new Place[1];
			places[0] = place;
			
			request.setSavedPlaces(places);
			
			edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SimpleMessageResponse response = service.updateSavedAddresses(request);
			
			System.out.println("Response is: " + response.getMessage().getIsSuccess());
			
			TestCase.assertTrue(response.getMessage().getIsSuccess());
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
	public void testSaveDefaultStartAddressesFailure()
	{
		try
		{
			CarPoolServiceStub service = new CarPoolServiceStub();
			UpdateSavedAddressesRequest request = new UpdateSavedAddressesRequest();
			request.setUserId("badUser");
			
			Place place = new Place();
			place.setId(UniqueIDGeneratorUtil.getUniqueId());
			place.setAddress1("100 Arrow Lane");
			// place.setCity("Philadelphia");
			place.setName("Geoff Home");
			// place.setPostalCode("19462");
			// place.setStateOrProvince("PA");
			place.setCategory("NOWHERE");
			place.setLatitude(40.281266f);
			place.setLongitude(-75.36733f);
			
			Place[] places = new Place[1];
			places[0] = place;
			
			request.setSavedPlaces(places);
			
			edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SimpleMessageResponse response = service.updateSavedAddresses(request);
			
			System.out.println("Response is: " + response.getMessage().getIsSuccess());
			
			TestCase.assertFalse(response.getMessage().getIsSuccess());
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
	public void testSaveDefaultEndAddresses()
	{
		try
		{
			CarPoolServiceStub service = new CarPoolServiceStub();
			UpdateSavedAddressesRequest request = new UpdateSavedAddressesRequest();
			request.setUserId("gscholl");
			
			Place place = new Place();
			place.setId(UniqueIDGeneratorUtil.getUniqueId());
			place.setAddress1("30 E Swedesford Rd");
			// place.setCity("Philadelphia");
			place.setName("PSU Great Valley");
			// place.setPostalCode("19462");
			// place.setStateOrProvince("PA");
			place.setCategory("END");
			place.setLatitude(40.06772f);
			place.setLongitude(-75.520821f);
			
			Place[] places = new Place[1];
			places[0] = place;
			
			request.setSavedPlaces(places);
			
			edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SimpleMessageResponse response = service.updateSavedAddresses(request);
			
			System.out.println("Response is: " + response.getMessage().getIsSuccess());
			
			TestCase.assertTrue(response.getMessage().getIsSuccess());
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
	public void testSaveDefaultEndAddressesFailure()
	{
		try
		{
			CarPoolServiceStub service = new CarPoolServiceStub();
			UpdateSavedAddressesRequest request = new UpdateSavedAddressesRequest();
			request.setUserId("badUser");
			
			Place place = new Place();
			place.setId(UniqueIDGeneratorUtil.getUniqueId());
			place.setAddress1("30 E Swedesford Rd");
			// place.setCity("Philadelphia");
			place.setName("PSU Great Valley");
			// place.setPostalCode("19462");
			// place.setStateOrProvince("PA");
			place.setCategory("NOWHERE");
			place.setLatitude(40.06772f);
			place.setLongitude(-75.520821f);
			
			Place[] places = new Place[1];
			places[0] = place;
			
			request.setSavedPlaces(places);
			
			edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.SimpleMessageResponse response = service.updateSavedAddresses(request);
			
			System.out.println("Response is: " + response.getMessage().getIsSuccess());
			
			TestCase.assertFalse(response.getMessage().getIsSuccess());
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
