package edu.psu.sweng500.carpool.beans;

import java.util.Date;
import java.util.List;

public class Trip
{
	private String id;
	private Address startAddress;
	private Address endAddress;
	private Date startDate;
	private Date endDate;
	private List users;
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
	public List getUsers()
	{
		return users;
	}
	public void setUsers(List users)
	{
		this.users = users;
	}
	
}
