package edu.psu.sweng500.carpool.services;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.User;

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

}
