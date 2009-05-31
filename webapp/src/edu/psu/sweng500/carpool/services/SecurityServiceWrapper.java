package edu.psu.sweng500.carpool.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import edu.psu.sweng500.carpool.beans.User;
import edu.psu.sweng500.carpool.exceptions.SecurityException;

public class SecurityServiceWrapper extends HibernateDaoSupport implements ISecurityServiceWrapper
{
	private static final Log log = LogFactory.getLog(SecurityServiceWrapper.class);

	/*
	public boolean login(String userName, String password)
			throws SecurityException
	{
		System.out.println("User name: " + userName + "/password: " + password);
		return false;
	}
	*/
	
	public boolean login(String userName, String password) throws SecurityException
    {
		if (log.isDebugEnabled())
		{
			log.debug("The incoming user name is: " + userName);
			log.debug("The incoming password is: " + password);
		}
        Session session = getSession(true);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "from edu.psu.sweng500.carpool.beans.User where userName = :userName AND password = :password";
            
            Query query = session.createQuery(hql);
            query.setParameter("userName", userName);
            query.setParameter("password", password);
            
            User user = (User)
            query.uniqueResult();
            // session.createQuery(hql).uniqueResult();
            
            tx.commit();
            
            if (user != null)
            {
            	return true;
            }
            
            throw new SecurityException(SecurityException.USER_NOT_FOUND, "The specified user could not be found");
            
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
