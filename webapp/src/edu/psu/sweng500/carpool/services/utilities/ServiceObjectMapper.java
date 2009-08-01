package edu.psu.sweng500.carpool.services.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.generated.beans.xsd.Organization;
import edu.psu.sweng500.carpool.generated.beans.xsd.Participant;
import edu.psu.sweng500.carpool.generated.beans.xsd.Place;
import edu.psu.sweng500.carpool.generated.beans.xsd.Trip;
import edu.psu.sweng500.carpool.generated.beans.xsd.TripUser;

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
 * This class is used to convert Service objects to internal objects
 * @author Eric Borisow
 *
 */
public class ServiceObjectMapper
{
	/**
	 * Convert a Service object to a Place
	 * @param address The address object to convert
	 * @return
	 */
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
	
	/**
	 * Convert a Place to an Address
	 * @param place The place to convert
	 * @return
	 */
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
	
	/**
	 * Convert a Service Organization to an internal Organization
	 * @param organization The service organization to convert
	 * @return
	 */
	public static Organization convertToServiceOrganization(edu.psu.sweng500.carpool.beans.Organization organization)
	{
		Organization serviceOrg = new Organization();
		if (organization == null)
		{
			return serviceOrg;
		}
		serviceOrg.setId(organization.getId());
		serviceOrg.setName(organization.getName());
		return serviceOrg;
	}
	
	/**
	 * Convert a User object to a TripUser
	 * @param user The user object to convert
	 * @return
	 */
	public static TripUser convertToTripUser(User user)
	{
		TripUser tripUser = new TripUser();
		if (user == null)
		{
			return tripUser;
		}
		tripUser.setId(user.getId());
		tripUser.setGivenName(user.getGivenName());
		tripUser.setSurName(user.getSurName());
		
		return tripUser;
	}
	
	/**
	 * Convert a Service trip to a Trip
	 * @param trip The service trip object
	 * @return
	 */
	public static Trip convertToTrip(edu.psu.sweng500.carpool.beans.Trip trip)
	{
		Trip serviceTrip = new Trip();
		if (trip == null)
		{
			return serviceTrip;
		}
		
		serviceTrip.setId(trip.getId());
		serviceTrip.setTitle(trip.getTitle());
		
		DateFormat format = SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG);
		if (trip.getStartDate() != null)
		{
			serviceTrip.setStartDate(format.format(trip.getStartDate()));
		}
		else
		{
			serviceTrip.setStartDate("");
		}
		if (trip.getEndDate() != null)
		{
			serviceTrip.setEndDate(format.format(trip.getEndDate()));
		}
		else
		{
			serviceTrip.setEndDate("");
		}
		
		edu.psu.sweng500.carpool.beans.Organization organization = trip.getOrganization();
		if (organization != null)
		{
			serviceTrip.setOrganization(ServiceObjectMapper.convertToServiceOrganization(organization));
		}
		
		serviceTrip.setEndAddress(ServiceObjectMapper.convertToPlace(trip.getEndAddress()));
		serviceTrip.setStartAddress(ServiceObjectMapper.convertToPlace(trip.getStartAddress()));
		
		Set<edu.psu.sweng500.carpool.beans.TripUser> members = trip.getMembers();
		if (members != null)
		{
			Participant[] participants = new Participant[members.size()];
			Iterator<edu.psu.sweng500.carpool.beans.TripUser> membersIter = members.iterator();
			int count = 0;
			while (membersIter.hasNext())
			{
				edu.psu.sweng500.carpool.beans.TripUser tripUser = (edu.psu.sweng500.carpool.beans.TripUser) membersIter.next();
				String memberType = tripUser.getType();
				TripUser serviceTripUser = ServiceObjectMapper.convertToTripUser(tripUser.getUser());
				
				Participant participant = new Participant();
				participant.setType(memberType);
				participant.setUser(serviceTripUser);
				
				participants[count++] = participant;
			}
			serviceTrip.setParticipants(participants);
		}
		
		return serviceTrip;
	}
	
}
