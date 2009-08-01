package edu.psu.sweng500.carpool.beans;

import java.util.Date;
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
 * Java Bean that represents a Carpool Trip
 * @author Eric Borisow
 */
public class Trip
{
	private String id;
	private String title;
	private Address startAddress;
	private Address endAddress;
	private Date startDate;
	private Date endDate;
	private Set<TripUser> members;
	private String userId;
	private Organization organization;
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public Address getStartAddress()
	{
		return startAddress;
	}
	public void setStartAddress(Address startAddress)
	{
		this.startAddress = startAddress;
	}
	public Address getEndAddress()
	{
		return endAddress;
	}
	public void setEndAddress(Address endAddress)
	{
		this.endAddress = endAddress;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public Set<TripUser> getMembers()
	{
		return members;
	}
	public void setMembers(Set<TripUser> members)
	{
		this.members = members;
	}
	public Organization getOrganization()
	{
		return organization;
	}
	public void setOrganization(Organization organization)
	{
		this.organization = organization;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	
}
