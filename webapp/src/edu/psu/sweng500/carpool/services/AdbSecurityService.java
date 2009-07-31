package edu.psu.sweng500.carpool.services;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.psu.sweng500.carpool.beans.Organization;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.exceptions.SecurityException;
import edu.psu.sweng500.security.generated.beans.xsd.SimpleMessage;
import edu.psu.sweng500.security.generated.services.SecurityServiceException;
import edu.psu.sweng500.security.generated.services.SecurityServiceSkeletonInterface;
import edu.psu.sweng500.security.generated.services.xsd.LoginElementRequest;
import edu.psu.sweng500.security.generated.services.xsd.LoginElementResponse;
import edu.psu.sweng500.security.generated.services.xsd.RegisterUserRequest;
import edu.psu.sweng500.security.generated.services.xsd.RegisterUserResponse;
import edu.psu.sweng500.security.generated.services.xsd.SecurityServiceExceptionE;

public class AdbSecurityService implements SecurityServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbSecurityService.class);
	
	private ISecurityServiceWrapper securityService;
	
	public void setSecurityService(ISecurityServiceWrapper securityService)
	{
		this.securityService = securityService;
	}

	public LoginElementResponse login(LoginElementRequest loginElementRequest)
			throws SecurityServiceException
	{
		
		if (log.isDebugEnabled())
		{
			log.debug("User name: " + loginElementRequest.getUserName() + "/password: " + loginElementRequest.getPassword());
		}
		
		try
		{
			boolean isLoggedIn;
			isLoggedIn = this.securityService.login(loginElementRequest.getUserName(), loginElementRequest.getPassword());
			
			LoginElementResponse response = new LoginElementResponse();
			SimpleMessage message = new SimpleMessage();
			message.setIsSuccess(isLoggedIn);
			message.setErrorCode(0);
			message.setErrorDescription("");
			message.setMessage("");
			message.setTraceMessage("");
			
			response.set_return(message);
			return response;
		}
		catch (edu.psu.sweng500.carpool.exceptions.SecurityException se) 
		{
			edu.psu.sweng500.security.generated.exceptions.xsd.SecurityServiceException localException = new edu.psu.sweng500.security.generated.exceptions.xsd.SecurityServiceException();
			localException.setErrorCode(se.getErrorCode());
			localException.setErrorDescription(se.getErrorDescription());
			localException.setMessage(se.getMessage());
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			se.printStackTrace(pw);
			localException.setTraceMessage(sw.toString());
			
			SecurityServiceExceptionE exceptionElement = new SecurityServiceExceptionE();
			exceptionElement.setSecurityServiceException(localException);
			
			SecurityServiceException sse = new SecurityServiceException();
			sse.setFaultMessage(exceptionElement);
			// throw sse;
			
			LoginElementResponse response = new LoginElementResponse();
			SimpleMessage message = new SimpleMessage();
			message.setIsSuccess(false);
			message.setErrorCode(localException.getErrorCode());
			message.setErrorDescription(localException.getErrorDescription());
			message.setMessage("");
			message.setTraceMessage("");
			
			response.set_return(message);
			return response;
		}
	}

	public RegisterUserResponse registerUser(
			RegisterUserRequest registerUserRequest)
			throws SecurityServiceException
	{
		edu.psu.sweng500.security.generated.beans.xsd.User serviceUser = registerUserRequest.getUser();
		User user = convertToUser(serviceUser);
		if (user != null)
		{
			try
			{
				boolean isSuccess = this.securityService.createOrUpdate(user);
				
				SimpleMessage message = new SimpleMessage();
				message.setIsSuccess(isSuccess);
				message.setErrorCode(0);
				message.setErrorDescription("");
				message.setMessage("");
				message.setTraceMessage("");
				
				RegisterUserResponse response = new RegisterUserResponse();
				response.set_return(message);
				return response;
			}
			catch (SecurityException se)
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				se.printStackTrace(pw);
				log.error(sw.toString());
			}
		}
		
		SimpleMessage message = new SimpleMessage();
		message.setIsSuccess(false);
		message.setErrorCode(8888);
		message.setErrorDescription("The user could not be created");
		message.setMessage("The user could not be created");
		message.setTraceMessage("");
		
		RegisterUserResponse response = new RegisterUserResponse();
		response.set_return(message);
		return response;
	}
	
	private User convertToUser (edu.psu.sweng500.security.generated.beans.xsd.User user)
	{
		User newUser = new User();
		newUser.setGivenName(user.getGivenName());
		newUser.setId(user.getId());
		newUser.setPassword(user.getPassword());
		newUser.setUserName(user.getUserName());
		newUser.setSurName(user.getSurName());
		
		Organization organization = new Organization();
		edu.psu.sweng500.security.generated.beans.xsd.Organization serviceOrg = user.getOrganization();
		organization.setId(serviceOrg.getId());
		
		newUser.setOrganization(organization);
		return newUser;
	}
}
