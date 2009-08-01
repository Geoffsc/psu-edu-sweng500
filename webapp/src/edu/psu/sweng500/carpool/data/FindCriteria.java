package edu.psu.sweng500.carpool.data;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

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
 * Represnts a criteria object that is passed to database operations
 * @author Eric Borisow
 */
public class FindCriteria {
 
	private String sortField;
	 
	private boolean isAscending;
	 
	private int offset;
	 
	private int size;
	 
	private Criterion[] criterion;
	 
	private String searchTerm;
	
	private DetachedCriteria detachedCriteria;

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
    
    public DetachedCriteria getDetachedCriteria()
	{
		return detachedCriteria;
	}
    
    public void setDetachedCriteria(DetachedCriteria detachedCriteria)
	{
		this.detachedCriteria = detachedCriteria;
	}
	 
}