package edu.psu.sweng500.carpool.services;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
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
import edu.psu.sweng500.carpool.generated.beans.xsd.SimpleMessage;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceException;
import edu.psu.sweng500.carpool.generated.services.CarPoolServiceSkeletonInterface;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateSavedAddressesRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse;
import edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest;
import edu.psu.sweng500.carpool.services.utilities.ServiceObjectMapper;
import edu.psu.sweng500.carpool.utilities.LocationUtils;

/* 
 * This program is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation; either version 2 of the License, or 
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

/**
 * Implementation of the web service skeleton interface
 * This is the main class that is invoked when a web service message
 * is received.
 * @author Eric Borisow
 */
public class AdbCarPoolService implements CarPoolServiceSkeletonInterface
{
	private static final Log log = LogFactory.getLog(AdbCarPoolService.class);
	
	private ICarPoolLocationService carPoolLocationService;
	private ICarPoolUserService carPoolUserService;
	private ICarPoolTripService carPoolTripService;
	private ICarPoolTripUserService carPoolTripUserService;
	
	/**
	 * Set the Car Pool Location Service
	 * This method is invoked by the Spring Class Loader
	 * @param carPoolLocationService
	 */
	public void setCarPoolLocationService(
			ICarPoolLocationService carPoolLocationService)
	{
		this.carPoolLocationService = carPoolLocationService;
	}
	
	/**
	 * Set the Car Pool User Service
	 * This method is invoked by the Spring Class Loader
	 * 
	 * @param carPoolUserService
	 */
	public void setCarPoolUserService(ICarPoolUserService carPoolUserService)
	{
		this.carPoolUserService = carPoolUserService;
	}
	
	/**
	 * Set the Car Pool Trip Service
	 * This method is invoked by the Spring Class Loader
	 * @param carPoolTripService
	 */
	public void setCarPoolTripService(ICarPoolTripService carPoolTripService)
	{
		this.carPoolTripService = carPoolTripService;
	}
	
	/**
	 * Set the Car Pool Trip User Service
	 * This method is invoked by the Spring Class Loader
	 * 
	 * @param carPoolTripUserService
	 */
	public void setCarPoolTripUserService(
			ICarPoolTripUserService carPoolTripUserService)
	{
		this.carPoolTripUserService = carPoolTripUserService;
	}
	
