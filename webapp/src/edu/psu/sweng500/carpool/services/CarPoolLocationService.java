package edu.psu.sweng500.carpool.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.data.FindCriteria;

public class CarPoolLocationService extends HibernateDaoSupport implements ICarPoolLocationService
{

	public List findLocationsNearLocation(FindCriteria criteria)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            String sortField = criteria.getSortField();
            boolean isAscending = criteria.isAscending();
            
            Criteria localCriteria=session.createCriteria(edu.psu.sweng500.carpool.beans.Address.class);
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
            return localCriteria.list(); 
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

	public List getLocationsByUser(FindCriteria criteria)
	{
		return null;
	}

}
