package edu.psu.sweng500.carpool.services.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.generated.beans.xsd.Organization;
import edu.psu.sweng500.carpool.generated.beans.xsd.Participant;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;
import edu.psu.sweng500.carpool.generated.beans.xsd.Trip;
import edu.psu.sweng500.carpool.generated.beans.xsd.TripUser;

public class ServiceObjectMapper
{
	public static Place convertToPlace(Address address)
	{
		Place place = new Place();
		if (address == null)
		{
			return place;
		}
		place.setAddress1(ServiceUtilities.getValue(address.getAddress1(), ""));
		place.setAddress2(ServiceUtilities.getValue(address.getAddress2(), ""));
		place.setId(ServiceUtilities.getValue(address.getId(), ""));
		place.setLatitude(address.getLatitude());
		place.setLongitude(address.getLongitude());
		place.setName(ServiceUtilities.getValue(address.getName(), ""));
		place.setDescription("");
		place.setPhoneNumber("");
		place.setCategory("");
		place.setUrl("");
		place.setZoom(0);
		
		return place;
	}
	
	public static Address convertToAddress(Place place)
	{
		Address address = new Address();
		if (place == null)
		{
			return address;
		}
		address.setAddress1(place.getAddress1());
		address.setAddress2(place.getAddress2());
		address.setId(place.getId());
		address.setLatitude(place.getLatitude());
		address.setLongitude(place.getLongitude());
		address.setName(place.getName());
		
		return address;
	}
	
	/*
	public static TripUser convertToUser(Participant participant)
	{
		TripUser user = new TripUser();
		if (participant == null)
		{
			return user;
		}
		edu.psu.sweng500.carpool.generated.beans.xsd.User serviceUser = participant.getUser();
		user.setUserId(serviceUser.getId());
	}
	*/
	
	public static Organization convertToServiceOrganization(edu.psu.sweng500.carpool.beans.Organization organization)
	{
		Organization serviceOrg = new Organization();
		serviceOrg.setId(organization.getId());
		serviceOrg.setName(organization.getName());
		return serviceOrg;
	}
	
	public static TripUser convertToTripUser(User user)
	{
		TripUser tripUser = new TripUser();
		tripUser.setId(user.getId());
		tripUser.setGivenName(user.getGivenName());
		tripUser.setSurName(user.getSurName());
		
		return tripUser;
	}
	
	public static Trip convertToTrip(edu.psu.sweng500.carpool.beans.Trip trip)
	{
		Trip serviceTrip = new Trip();
		serviceTrip.setId(trip.getId());
		serviceTrip.setTitle(trip.getTitle());
		
		DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		serviceTrip.setStartDate(format.format(trip.getStartDate()));
		serviceTrip.setEndDate(format.format(trip.getEndDate()));
		
		edu.psu.sweng500.carpool.beans.Organization organization = trip.getOrganization();
		serviceTrip.setOrganization(ServiceObjectMapper.convertToServiceOrganization(organization));
		
		serviceTrip.setEndAddress(ServiceObjectMapper.convertToPlace(trip.getEndAddress()));
		serviceTrip.setStartAddress(ServiceObjectMapper.convertToPlace(trip.getStartAddress()));
		
		return serviceTrip;
	}
	
}
