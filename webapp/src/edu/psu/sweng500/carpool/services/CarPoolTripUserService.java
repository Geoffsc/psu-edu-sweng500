package edu.psu.sweng500.carpool.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.TripUser;
import edu.psu.sweng500.carpool.data.FindCriteria;

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
	
	public boolean find(FindCriteria criteria)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
