package edu.psu.sweng500.carpool.services;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.Organization;
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.beans.UserAddress;
import edu.psu.sweng500.carpool.data.FindCriteria;
import edu.psu.sweng500.carpool.generated.beans.xsd.Parameter;
import edu.psu.sweng500.carpool.generated.beans.xsd.Participant;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest;
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
		
		/*
		Criterion latLECriterion = Restrictions.le("latitude", 180f);
		Criterion latGECriterion = Restrictions.ge("latitude", -180f);
		Criterion longLECriterion = Restrictions.le("longitude", 180f);
		Criterion longGECriterion = Restrictions.ge("longitude", -180f);
		
		allCriterion[0] = latLECriterion;
		allCriterion[1] = latGECriterion;
		allCriterion[2] = longLECriterion;
		allCriterion[3] = longGECriterion;
		*/
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
		if (foundAddresses.size() <= 0)
		{
			return response;
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
		GetUserResponse response = new GetUserResponse();
		
		String id = getUserRequest.getUserId();
		if (id == null || id.trim().length() == 0)
		{
			return response;
		}
		
		User user = this.carPoolUserService.getUser(id);
		
		edu.psu.sweng500.carpool.generated.beans.xsd.User serviceUser = new edu.psu.sweng500.carpool.generated.beans.xsd.User();
		serviceUser.setId(user.getId());
		serviceUser.setGivenName(user.getGivenName());
		serviceUser.setSurName(user.getSurName());
		serviceUser.setType("");
		Organization organization = user.getOrganization();
		if (organization != null)
		{
			edu.psu.sweng500.carpool.generated.beans.xsd.Organization serviceOrg = new edu.psu.sweng500.carpool.generated.beans.xsd.Organization();
			serviceOrg.setId(organization.getId());
			serviceOrg.setName(organization.getName());
			serviceUser.setOrganization(serviceOrg);
		}
		
		Set savedAddresses = user.getSavedAddresses();
		if (savedAddresses != null && savedAddresses.size() > 0)
		{
			Place[] places = new Place[savedAddresses.size()];
			Iterator savedAddrIter = savedAddresses.iterator();
			int count = 0;
			while (savedAddrIter.hasNext())
			{
				Object userSavedAddressObj = savedAddrIter.next();
				if (log.isDebugEnabled())
				{
					log.debug("About to retrieve user address: " + userSavedAddressObj.getClass().getName());
				}
				UserAddress userSavedAddress = (UserAddress) userSavedAddressObj;
				if (log.isDebugEnabled())
				{
					log.debug("Done retrieving user address");
				}
				if (userSavedAddress != null)
				{
					Address savedAddress = userSavedAddress.getAddress();
					if (savedAddress != null)
					{
						Place place = new Place();
						place.setId(savedAddress.getId());
						place.setAddress1(savedAddress.getAddress1());
						place.setAddress2(savedAddress.getAddress2());
						place.setLatitude(savedAddress.getLatitude());
						place.setLongitude(savedAddress.getLongitude());
						place.setDescription("");
						place.setName(savedAddress.getName());
						place.setPhoneNumber("");
						place.setUrl("");
						place.setZoom(0);
						
						place.setCategory(userSavedAddress.getType());
						
						places[count++] = place;
					}
				}
			}
			serviceUser.setSavedPlaces(places);
		}
		
		if (log.isDebugEnabled())
		{
			log.debug("About to send response back");
		}
		response.setUser(serviceUser);
		return response;
	}
	
	public GetTripsByUserResponse getTripsByUser(
			GetTripsByUserRequest getTripsByUserRequest)
			throws CarPoolServiceException
	{
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		
		String id = getTripsByUserRequest.getUserId();
		if (id == null || id.trim().length() == 0)
		{
			return response;
		}
		
		FindCriteria criteria = new FindCriteria();
		
		Criterion[] allCriterion = new Criterion[1];
		Criterion userCriterion = Restrictions.eq("user.userName", id);
		
		criteria.setSortField("trip.title");
		criteria.setAscending(true);
		
		allCriterion[0] = userCriterion;
		criteria.setCriterion(allCriterion);
		
		List tripUsers = carPoolTripService.findTrips(criteria);
		if (tripUsers == null || tripUsers.size() == 0)
		{
			return response;
		}
		Iterator tripUsersIter = tripUsers.iterator();
		
		// TripList tripList = new TripList();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip[] serviceTrips = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip[tripUsers.size()];
		
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
	
	public GetTripsByUserResponse findTrips(
			SearchTripsRequest findTripsRequest)
			throws CarPoolServiceException
	{
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		
		FindCriteria criteria = new FindCriteria();
		
		// SearchCriteria searchCriteria = findTripsRequest.getCriteria();
		Parameter[] parameters = findTripsRequest.getSearchParameters();
		
		List criterionList = new ArrayList();
		for (int i = 0; i < parameters.length; i++)
		{
			Parameter param = parameters[i];
			String name = param.getName();
			String value = param.getValue();
			
			if (name.equalsIgnoreCase("username"))
			{
				Criterion userCriterion = Restrictions.eq("user.userName", value);
				criterionList.add(userCriterion);
			}
			
			if (name.equalsIgnoreCase("startDate"))
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try
				{
					Date theDateBegin = dateFormat.parse(value + " 00:00:00");
					Date theDateEnd = dateFormat.parse(value + " 23:59:59");
					Criterion startDateCriterion = Restrictions.ge("trip.startDate", theDateBegin);
					Criterion endDateCriterion = Restrictions.le("trip.startDate", theDateEnd);
					criterionList.add(startDateCriterion);
					criterionList.add(endDateCriterion);
				}
				catch (ParseException pe)
				{
					// TODO: Throw an error
					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					pe.printStackTrace(pw);
					log.error(sw.toString());
				}
			}
			
		}
		
		Criterion[] allCriterion = new Criterion[criterionList.size()];
		int criterionCount = 0;
		Iterator criterionIter = criterionList.iterator();
		while (criterionIter.hasNext())
		{
			allCriterion[criterionCount++] = (Criterion) criterionIter.next();
		}
		
		criteria.setSortField("trip.title");
		criteria.setAscending(true);
		
		criteria.setCriterion(allCriterion);
		
		List tripUsers = carPoolTripService.findTrips(criteria);
		if (tripUsers == null || tripUsers.size() == 0)
		{
			return response;
		}
		Iterator tripUsersIter = tripUsers.iterator();
		
		// TripList tripList = new TripList();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip[] serviceTrips = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip[tripUsers.size()];
		
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
	
	public GetTripsByUserResponse findSimilarTrips(
			FindSimilarTripsRequest findSimilarTripsRequest)
			throws CarPoolServiceException
	{
		// BasicTrip trip = findSimilarTripsRequest.getTrip();
		
		float latitude = Float.parseFloat(findSimilarTripsRequest.getLatitude());
		float longitude = Float.parseFloat(findSimilarTripsRequest.getLongitude());
		log.debug("Incoming lat/lon: " + latitude + "/" + longitude);
		
		List criterionList = new ArrayList();
		if (findSimilarTripsRequest.getStartAddress() != null)
		{
			Criterion criterion = Restrictions.eq("startAddress.id", findSimilarTripsRequest.getStartAddress());
			criterionList.add(criterion);
		}
		
		if (findSimilarTripsRequest.getEndAddress() != null)
		{
			Criterion criterion = Restrictions.eq("endAddress.id", findSimilarTripsRequest.getEndAddress());
			criterionList.add(criterion);
		}
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (findSimilarTripsRequest.getStartDate() != null)
		{
			try
			{
				Date theDateBegin = format.parse(findSimilarTripsRequest.getStartDate() + " 00:00:00");
				Date theDateEnd = format.parse(findSimilarTripsRequest.getStartDate() + " 23:59:59");
				Criterion startDateCriterion = Restrictions.ge("startDate", theDateBegin);
				Criterion endDateCriterion = Restrictions.le("startDate", theDateEnd);
				
				criterionList.add(startDateCriterion);
				criterionList.add(endDateCriterion);
			}
			catch (ParseException pe)
			{
				pe.printStackTrace();
			}
		}
		
		if (findSimilarTripsRequest.getEndDate() != null)
		{
			try
			{
				Date theDateBegin = format.parse(findSimilarTripsRequest.getEndDate() + " 00:00:00");
				Date theDateEnd = format.parse(findSimilarTripsRequest.getEndDate() + " 23:59:59");
				Criterion startDateCriterion = Restrictions.ge("endDate", theDateBegin);
				Criterion endDateCriterion = Restrictions.le("endDate", theDateEnd);
				
				criterionList.add(startDateCriterion);
				criterionList.add(endDateCriterion);
			}
			catch (ParseException pe)
			{
				pe.printStackTrace();
			}
		}
		
		if (findSimilarTripsRequest.getOrganizationId() != null)
		{
			Criterion criterion = Restrictions.eq("organization.id", findSimilarTripsRequest.getOrganizationId());
			criterionList.add(criterion);
		}
		
		Criterion[] criterion = new Criterion[criterionList.size()];
		Iterator criterionListIter = criterionList.iterator();
		int count = 0;
		while (criterionListIter.hasNext())
		{
			criterion[count++] = (Criterion) criterionListIter.next();
		}
		FindCriteria criteria = new FindCriteria();
		criteria.setCriterion(criterion);
		
		// CarPoolTripService carPoolTripService = new CarPoolTripService();
		// SessionFactory sessionFactory = new Configuration().configure("edu/psu/sweng500/carpool/tests/hibernate.cfg.xml").buildSessionFactory();
		// carPoolTripService.setSessionFactory(sessionFactory);
		List trips = carPoolTripService.findTripsOnly(criteria);
		
		// if the user passes in a number for the number of miles in the range, then use that
		int numMiles = findSimilarTripsRequest.getNumMiles();
		if (numMiles > 0)
		{
			List foundTrips = new ArrayList();
			// figure out which addresses are within our range
			for (int i = 0; i < trips.size(); i++)
			{
				Trip trip = (Trip) trips.get(i);
				if (trip != null)
				{
					Address startAddress = trip.getStartAddress();
					Address endAddress = trip.getEndAddress();
					boolean keepTrip = false;
					
					log.debug("Start address: " + startAddress.getLatitude() + "/" + startAddress.getLongitude());
					
					double distance = LocationUtils.getDistance(latitude, longitude, startAddress.getLatitude(), startAddress.getLongitude(), 'M');
					log.debug("Distance to start address is: " + distance);
					if (distance <= findSimilarTripsRequest.getNumMiles())
					{
						log.debug("Start address is within distance for trip #" + trip.getId());
						keepTrip = true;
					}
					
					distance = LocationUtils.getDistance(latitude, longitude, endAddress.getLatitude(), endAddress.getLongitude(), 'M');
					log.debug("Distance to end address is: " + distance);
					if (distance <= findSimilarTripsRequest.getNumMiles())
					{
						log.debug("End address is within distance for trip #" + trip.getId());
						keepTrip = true;
					}
					
					if (keepTrip)
					{
						log.debug("Keeping trip #" + trip.getId());
						// trips.remove(i);
						foundTrips.add(trip);
					}
				}
			}
			trips.clear();
			trips = foundTrips;
		}
		
		
		Iterator tripUsersIter = trips.iterator();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip[] serviceTrips = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip[trips.size()];
		int serviceTripCount = 0;
		while (tripUsersIter.hasNext())
		{
			Trip resultTrip = (Trip) tripUsersIter.next();
			if (resultTrip != null)
			{
				serviceTrips[serviceTripCount++] = ServiceObjectMapper.convertToTrip(resultTrip);
			}
		}
		
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		response.setTrip(serviceTrips);
		
		return response;
	}

}
