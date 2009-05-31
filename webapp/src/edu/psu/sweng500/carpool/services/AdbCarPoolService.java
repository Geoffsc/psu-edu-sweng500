package edu.psu.sweng500.carpool.services;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;
import edu.psu.sweng500.carpool.generated.beans.xsd.PlaceList;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse;

public class AdbCarPoolService implements CarPoolServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbCarPoolService.class);
	
	private ICarPoolLocationService carPoolLocationService;
	private ICarPoolUserService carPoolUserService;
	
	public void setCarPoolLocationService(
			ICarPoolLocationService carPoolLocationService)
	{
		this.carPoolLocationService = carPoolLocationService;
	}
	
	public void setCarPoolUserService(ICarPoolUserService carPoolUserService)
	{
		this.carPoolUserService = carPoolUserService;
	}

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
		place1.setAddress1("127 Surrey Lane");
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
	
	public FindPlacesNearLocationResponse findPlacesNearLocation(
			FindPlacesNearLocationRequest findPlacesNearLocationRequest)
			throws CarPoolServiceException
	{
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[4];
		
		Criterion latLECriterion = Restrictions.le("latitude", 180f);
		Criterion latGECriterion = Restrictions.ge("latitude", -180f);
		Criterion longLECriterion = Restrictions.le("longitude", 180f);
		Criterion longGECriterion = Restrictions.ge("longitude", -180f);
		
		allCriterion[0] = latLECriterion;
		allCriterion[1] = latGECriterion;
		allCriterion[2] = longLECriterion;
		allCriterion[3] = longGECriterion;
		criteria.setCriterion(allCriterion);
		
		List addresses = this.carPoolLocationService.findLocationsNearLocation(criteria);
		
		Place[] places = new Place[addresses.size()];
		Iterator addrIter = addresses.iterator();
		int count = 0;
		while (addrIter.hasNext())
		{
			Address address = (Address) addrIter.next();
			Place place = new Place();
			place.setAddress1(address.getAddress1());
			place.setAddress2(address.getAddress2());
			place.setId(address.getId());
			place.setLatitude(address.getLatitude());
			place.setLongitude(address.getLongitude());
			place.setName(address.getName());
			
			if (log.isDebugEnabled())
			{
				log.debug("The address is: " + address.getAddress1());
			}
			places[count++] = place;
		}
		PlaceList placeList = new PlaceList();
		placeList.setPlace(places);
		
		FindPlacesNearLocationResponse response = new FindPlacesNearLocationResponse();
		response.setPlaces(placeList);
		return response;
	}
	
	public GetUserResponse getUser(GetUserRequest getUserRequest)
			throws CarPoolServiceException
	{
		String id = getUserRequest.getUserId();
		User user = this.carPoolUserService.getUser(id);
		
		edu.psu.sweng500.carpool.generated.beans.xsd.User serviceUser = new edu.psu.sweng500.carpool.generated.beans.xsd.User();
		serviceUser.setId(user.getId());
		serviceUser.setGivenName(user.getGivenName());
		serviceUser.setSurName(user.getSurName());
		
		Set savedAddresses = user.getSavedAddresses();
		Place[] places = new Place[savedAddresses.size()];
		Iterator savedAddrIter = savedAddresses.iterator();
		int count = 0;
		while (savedAddrIter != null)
		{
			Address savedAddress = (Address) savedAddrIter.next();
			Place place = new Place();
			place.setId(savedAddress.getId());
			place.setAddress1(savedAddress.getAddress1());
			place.setAddress2(savedAddress.getAddress2());
			place.setLatitude(savedAddress.getLatitude());
			place.setLongitude(savedAddress.getLongitude());
			
			places[count++] = place;
		}
		serviceUser.setSavedPlaces(places);
		
		GetUserResponse response = new GetUserResponse();
		response.setUser(serviceUser);
		return null;
	}

}
