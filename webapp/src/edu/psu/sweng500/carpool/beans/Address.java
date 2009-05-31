package edu.psu.sweng500.carpool.beans;

public class Address
{
	private String id;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String stateOrProvince;
	private String postalCode;
	private float latitude;
	private float longitude;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getAddress1()
	{
		return address1;
	}
	public void setAddress1(String address1)
	{
		this.address1 = address1;
	}
	public String getAddress2()
	{
		return address2;
	}
	public void setAddress2(String address2)
	{
		this.address2 = address2;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public String getStateOrProvince()
	{
		return stateOrProvince;
	}
	public void setStateOrProvince(String stateOrProvince)
	{
		this.stateOrProvince = stateOrProvince;
	}
	public String getPostalCode()
	{
		return postalCode;
	}
	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}
	public float getLatitude()
	{
		return latitude;
	}
	public void setLatitude(float latitude)
	{
		this.latitude = latitude;
	}
	public float getLongitude()
	{
		return longitude;
	}
	public void setLongitude(float longitude)
	{
		this.longitude = longitude;
	}
	
}
