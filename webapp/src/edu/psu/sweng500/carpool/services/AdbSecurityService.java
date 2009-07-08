package edu.psu.sweng500.carpool.services;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.psu.sweng500.security.generated.beans.xsd.SimpleMessage;
import edu.psu.sweng500.security.generated.services.SecurityServiceException;
import edu.psu.sweng500.security.generated.services.SecurityServiceSkeletonInterface;
import edu.psu.sweng500.security.generated.services.xsd.LoginElementRequest;
import edu.psu.sweng500.security.generated.services.xsd.LoginElementResponse;
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

}
