package edu.psu.sweng500.carpool.device.services.generated;
import javax.xml.namespace.QName;

public interface CarPoolService extends java.rmi.Remote {

    /**
     *
     */
    public Boolean updateTrip(Trip trip) throws java.rmi.RemoteException;

    /**
     *
     */
    public Place[] findPlacesNearLocation(float latitude, float longitude, int numMiles) throws java.rmi.RemoteException;

    /**
     *
     */
    public User getUser(String userId) throws java.rmi.RemoteException;

    /**
     *
     */
    public Place[] findPlaces(String userName, String type) throws java.rmi.RemoteException;

    /**
     *
     */
    public Trip[] getTripsByUser(String userId) throws java.rmi.RemoteException;

}
