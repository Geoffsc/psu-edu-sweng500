package edu.psu.sweng500.carpool.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.Organization;
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.generated.beans.xsd.Participant;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse;
import edu.psu.sweng500.carpool.services.utilities.ServiceObjectMapper;
import edu.psu.sweng500.carpool.utilities.LocationUtils;

public class AdbCarPoolService implements CarPoolServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbCarPoolService.class);
	
	private ICarPoolLocationService carPoolLocationService;
	private ICarPoolUserService carPoolUserService;
	private ICarPoolTripService carPoolTripService;
	private ICarPoolTripUserService carPoolTripUserService;
	
	public void setCarPoolLocationService(
			ICarPoolLocationService carPoolLocationService)
	{
		this.carPoolLocationService = carPoolLocationService;
	}
	
	public void setCarPoolUserService(ICarPoolUserService carPoolUserService)
	{
		this.carPoolUserService = carPoolUserService;
	}
	
	public void setCarPoolTripService(ICarPoolTripService carPoolTripService)
	{
		this.carPoolTripService = carPoolTripService;
	}
	
	public void setCarPoolTripUserService(
			ICarPoolTripUserService carPoolTripUserService)
	{
		this.carPoolTripUserService = carPoolTripUserService;
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
		if (log.isDebugEnabled())
		{
			log.debug("Starting findPlacesNearLocation");
		}
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
		// criteria.setCriterion(allCriterion);
		
		FindPlacesNearLocationResponse response = new FindPlacesNearLocationResponse();
		
		int numMilesProp = findPlacesNearLocationRequest.getNumMiles();
		if (numMilesProp <= 0)
		{
			return response; 
		}
		
		float lat1 = findPlacesNearLocationRequest.getLatitude();
		float lon1 = findPlacesNearLocationRequest.getLongitude();
		if (lat1 < -90 || lat1 > 90 || lon1 < -180 || lon1 > 180)
		{
			return response;
		}
		
		List addresses = this.carPoolLocationService.findLocationsNearLocation(criteria);
		
		if (addresses == null || addresses.size() <= 0)
		{
			return response;
		}
		
		List foundAddresses = new ArrayList();
		// figure out which addresses are within our range
		Iterator addrIter = addresses.iterator();
		while (addrIter.hasNext())
		{
			Address address = (Address) addrIter.next();
			double distance = LocationUtils.getDistance(lat1, lon1, address.getLatitude(), address.getLongitude(), 'M');
			if (distance <= numMilesProp)
			{
				foundAddresses.add(address);
			}
		}
		
		Place[] places = new Place[foundAddresses.size()];
		addrIter = foundAddresses.iterator();
		int count = 0;
		while (addrIter.hasNext())
		{
			Address address = (Address) addrIter.next();
			Place place = ServiceObjectMapper.convertToPlace(address);
			
			if (log.isDebugEnabled())
			{
				log.debug("The address is: " + address.getAddress1());
			}
			places[count++] = place;
		}
		// PlaceList placeList = new PlaceList();
		// placeList.setPlace(places);
		
		response.setPlace(places);
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
	
	public GetTripsByUserResponse getTripsByUser(
			GetTripsByUserRequest getTripsByUserRequest)
			throws CarPoolServiceException
	{
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		
		Criterion userCriterion = Restrictions.eq("user.userName", getTripsByUserRequest.getUserId());
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		List tripUsers = carPoolTripService.findTrips(criteria);
		Iterator tripUsersIter = tripUsers.iterator();
		
		// TripList tripList = new TripList();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip[] serviceTrips = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip[tripUsers.size()];
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		int count = 0;
		while (tripUsersIter.hasNext())
		{
			TripUser tripUser = (TripUser) tripUsersIter.next();
			if (tripUser != null)
			{
				Trip trip = tripUser.getTrip();
				edu.psu.sweng500.carpool.generated.beans.xsd.Trip serviceTrip = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip();
				if (trip != null)
				{
					System.out.println("Trip id: " + trip.getId());
					
					// map database Trip to service Trip
					serviceTrip.setId(trip.getId());
					serviceTrip.setTitle(trip.getTitle());
					serviceTrip.setOrganization(ServiceObjectMapper.convertToServiceOrganization(trip.getOrganization()));
					
					// use a class to map address to service address
					Place startPlace = ServiceObjectMapper.convertToPlace(trip.getStartAddress());
					Place endPlace = ServiceObjectMapper.convertToPlace(trip.getEndAddress());
					
					serviceTrip.setStartAddress(startPlace);
					serviceTrip.setEndAddress(endPlace);
					Calendar startCalendar = Calendar.getInstance();
					if (trip.getStartDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setStartDate(format.format(trip.getStartDate()));
					}
					
					Calendar endCalendar = Calendar.getInstance();
					if (trip.getEndDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setEndDate(format.format(trip.getEndDate()));
					}
					
					
					serviceTrips[count++] = serviceTrip;
				}
				
				User user = carPoolUserService.getUserById(tripUser.getUserId());
				edu.psu.sweng500.carpool.generated.beans.xsd.TripUser serviceTripUser = ServiceObjectMapper.convertToTripUser(user);
				
				Participant participant = new Participant();
				participant.setUser(serviceTripUser);
				participant.setType(tripUser.getType());
				
				serviceTrip.addParticipants(participant);
				
				response.addTrip(serviceTrip);
			}
		}
		// tripList.setTrip(serviceTrips);
		
		
		// response.setTrips(tripList);
		
		return response;
	}
	
	public UpdateTripResponse updateTrip(UpdateTripRequest updateTripRequest)
			throws CarPoolServiceException
	{
		Trip trip = new Trip();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip serviceTrip = updateTripRequest.getTrip();
		
		trip.setId(serviceTrip.getId());
		// get the dates and convert to date/time
		DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		try
		{
			trip.setEndDate(format.parse(serviceTrip.getEndDate()));
			trip.setStartDate(format.parse(serviceTrip.getStartDate()));
		}
		catch (ParseException pe)
		{
			// do nothing - we just don't set the dates
		}
		
		trip.setStartAddress(ServiceObjectMapper.convertToAddress(serviceTrip.getStartAddress()));
		trip.setEndAddress(ServiceObjectMapper.convertToAddress(serviceTrip.getEndAddress()));
		trip.setTitle(serviceTrip.getTitle());
		
		Organization organization = new Organization();
		organization.setId(serviceTrip.getOrganization().getId());
		trip.setOrganization(organization);
		
		carPoolTripService.createOrUpdateTrip(trip);
		
		// now, add the participants		
		TripUser tripUser = new TripUser();
		tripUser.setTripId(trip.getId());
		
		Participant[] participants = serviceTrip.getParticipants();
		if (participants != null)
		{
			for (int i = 0; i < participants.length; i++)
			{
				Participant participant = participants[i];
				edu.psu.sweng500.carpool.generated.beans.xsd.TripUser serviceUser = participant.getUser();
				tripUser.setUserId(serviceUser.getId());
				tripUser.setType(participant.getType());
			}
		}
		
		carPoolTripUserService.createOrUpdate(tripUser);
		
		UpdateTripResponse response = new UpdateTripResponse();
		response.setIsSuccess(true);
		return response;
	}

}
