package edu.psu.sweng500.carpool.beans;

import java.util.Set;

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
 * Java bean that represents a User
 * @author Eric Borisow
 */
public class User
{
	private String id;
	private String userName;
	private String password;
	private String givenName;
	private String surName;
	private String type;
	private Organization organization;
	private Set<UserAddress> savedAddresses;
	
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
	public Set<UserAddress> getSavedAddresses()
	{
		return savedAddresses;
	}
	public void setSavedAddresses(Set<UserAddress> savedAddresses)
	{
		this.savedAddresses = savedAddresses;
	}
	
}