	/**
	 * Find places near a specified location
	 * This method finds locations that are within a certain number of miles
	 */
	public FindPlacesNearLocationResponse findPlacesNearLocation(
			FindPlacesNearLocationRequest findPlacesNearLocationRequest)
			throws CarPoolServiceException
	{
		if (log.isDebugEnabled())
		{
			log.debug("Starting findPlacesNearLocation");
		}
		FindCriteria criteria = new FindCriteria();
		
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
		
		List<Address> addresses = this.carPoolLocationService.findLocationsNearLocation(criteria);
		
		if (addresses == null || addresses.size() <= 0)
		{
			return response;
		}
		
		List<Address> foundAddresses = new ArrayList<Address>();
		// figure out which addresses are within our range
		Iterator<Address> addrIter = addresses.iterator();
		while (addrIter.hasNext())
		{
			Address address = addrIter.next();
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
			Address address = addrIter.next();
			Place place = ServiceObjectMapper.convertToPlace(address);
			
			if (log.isDebugEnabled())
			{
				log.debug("The address is: " + address.getAddress1());
			}
			places[count++] = place;
		}
		
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
		
		Set<UserAddress> savedAddresses = user.getSavedAddresses();
		if (savedAddresses != null && savedAddresses.size() > 0)
		{
			Place[] places = new Place[savedAddresses.size()];
			Iterator<UserAddress> savedAddrIter = savedAddresses.iterator();
			int count = 0;
			while (savedAddrIter.hasNext())
			{
				UserAddress userSavedAddress = savedAddrIter.next();
				if (log.isDebugEnabled())
				{
					log.debug("Done retrieving user address");
				}
				if (userSavedAddress != null)
				{
					Address savedAddress = userSavedAddress.getAddress();
					if (savedAddress != null)
					{
						Place place = ServiceObjectMapper.convertToPlace(savedAddress);
						
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
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(edu.psu.sweng500.carpool.beans.TripUser.class);
		detachedCriteria.setFetchMode("trip.startAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.endAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.organization", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.members", FetchMode.JOIN);
		criteria.setDetachedCriteria(detachedCriteria);
		
		List<TripUser> tripUsers = carPoolTripService.findTrips(criteria);
		if (tripUsers == null || tripUsers.size() == 0)
		{
			return response;
		}
		Iterator<TripUser> tripUsersIter = tripUsers.iterator();
		
		// TripList tripList = new TripList();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip[] serviceTrips = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip[tripUsers.size()];
		
		int count = 0;
		while (tripUsersIter.hasNext())
		{
			TripUser tripUser = tripUsersIter.next();
			if (tripUser != null)
			{
				Trip trip = tripUser.getTrip();
				edu.psu.sweng500.carpool.generated.beans.xsd.Trip serviceTrip = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip();
				if (trip != null)
				{
					if (log.isDebugEnabled())
					{
						log.debug("Trip id: " + trip.getId());
					}
					
					// map database Trip to service Trip
					serviceTrip.setId(trip.getId());
					serviceTrip.setTitle(trip.getTitle());
					serviceTrip.setOrganization(ServiceObjectMapper.convertToServiceOrganization(trip.getOrganization()));
					
					// use a class to map address to service address
					Place startPlace = ServiceObjectMapper.convertToPlace(trip.getStartAddress());
					Place endPlace = ServiceObjectMapper.convertToPlace(trip.getEndAddress());
					
					serviceTrip.setStartAddress(startPlace);
					serviceTrip.setEndAddress(endPlace);
					if (trip.getStartDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setStartDate(format.format(trip.getStartDate()));
					}
					else
					{
						serviceTrip.setStartDate("");
					}
					
					if (trip.getEndDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setEndDate(format.format(trip.getEndDate()));
					}
					else
					{
						serviceTrip.setEndDate("");
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
		
		return response;
	}
	
	public GetTripsByUserResponse findTrips(
			SearchTripsRequest findTripsRequest)
			throws CarPoolServiceException
	{
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		
		FindCriteria criteria = new FindCriteria();
		
		List<Criterion> criterionList = new ArrayList<Criterion>();
		Parameter[] parameters = findTripsRequest.getSearchParameters();
		if (parameters != null)
		{
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
						StringWriter sw = new StringWriter();
						PrintWriter pw = new PrintWriter(sw);
						pe.printStackTrace(pw);
						log.error(sw.toString());
						
						edu.psu.sweng500.carpool.generated.exceptions.xsd.CarPoolServiceException theError = new edu.psu.sweng500.carpool.generated.exceptions.xsd.CarPoolServiceException();
						theError.setErrorCode(8888);
						theError.setErrorDescription(pe.getMessage());
						theError.setMessage(pe.getMessage());
						theError.setTraceMessage(sw.toString());
						// CarPoolServiceException exception = new CarPoolServiceException();
						// throw theError;
					}
				}
				
			}
		}
		
		Criterion[] allCriterion = new Criterion[criterionList.size()];
		int criterionCount = 0;
		Iterator<Criterion> criterionIter = criterionList.iterator();
		while (criterionIter.hasNext())
		{
			allCriterion[criterionCount++] = (Criterion) criterionIter.next();
		}
		
		criteria.setSortField("trip.title");
		criteria.setAscending(true);
		
		criteria.setCriterion(allCriterion);
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(edu.psu.sweng500.carpool.beans.TripUser.class);
		detachedCriteria.setFetchMode("trip.startAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.endAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.organization", FetchMode.JOIN);
		detachedCriteria.setFetchMode("trip.members", FetchMode.JOIN);
		criteria.setDetachedCriteria(detachedCriteria);
		
		List<TripUser> tripUsers = carPoolTripService.findTrips(criteria);
		if (tripUsers == null || tripUsers.size() == 0)
		{
			return response;
		}
		Iterator<TripUser> tripUsersIter = tripUsers.iterator();
		
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
					if (log.isDebugEnabled())
					{
						log.debug("Trip id: " + trip.getId());
					}
					
					// map database Trip to service Trip
					serviceTrip.setId(trip.getId());
					serviceTrip.setTitle(trip.getTitle());
					serviceTrip.setOrganization(ServiceObjectMapper.convertToServiceOrganization(trip.getOrganization()));
					
					// use a class to map address to service address
					Place startPlace = ServiceObjectMapper.convertToPlace(trip.getStartAddress());
					Place endPlace = ServiceObjectMapper.convertToPlace(trip.getEndAddress());
					
					serviceTrip.setStartAddress(startPlace);
					serviceTrip.setEndAddress(endPlace);
					// Calendar startCalendar = Calendar.getInstance();
					if (trip.getStartDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setStartDate(format.format(trip.getStartDate()));
					}
					else
					{
						serviceTrip.setStartDate("");
					}
					
					// Calendar endCalendar = Calendar.getInstance();
					if (trip.getEndDate() != null)
					{
						DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
						serviceTrip.setEndDate(format.format(trip.getEndDate()));
					}
					else
					{
						serviceTrip.setEndDate("");
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
		
		return response;
	}
	
	public UpdateTripResponse updateTrip(UpdateTripRequest updateTripRequest)
			throws CarPoolServiceException
	{
		Trip trip = new Trip();
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip serviceTrip = updateTripRequest.getTrip();
		
		trip.setId(serviceTrip.getId());
		// get the dates and convert to date/time
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
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
		
		boolean isSuccess = carPoolTripUserService.createOrUpdate(tripUser);
		
		UpdateTripResponse response = new UpdateTripResponse();
		response.setIsSuccess(false);
		if (isSuccess)
		{
			response.setIsSuccess(true);
		}
		return response;
	}
	
	public GetTripsByUserResponse findSimilarTrips(
			FindSimilarTripsRequest findSimilarTripsRequest)
			throws CarPoolServiceException
	{
		GetTripsByUserResponse response = new GetTripsByUserResponse();
		float latitude = 0;
		if (findSimilarTripsRequest.getLatitude() != null && findSimilarTripsRequest.getLatitude().trim().length() != 0)
		{
			latitude = Float.parseFloat(findSimilarTripsRequest.getLatitude());
		}
		float longitude = 0;
		if (findSimilarTripsRequest.getLongitude() != null && findSimilarTripsRequest.getLongitude().trim().length() != 0)
		{
			longitude = Float.parseFloat(findSimilarTripsRequest.getLongitude());
		}
		log.debug("Incoming lat/lon: " + latitude + "/" + longitude);
		
		List<Criterion> criterionList = new ArrayList<Criterion>();
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
		Iterator<Criterion> criterionListIter = criterionList.iterator();
		int count = 0;
		while (criterionListIter.hasNext())
		{
			criterion[count++] = (Criterion) criterionListIter.next();
		}
		FindCriteria criteria = new FindCriteria();
		criteria.setCriterion(criterion);
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(edu.psu.sweng500.carpool.beans.Trip.class);
		detachedCriteria.setFetchMode("startAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("endAddress", FetchMode.JOIN);
		detachedCriteria.setFetchMode("organization", FetchMode.JOIN);
		detachedCriteria.setFetchMode("members", FetchMode.JOIN);
		detachedCriteria.setFetchMode("members.user", FetchMode.JOIN);
		criteria.setDetachedCriteria(detachedCriteria);
		
		List<Trip> trips = carPoolTripService.findTripsOnly(criteria);
		if (trips == null)
		{
			return response;
		}
		
		// if the user passes in a number for the number of miles in the range, then use that
		int numMiles = findSimilarTripsRequest.getNumMiles();
		if (numMiles > 0)
		{
			List<Trip> foundTrips = new ArrayList<Trip>();
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
		
		Iterator<Trip> tripUsersIter = trips.iterator();
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
		
		response.setTrip(serviceTrips);
		
		return response;
	}
	
	public GetTripResponse getTrip(GetTripRequest getTripRequest)
			throws CarPoolServiceException
	{
		String id = getTripRequest.getTripId();
		GetTripResponse response = new GetTripResponse(); 
		edu.psu.sweng500.carpool.generated.beans.xsd.Trip myTrip = new edu.psu.sweng500.carpool.generated.beans.xsd.Trip();
		
		Trip trip = carPoolTripService.getTrip(id);
		
		if (trip != null)
		{
			myTrip = ServiceObjectMapper.convertToTrip(trip);
		}
		
		myTrip.setId(id);
		response.setTrip(myTrip);
		return response;
	}
	
	public SimpleMessageResponse updateTripUser(
			UpdateTripUserRequest updateTripUserRequest)
			throws CarPoolServiceException
	{
		Participant participant = updateTripUserRequest.getParticipant();
		edu.psu.sweng500.carpool.generated.beans.xsd.TripUser serviceTripUser = participant.getUser();
		String tripId = updateTripUserRequest.getTripId();
		if (tripId == null || tripId.trim().length() == 0)
		{
			SimpleMessageResponse response = new SimpleMessageResponse();
			SimpleMessage message = new SimpleMessage();
			message.setIsSuccess(false);
			message.setErrorCode(0);
			message.setErrorDescription("");
			message.setMessage("");
			message.setTraceMessage("");
			response.setMessage(message);
			return response;
		}
		
		TripUser tripUser = new TripUser();
		User user = new User();
		user.setId(serviceTripUser.getId());
		tripUser.setTripId(tripId);
		tripUser.setType(participant.getType());
		tripUser.setUserId(serviceTripUser.getId());
		
		boolean isSuccess = carPoolTripUserService.createOrUpdate(tripUser);
		
		SimpleMessageResponse response = new SimpleMessageResponse();
		SimpleMessage message = new SimpleMessage();
		message.setIsSuccess(false);
		message.setErrorCode(0);
		message.setErrorDescription("");
		message.setMessage("");
		message.setTraceMessage("");
		if (isSuccess)
		{
			message.setIsSuccess(true);
		}
		
		response.setMessage(message);
		return response;
	}
	
	public SimpleMessageResponse updateSavedAddresses(
			UpdateSavedAddressesRequest updateSavedAddressesRequest)
			throws CarPoolServiceException
	{
		boolean isSuccess = false;
		String userId = updateSavedAddressesRequest.getUserId();
		if (userId != null)
		{
			User user = carPoolUserService.getUser(userId);
			if (user != null)
			{
				Set<UserAddress> savedAddresses = user.getSavedAddresses();
				Place[] places = updateSavedAddressesRequest.getSavedPlaces();
				if (places != null)
				{
					for (int i = 0; i < places.length; i++)
					{
						Place place = places[i];
						// check to see which type the user specified
						String type = place.getCategory();
						// check the set for this type
						Iterator<UserAddress> savedAddressIter = savedAddresses.iterator();
						boolean addressFound = false;
						List<UserAddress> removeAddressList = new ArrayList<UserAddress>();
						while (savedAddressIter.hasNext())
						{
							UserAddress savedUserAddress = savedAddressIter.next();
							if (savedUserAddress != null)
							{
								String savedAddressType = savedUserAddress.getType();
								if (savedAddressType.equalsIgnoreCase(type))
								{
									// then we want to delete the current setting
									// savedAddresses.remove(savedUserAddress);
									// carPoolUserService.deleteAddress(savedUserAddress.getAddress().getId());
									removeAddressList.add(savedUserAddress);
									addressFound = true;
								}
							}
						}
						if (addressFound)
						{
							// remove all of the addresses found
							savedAddresses.removeAll(removeAddressList);
							// iterate through the addresses to remove
							Iterator<UserAddress> removeAddressIter = removeAddressList.iterator();
							while (removeAddressIter.hasNext())
							{
								UserAddress savedUserAddress = (UserAddress) removeAddressIter.next();
								if (savedUserAddress != null)
								{
									// don't delete the address, just set it to inactive
									Address addressToUpdate = savedUserAddress.getAddress();
									addressToUpdate.setStatus("I");
									carPoolUserService.createOrUpdateAddress(addressToUpdate);
									// carPoolUserService.deleteAddress(savedUserAddress.getAddress().getId());
								}
							}
						}
						
						Address newAddress = ServiceObjectMapper.convertToAddress(place);
						newAddress.setStatus("A");
						carPoolUserService.createOrUpdateAddress(newAddress);
						
						UserAddress newUserAddress = new UserAddress();
						newUserAddress.setType(type);
						newUserAddress.setAddress(newAddress);
						
						savedAddresses.add(newUserAddress);
					}
					user.setSavedAddresses(savedAddresses);
					isSuccess = carPoolUserService.createOrUpdateUser(user);
				}
			}
		}
		
		SimpleMessageResponse response = new SimpleMessageResponse();
		SimpleMessage message = new SimpleMessage();
		message.setIsSuccess(false);
		message.setErrorCode(0);
		message.setErrorDescription("");
		message.setMessage("");
		message.setTraceMessage("");
		if (isSuccess)
		{
			message.setIsSuccess(true);
		}
		
		response.setMessage(message);
		return response;
	}
	
	public FindPlacesResponse findPlaces(FindPlacesRequest findPlacesRequest)
			throws CarPoolServiceException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
