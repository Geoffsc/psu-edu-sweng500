package edu.psu.sweng500.carpool.services;

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
 * Implementation of the Carpool User Service using Hibernate
 * @author Eric Borisow
 *
 */
public class CarPoolUserService extends HibernateDaoSupport implements ICarPoolUserService
{

	public User getUser(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Criteria criteria=session.createCriteria(edu.psu.sweng500.carpool.beans.User.class);
            criteria.setFetchMode("savedAddresses", FetchMode.JOIN);
            criteria.setFetchMode("savedAddresses.address", FetchMode.JOIN);
            criteria.setFetchMode("organization", FetchMode.JOIN);
            criteria.add(Restrictions.eq("userName", id));

            tx.commit();
            return (User) criteria.uniqueResult(); 
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
	
	public User getUserById(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            
            Criteria criteria=session.createCriteria(edu.psu.sweng500.carpool.beans.User.class);
            criteria.setFetchMode("savedAddresses", FetchMode.JOIN);
            criteria.setFetchMode("savedAddresses.address", FetchMode.JOIN);
            criteria.setFetchMode("organization", FetchMode.JOIN);
            criteria.add(Restrictions.eq("id", id));

            tx.commit();
            return (User) criteria.uniqueResult(); 
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
	
	public boolean createOrUpdateUser(User user)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(user);
        
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
	
	public boolean createOrUpdateAddress(Address address)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(address);
        
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
	
	public boolean deleteAddress(String id)
	{
		Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Object address = session.load(Address.class, id);
            session.delete(address);
        
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

}
