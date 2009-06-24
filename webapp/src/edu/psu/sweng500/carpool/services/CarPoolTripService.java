package edu.psu.sweng500.carpool.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.data.FindCriteria;

public class CarPoolTripService extends HibernateDaoSupport implements
		ICarPoolTripService
{
	private static final Log log = LogFactory.getLog(CarPoolTripService.class);

	public boolean createOrUpdateTrip(Trip trip)
	{
        Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(trip);
        
            tx.commit();
            return true;
        }
        catch (HibernateException ex) {
            // trap primary key violation since we don't really care
            DataAccessException ae = convertHibernateAccessException(ex);
            if (ae instanceof DataIntegrityViolationException)
            {
                // do nothing
            }
            else
            {
                throw convertHibernateAccessException(ex);
            }
        }
        finally
        {
            if (session != null)
            {
                this.releaseSession(session);
            }
        }
        return false;
	}

	public boolean deleteTrip(String id)
	{
		// TODO Auto-generated method stub
		return false;
	}

	public List findTrips(FindCriteria criteria)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            String sortField = criteria.getSortField();
            boolean isAscending = criteria.isAscending();
            
            Criteria localCriteria=session.createCriteria(edu.psu.sweng500.carpool.beans.TripUser.class);
            // localCriteria.setFetchMode("savedAddresses", FetchMode.JOIN);
            localCriteria.createAlias("user", "user", CriteriaSpecification.INNER_JOIN);
            localCriteria.createAlias("trip", "trip", CriteriaSpecification.INNER_JOIN);
            
            DetachedCriteria detachedCriteria = criteria.getDetachedCriteria();
            if (detachedCriteria != null)
            {
            	if (log.isDebugEnabled())
            	{
            		log.debug("Detached criteria is in the FindCriteria");
            	}
            	localCriteria = detachedCriteria.getExecutableCriteria(session);
            }
            else
            {
            	if (log.isDebugEnabled())
            	{
            		log.debug("There is no detached criteria set");
            	}
            }
            
            // localCriteria.setFetchMode("trip.startAddress", FetchMode.JOIN);
            // localCriteria.setFetchMode("trip.endAddress", FetchMode.JOIN);
            // localCriteria.setFetchMode("trip.organization", FetchMode.JOIN);
            // localCriteria.setFetchMode("trip.members", FetchMode.JOIN);

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
	
	public List findTripsOnly(FindCriteria criteria)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            String sortField = criteria.getSortField();
            boolean isAscending = criteria.isAscending();
            
            Criteria localCriteria=session.createCriteria(edu.psu.sweng500.carpool.beans.Trip.class);
            // localCriteria.setFetchMode("savedAddresses", FetchMode.JOIN);
            
            DetachedCriteria detachedCriteria = criteria.getDetachedCriteria();
            if (detachedCriteria != null)
            {
            	if (log.isDebugEnabled())
            	{
            		log.debug("Detached criteria is in the FindCriteria");
            	}
            	localCriteria = detachedCriteria.getExecutableCriteria(session);
            }
            else
            {
            	if (log.isDebugEnabled())
            	{
            		log.debug("There is no detached criteria set");
            	}
            }
            
            /*
            localCriteria.setFetchMode("startAddress", FetchMode.JOIN);
            localCriteria.setFetchMode("endAddress", FetchMode.JOIN);
            localCriteria.setFetchMode("organization", FetchMode.JOIN);
            localCriteria.setFetchMode("members", FetchMode.JOIN);
            */

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

	public Trip getTrip(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Criteria criteria=session.createCriteria(edu.psu.sweng500.carpool.beans.Trip.class);
            criteria.add(Restrictions.eq("id", id));
            
            tx.commit();
            return (Trip) criteria.uniqueResult(); 
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
