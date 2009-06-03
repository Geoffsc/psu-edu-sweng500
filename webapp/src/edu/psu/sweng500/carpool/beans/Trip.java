package edu.psu.sweng500.carpool.beans;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Trip
{
	private String id;
	private String title;
	private Address startAddress;
	private Address endAddress;
	private Date startDate;
	private Date endDate;
	private Set members;
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
	public Set getMembers()
	{
		return members;
	}
	public void setMembers(Set members)
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
