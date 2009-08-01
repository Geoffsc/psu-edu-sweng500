package edu.psu.sweng500.carpool.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.data.FindCriteria;

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
 * Implementation of the CarpoolLocationService using Hibernate
 * @author Eric Borisow
 *
 */
public class CarPoolLocationService extends HibernateDaoSupport implements ICarPoolLocationService
{

	@SuppressWarnings("unchecked")
	public List<Address> findLocationsNearLocation(FindCriteria criteria)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            String sortField = criteria.getSortField();
            boolean isAscending = criteria.isAscending();
            
            Criteria localCriteria=session.createCriteria(edu.psu.sweng500.carpool.beans.Address.class);
            localCriteria.add(Restrictions.eq("status", "A"));
            // localCriteria.setFetchMode("savedAddresses", FetchMode.JOIN);

            if (sortField != null && !sortField.equals(""))
            {
                if (isAscending)
                {
                	localCriteria.addOrder(Order.asc(sortField).ignoreCase());
                }
                else
                {
                	localCriteria.addOrder(Order.desc(sortField).ignoreCase());
                }
                // localCriteria.addOrder(Order.asc("stateOrProvince").ignoreCase());
                // localCriteria.addOrder(Order.asc("city").ignoreCase());
            }
            
            else
            {
                if (isAscending)
                {
                	// localCriteria.addOrder(Order.asc("stateOrProvince").ignoreCase());
                    // localCriteria.addOrder(Order.asc("city").ignoreCase());
                }
                else
                {
                	// localCriteria.addOrder(Order.desc("stateOrProvince").ignoreCase());
                    // localCriteria.addOrder(Order.asc("city").ignoreCase());
                }
            }
            
            int offset = criteria.getOffset();
            if (offset >= 0)
            {
            	localCriteria.setFirstResult(offset);
            }
            int size = criteria.getSize();
            if (size > 0)
            {
            	localCriteria.setMaxResults(size);
            }
            
            Criterion[] criterionArray = criteria.getCriterion();

            if (criterionArray != null)
            {
                for (int i = 0; i < criterionArray.length; i++)
                {
                    Criterion thisCriterion = (Criterion) criterionArray[i];

                    if (thisCriterion != null)
                    {
                    	localCriteria.add(thisCriterion);
                    }
                }
            }
        
            tx.commit();
            return (List<Address>) localCriteria.list(); 
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
        finally
        {
            if (session != null)
            {
                this.releaseSession(session);
            }
        }
	}

	public List<Address> getLocationsByUser(FindCriteria criteria)
	{
		return null;
	}
	
	public Address getAddress(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Criteria criteria=session.createCriteria(edu.psu.sweng500.carpool.beans.Address.class);
            criteria.add(Restrictions.eq("id", id));
            
            tx.commit();
            return (Address) criteria.uniqueResult(); 
        }
        catch (HibernateException ex) {
            throw convertHibernateAccessException(ex);
        }
        finally
        {
            if (session != null)
            {
                this.releaseSession(session);
            }
        }
	}

}
