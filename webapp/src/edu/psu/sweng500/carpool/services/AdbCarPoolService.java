package edu.psu.sweng500.carpool.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.psu.sweng500.carpool.generated.services.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse;

public class AdbCarPoolService implements CarPoolServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbCarPoolService.class);

	public FindPlacesResponse findPlaces(FindPlacesRequest findPlacesRequest)
			throws CarPoolServiceException
	{
		if (log.isDebugEnabled())
		{
			log.debug("The incoming user name is: " + findPlacesRequest.getUserName());
			log.debug("The incoming type is: " + findPlacesRequest.getType());
		}
		
		edu.psu.sweng500.carpool.generated.beans.xsd.Place[] generatedPlaces = new edu.psu.sweng500.carpool.generated.beans.xsd.Place[1];
		edu.psu.sweng500.carpool.generated.beans.xsd.Place place1 = new edu.psu.sweng500.carpool.generated.beans.xsd.Place();
		place1.setAddress("127 Surrey Lane");
		place1.setDescription("Home");
		place1.setId("12345");
		place1.setName("Home - 127 Surrey Lane");
		place1.setLatitude(-75.371f);
		place1.setLongitude(40.279f);
		generatedPlaces[0] = place1;
		
		FindPlacesResponse response = new FindPlacesResponse();
		response.setPlaces(generatedPlaces);
		
		return response;
	}

}
