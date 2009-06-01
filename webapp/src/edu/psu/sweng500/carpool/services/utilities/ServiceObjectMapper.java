package edu.psu.sweng500.carpool.services.utilities;

import edu.psu.sweng500.carpool.beans.Address;
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
}
