package edu.psu.sweng500.carpool.services;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.Address;
import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.beans.User;

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
 * Implementation of the CarPoolTripUserService that uses Hibernate
 * @author Eric Borisow
 *
 */
public class CarPoolTripUserService extends HibernateDaoSupport implements
		ICarPoolTripUserService
{

	public boolean createOrUpdate(TripUser tripUser)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(tripUser);
        
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
	
	@SuppressWarnings("unchecked")
	public List<TripUser> getTripUsersForTrip(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Criteria criteria=session.createCriteria(edu.psu.sweng500.carpool.beans.TripUser.class);
            criteria.setFetchMode("user", FetchMode.JOIN);
            criteria.setFetchMode("trip", FetchMode.JOIN);
            criteria.add(Restrictions.eq("tripId", id));

            tx.commit();
            return (List<TripUser>) criteria.list();
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
