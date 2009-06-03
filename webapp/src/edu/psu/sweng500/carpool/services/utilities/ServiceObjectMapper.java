package edu.psu.sweng500.carpool.services.utilities;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.generated.beans.xsd.Participant;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;

public class ServiceObjectMapper
{
	public static Place convertToPlace(Address address)
	{
		Place place = new Place();
		if (address == null)
		{
			return place;
		}
		place.setAddress1(address.getAddress1());
		place.setAddress2(address.getAddress2());
		place.setId(address.getId());
		place.setLatitude(address.getLatitude());
		place.setLongitude(address.getLongitude());
		place.setName(address.getName());
		
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
}
