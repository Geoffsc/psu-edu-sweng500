package edu.psu.sweng500.carpool.device.services.generated;
import javax.xml.namespace.QName;

public interface SecurityService extends java.rmi.Remote {

    /**
     *
     */
    public SimpleMessage login(String userName, String password) throws java.rmi.RemoteException;

}
