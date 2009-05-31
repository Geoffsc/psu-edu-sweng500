package edu.psu.sweng500.carpool.data;

import org.hibernate.criterion.Criterion;

public class FindCriteria {
 
	private String sortField;
	 
	private boolean isAscending;
	 
	private int offset;
	 
	private int size;
	 
	private Criterion[] criterion;
	 
	private String searchTerm;

    public Criterion[] getCriterion()
    {
        return criterion;
    }

    public void setCriterion(Criterion[] criterion)
    {
        this.criterion = criterion;
    }

    public boolean isAscending()
    {
        return isAscending;
    }

    public void setAscending(boolean isAscending)
    {
        this.isAscending = isAscending;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public String getSearchTerm()
    {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm)
    {
        this.searchTerm = searchTerm;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public String getSortField()
    {
        return sortField;
    }

    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
	 
}