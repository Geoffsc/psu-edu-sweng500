package edu.psu.sweng500.carpool.beans;

import java.util.Set;

public class User
{
	private String id;
	private String userName;
	private String password;
	private String givenName;
	private String surName;
	private String type;
	private Organization organization;
	private Set savedAddresses;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getUserName()
	{
		return userName;
	}
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public String getGivenName()
	{
		return givenName;
	}
	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}
	public String getSurName()
	{
		return surName;
	}
	public void setSurName(String surName)
	{
		this.surName = surName;
	}
	public Organization getOrganization()
	{
		return organization;
	}
	public void setOrganization(Organization organization)
	{
		this.organization = organization;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Set getSavedAddresses()
	{
		return savedAddresses;
	}
	public void setSavedAddresses(Set savedAddresses)
	{
		this.savedAddresses = savedAddresses;
	}
	
}
