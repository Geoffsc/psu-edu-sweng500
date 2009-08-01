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

import edu.psu.sweng500.carpool.beans.Trip;
import edu.psu.sweng500.carpool.beans.TripUser;
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
 * Implementation of a CarpoolTripService using Hibernate
 * @author Eric Borisow
 *
 */
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

	@SuppressWarnings("unchecked")
	public List<TripUser> findTrips(FindCriteria criteria)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            String sortField = criteria.getSortField();
            boolean isAscending = criteria.isAscending();
            
            Criteria localCriteria=session.createCriteria(edu.psu.sweng500.carpool.beans.TripUser.class);
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
            localCriteria.createAlias("user", "user", CriteriaSpecification.INNER_JOIN);
            localCriteria.createAlias("trip", "trip", CriteriaSpecification.INNER_JOIN);
            
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
            }
            
            /*
             * no default sort
            else
            {
                if (isAscending)
                {
                }
                else
                {
                }
            }
            */
            
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
            return (List<TripUser>) localCriteria.list(); 
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
	
	@SuppressWarnings("unchecked")
	public List<Trip> findTripsOnly(FindCriteria criteria)
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
            }
            
            /*
            	no default sort
            else
            {
                if (isAscending)
                {
                }
                else
                {
                }
            }
            */
            
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
            return (List<Trip>) localCriteria.list(); 
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
            criteria.setFetchMode("startAddress", FetchMode.JOIN);
            criteria.setFetchMode("endAddress", FetchMode.JOIN);
            criteria.setFetchMode("organization", FetchMode.JOIN);
            criteria.setFetchMode("members", FetchMode.JOIN);
            criteria.setFetchMode("members.user", FetchMode.JOIN);
            
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
