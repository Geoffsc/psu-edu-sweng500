package edu.psu.sweng500.carpool.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.psu.sweng500.carpool.generated.services.SecurityServiceException;
import edu.psu.sweng500.carpool.generated.services.SecurityServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.LoginElementRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.LoginElementResponse;

public class AdbSecurityService implements SecurityServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbSecurityService.class);

	public LoginElementResponse login(LoginElementRequest loginElementRequest)
			throws SecurityServiceException
	{
		
		if (log.isDebugEnabled())
		{
			log.debug("User name: " + loginElementRequest.getUserName() + "/password: " + loginElementRequest.getPassword());
		}
		LoginElementResponse response = new LoginElementResponse();
		response.set_return(true);
		return response;
	}

}
