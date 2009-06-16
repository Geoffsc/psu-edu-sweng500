package edu.psu.sweng500.carpool.device.services.generated;
import javax.xml.namespace.QName;

public interface CarPoolService extends java.rmi.Remote {

    /**
     *
     */
    public Trip[] findSimilarTrips(String id, String title, String startAddress, String endAddress, String startDate, String endDate, String latitude, String longitude, String organizationId, int numMiles) throws java.rmi.RemoteException;

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
    public Trip[] findTrips(Parameter[] searchParameters) throws java.rmi.RemoteException;

    /**
     *
     */
    public Place[] findPlaces(String userName, String type) throws java.rmi.RemoteException;

    /**
     *
     */
    public Trip[] getTripsByUser(String userId) throws java.rmi.RemoteException;

}
