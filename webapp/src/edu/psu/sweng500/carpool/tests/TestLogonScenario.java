package edu.psu.sweng500.carpool.tests;

import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.apache.axis2.AxisFault;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.services.CarPoolUserService;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceException;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub;
import edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.LoginElementRequest;


public class TestLogonScenario extends TestCase{
	
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
	
	//@Test
	// test the CarPoolUserService::getUser method with a valid user name
	public void testGetUser()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUser("eborisow");
		TestCase.assertNotNull(user);
	}
	
	//@Test
	// test the CarPoolUserService::getUser method with a invalid user name
	public void testGetBadUser()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUser("badUser");
		TestCase.assertNull(user);
	}
	
	//@Test
	// test the CarPoolUserService::getUser method with no user name
	public void testGetNoUser()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUser("");
		TestCase.assertNull(user);
	}
	
	//@Test
	// test the CarPoolUserService::getUserById method with a valid user id
	public void testGetUserById()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUserById("11111");
		TestCase.assertNotNull(user);
	}
	
	//@Test
	// test the CarPoolUserService::getUserById method with a invalid user id
	public void testGetBadUserById()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUserById("23432");
		TestCase.assertNull(user);
	}
	
	//@Test
	// test the CarPoolUserService::getUserById method with a no user id
	public void testGetNoUserById()
	{
		CarPoolUserService carPoolService = new CarPoolUserService();
		SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory(); 
		carPoolService.setSessionFactory(sessionFactory);
		
		User user = carPoolService.getUserById("");
		TestCase.assertNull(user);
	}	
}
