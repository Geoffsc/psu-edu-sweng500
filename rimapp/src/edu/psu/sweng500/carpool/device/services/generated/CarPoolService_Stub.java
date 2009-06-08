package edu.psu.sweng500.carpool.device.services.generated;

import javax.xml.rpc.JAXRPCException;
import javax.xml.namespace.QName;
import javax.microedition.xml.rpc.Operation;
import javax.microedition.xml.rpc.Type;
import javax.microedition.xml.rpc.ComplexType;
import javax.microedition.xml.rpc.Element;

public class CarPoolService_Stub implements CarPoolService, javax.xml.rpc.Stub {

    private String[] _propertyNames;
    private Object[] _propertyValues;

    public CarPoolService_Stub() {
        _propertyNames = new String[] { ENDPOINT_ADDRESS_PROPERTY };
        _propertyValues = new Object[] { "http://162.44.84.143:6187/sweng500_car_pool/services/CarPoolService/" };
    }

    public void _setProperty( String name, Object value ) {
        int size = _propertyNames.length;
        for (int i = 0; i < size; ++i) {
            if( _propertyNames[i].equals( name )) {
                _propertyValues[i] = value;
                return;
            }
        }
        String[] newPropNames = new String[size + 1];
        System.arraycopy(_propertyNames, 0, newPropNames, 0, size);
        _propertyNames = newPropNames;
        Object[] newPropValues = new Object[size + 1];
        System.arraycopy(_propertyValues, 0, newPropValues, 0, size);
        _propertyValues = newPropValues;

        _propertyNames[size] = name;
        _propertyValues[size] = value;
    }

    public Object _getProperty(String name) {
        for (int i = 0; i < _propertyNames.length; ++i) {
            if (_propertyNames[i].equals(name)) {
                return _propertyValues[i];
            }
        }
        if (ENDPOINT_ADDRESS_PROPERTY.equals(name) || USERNAME_PROPERTY.equals(name) || PASSWORD_PROPERTY.equals(name)) {
            return null;
        }
        if (SESSION_MAINTAIN_PROPERTY.equals(name)) {
            return new Boolean(false);
        }
        throw new JAXRPCException("Stub does not recognize property: " + name);
    }

    protected void _prepOperation(Operation op) {
        for (int i = 0; i < _propertyNames.length; ++i) {
            op.setProperty(_propertyNames[i], _propertyValues[i].toString());
        }
    }

    public Boolean updateTrip(Trip trip) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            Trip_toObject( trip )
        };

        Operation op = Operation.newInstance( _qname_operation_updateTrip, _type_UpdateTripRequest, _type_UpdateTripResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:updateTrip" );
        Object resultObj;
        try {
            resultObj = op.invoke( inputObject );
        } catch( JAXRPCException e ) {
            Throwable cause = e.getLinkedCause();
            if( cause instanceof java.rmi.RemoteException ) {
                throw (java.rmi.RemoteException) cause;
            }
            throw e;
        }

        return (Boolean )((Object[])resultObj)[0];
    }

    public Place[] findPlacesNearLocation(float latitude, float longitude, int numMiles) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            new Float(latitude),
            new Float(longitude),
            new Integer(numMiles)
        };

        Operation op = Operation.newInstance( _qname_operation_findPlacesNearLocation, _type_FindPlacesNearLocationRequest, _type_FindPlacesNearLocationResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:findPlacesNearLocation" );
        Object resultObj;
        try {
            resultObj = op.invoke( inputObject );
        } catch( JAXRPCException e ) {
            Throwable cause = e.getLinkedCause();
            if( cause instanceof java.rmi.RemoteException ) {
                throw (java.rmi.RemoteException) cause;
            }
            throw e;
        }

        return Place_ArrayfromObject((Object[])((Object[]) resultObj)[0]);
    }

    public User getUser(String userId) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            userId
        };

        Operation op = Operation.newInstance( _qname_operation_getUser, _type_GetUserRequest, _type_GetUserResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:getUser" );
        Object resultObj;
        try {
            resultObj = op.invoke( inputObject );
        } catch( JAXRPCException e ) {
            Throwable cause = e.getLinkedCause();
            if( cause instanceof java.rmi.RemoteException ) {
                throw (java.rmi.RemoteException) cause;
            }
            throw e;
        }

        return User_fromObject((Object[])((Object[]) resultObj)[0]);
    }

    public Place[] findPlaces(String userName, String type) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            userName,
            type
        };

        Operation op = Operation.newInstance( _qname_operation_findPlaces, _type_FindPlacesRequest, _type_FindPlacesResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:findPlaces" );
        Object resultObj;
        try {
            resultObj = op.invoke( inputObject );
        } catch( JAXRPCException e ) {
            Throwable cause = e.getLinkedCause();
            if( cause instanceof java.rmi.RemoteException ) {
                throw (java.rmi.RemoteException) cause;
            }
            throw e;
        }

        return Place_ArrayfromObject((Object[])((Object[]) resultObj)[0]);
    }

    public Trip[] getTripsByUser(String userId) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            userId
        };

        Operation op = Operation.newInstance( _qname_operation_getTripsByUser, _type_GetTripsByUserRequest, _type_GetTripsByUserResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:getTripsByUser" );
        Object resultObj;
        try {
            resultObj = op.invoke( inputObject );
        } catch( JAXRPCException e ) {
            Throwable cause = e.getLinkedCause();
            if( cause instanceof java.rmi.RemoteException ) {
                throw (java.rmi.RemoteException) cause;
            }
            throw e;
        }

        return Trip_ArrayfromObject((Object[])((Object[]) resultObj)[0]);
    }

    private static Object Participant_ArraytoObject( Participant[] obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ obj.length ];
        for( int i = 0; i < obj.length; i++ ) {
            Object[] resultArray = new Object[ 2 ];
            resultArray[0] = TripUser_toObject( obj[i].getUser());
            resultArray[1] = obj[i].getType();
            result[i] = resultArray;
        }
        return result;
    }

    private static Object Trip_toObject( Trip obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 8 ];
        result[0] = obj.getId();
        result[1] = obj.getTitle();
        result[2] = Place_toObject( obj.getStartAddress());
        result[3] = Place_toObject( obj.getEndAddress());
        result[4] = obj.getStartDate();
        result[5] = obj.getEndDate();
        result[6] = Organization_toObject( obj.getOrganization());
        result[7] = Participant_ArraytoObject( obj.getParticipants());
        return result;
    }

    private static Object Place_toObject( Place obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 11 ];
        result[0] = obj.getId();
        result[1] = obj.getName();
        result[2] = obj.getAddress1();
        result[3] = obj.getAddress2();
        result[4] = obj.getDescription();
        result[5] = obj.getCategory();
        result[6] = obj.getLatitude();
        result[7] = obj.getLongitude();
        result[8] = obj.getPhoneNumber();
        result[9] = obj.getUrl();
        result[10] = obj.getZoom();
        return result;
    }

    private static Object Organization_toObject( Organization obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 2 ];
        result[0] = obj.getId();
        result[1] = obj.getName();
        return result;
    }

    private static Object TripUser_toObject( TripUser obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 3 ];
        result[0] = obj.getId();
        result[1] = obj.getGivenName();
        result[2] = obj.getSurName();
        return result;
    }

    private static Participant[] Participant_ArrayfromObject( Object obj[] ) {
        if(obj == null) return null;
        Participant result[] = new Participant[obj.length];
        for( int i = 0; i < obj.length; i++ ) {
            result[i] = new Participant();
            Object[] oo = (Object[]) obj[i];
            result[i].setUser(TripUser_fromObject((Object[]) oo[0] ));
            result[i].setType((String )oo[1]);
        }
        return result;
    }

    private static User User_fromObject( Object obj[] ) {
        if(obj == null) return null;
        User result = new User();
        result.setId((String )obj[0]);
        result.setGivenName((String )obj[1]);
        result.setSurName((String )obj[2]);
        result.setType((String )obj[3]);
        result.setOrganization(Organization_fromObject((Object[]) obj[4] ));
        result.setSavedPlaces(Place_ArrayfromObject((Object[]) obj[5] ));
        return result;
    }

    private static Trip[] Trip_ArrayfromObject( Object obj[] ) {
        if(obj == null) return null;
        Trip result[] = new Trip[obj.length];
        for( int i = 0; i < obj.length; i++ ) {
            result[i] = new Trip();
            Object[] oo = (Object[]) obj[i];
            result[i].setId((String )oo[0]);
            result[i].setTitle((String )oo[1]);
            result[i].setStartAddress(Place_fromObject((Object[]) oo[2] ));
            result[i].setEndAddress(Place_fromObject((Object[]) oo[3] ));
            result[i].setStartDate((String )oo[4]);
            result[i].setEndDate((String )oo[5]);
            result[i].setOrganization(Organization_fromObject((Object[]) oo[6] ));
            result[i].setParticipants(Participant_ArrayfromObject((Object[]) oo[7] ));
        }
        return result;
    }

    private static Place[] Place_ArrayfromObject( Object obj[] ) {
        if(obj == null) return null;
        Place result[] = new Place[obj.length];
        for( int i = 0; i < obj.length; i++ ) {
            result[i] = new Place();
            Object[] oo = (Object[]) obj[i];
            result[i].setId((String )oo[0]);
            result[i].setName((String )oo[1]);
            result[i].setAddress1((String )oo[2]);
            result[i].setAddress2((String )oo[3]);
            result[i].setDescription((String )oo[4]);
            result[i].setCategory((String )oo[5]);
            result[i].setLatitude((Float )oo[6]);
            result[i].setLongitude((Float )oo[7]);
            result[i].setPhoneNumber((String )oo[8]);
            result[i].setUrl((String )oo[9]);
            result[i].setZoom((Integer )oo[10]);
        }
        return result;
    }

    private static Place Place_fromObject( Object obj[] ) {
        if(obj == null) return null;
        Place result = new Place();
        result.setId((String )obj[0]);
        result.setName((String )obj[1]);
        result.setAddress1((String )obj[2]);
        result.setAddress2((String )obj[3]);
        result.setDescription((String )obj[4]);
        result.setCategory((String )obj[5]);
        result.setLatitude((Float )obj[6]);
        result.setLongitude((Float )obj[7]);
        result.setPhoneNumber((String )obj[8]);
        result.setUrl((String )obj[9]);
        result.setZoom((Integer )obj[10]);
        return result;
    }

    private static Organization Organization_fromObject( Object obj[] ) {
        if(obj == null) return null;
        Organization result = new Organization();
        result.setId((String )obj[0]);
        result.setName((String )obj[1]);
        return result;
    }

    private static TripUser TripUser_fromObject( Object obj[] ) {
        if(obj == null) return null;
        TripUser result = new TripUser();
        result.setId((String )obj[0]);
        result.setGivenName((String )obj[1]);
        result.setSurName((String )obj[2]);
        return result;
    }

    protected static final QName _qname_operation_getTripsByUser = new QName( "http://services.generated.carpool.sweng500.psu.edu", "getTripsByUser" );
    protected static final QName _qname_operation_findPlaces = new QName( "http://services.generated.carpool.sweng500.psu.edu", "findPlaces" );
    protected static final QName _qname_operation_updateTrip = new QName( "http://services.generated.carpool.sweng500.psu.edu", "updateTrip" );
    protected static final QName _qname_operation_getUser = new QName( "http://services.generated.carpool.sweng500.psu.edu", "getUser" );
    protected static final QName _qname_operation_findPlacesNearLocation = new QName( "http://services.generated.carpool.sweng500.psu.edu", "findPlacesNearLocation" );
    protected static final QName _qname_GetUserResponse = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "GetUserResponse" );
    protected static final QName _qname_UpdateTripRequest = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "UpdateTripRequest" );
    protected static final QName _qname_GetUserRequest = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "GetUserRequest" );
    protected static final QName _qname_GetTripsByUserResponse = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "GetTripsByUserResponse" );
    protected static final QName _qname_FindPlacesNearLocationRequest = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "FindPlacesNearLocationRequest" );
    protected static final QName _qname_FindPlacesNearLocationResponse = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "FindPlacesNearLocationResponse" );
    protected static final QName _qname_GetTripsByUserRequest = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "GetTripsByUserRequest" );
    protected static final QName _qname_FindPlacesResponse = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "FindPlacesResponse" );
    protected static final QName _qname_UpdateTripResponse = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "UpdateTripResponse" );
    protected static final QName _qname_FindPlacesRequest = new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "FindPlacesRequest" );
    protected static final Element _type_FindPlacesResponse;
    protected static final Element _type_FindPlacesRequest;
    protected static final Element _type_GetUserRequest;
    protected static final Element _type_GetUserResponse;
    protected static final Element _type_UpdateTripRequest;
    protected static final Element _type_FindPlacesNearLocationResponse;
    protected static final Element _type_UpdateTripResponse;
    protected static final Element _type_FindPlacesNearLocationRequest;
    protected static final Element _type_GetTripsByUserResponse;
    protected static final Element _type_GetTripsByUserRequest;

    static {
        _type_GetUserResponse = new Element( _qname_GetUserResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "user" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "givenName" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "surName" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "type" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "organization" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "savedPlaces" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, Element.UNBOUNDED, true )}), 0, 1, true )}), 1, 1, false );
        _type_UpdateTripRequest = new Element( _qname_UpdateTripRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "trip" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "title" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "startAddress" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "endAddress" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "startDate" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "endDate" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "organization" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "participants" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "user" ), _complexType( new Element[] {
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "givenName" ), Type.STRING, 0, 1, true ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "surName" ), Type.STRING, 0, 1, true )}), 0, 1, true ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "type" ), Type.STRING, 0, 1, true )}), 0, Element.UNBOUNDED, true )}), 1, 1, false )}), 1, 1, false );
        _type_GetUserRequest = new Element( _qname_GetUserRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "userId" ), Type.STRING )}), 1, 1, false );
        _type_GetTripsByUserResponse = new Element( _qname_GetTripsByUserResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "trip" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "title" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "startAddress" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "endAddress" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "startDate" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "endDate" ), Type.STRING, 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "organization" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true )}), 0, 1, true ),
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "participants" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "user" ), _complexType( new Element[] {
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "givenName" ), Type.STRING, 0, 1, true ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "surName" ), Type.STRING, 0, 1, true )}), 0, 1, true ),
                        new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "type" ), Type.STRING, 0, 1, true )}), 0, Element.UNBOUNDED, true )}), 0, Element.UNBOUNDED, true )}), 1, 1, false );
        _type_FindPlacesNearLocationRequest = new Element( _qname_FindPlacesNearLocationRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT ),
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT ),
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "numMiles" ), Type.INT )}), 1, 1, false );
        _type_FindPlacesNearLocationResponse = new Element( _qname_FindPlacesNearLocationResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "place" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, Element.UNBOUNDED, true )}), 1, 1, false );
        _type_GetTripsByUserRequest = new Element( _qname_GetTripsByUserRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "userId" ), Type.STRING )}), 1, 1, false );
        _type_FindPlacesResponse = new Element( _qname_FindPlacesResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "places" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address1" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "address2" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "description" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "category" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "latitude" ), Type.FLOAT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "longitude" ), Type.FLOAT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "phoneNumber" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "url" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.carpool.sweng500.psu.edu/xsd", "zoom" ), Type.INT, 0, 1, true )}), 0, Element.UNBOUNDED, true )}), 1, 1, false );
        _type_FindPlacesRequest = new Element( _qname_FindPlacesRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "userName" ), Type.STRING ),
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "type" ), Type.STRING )}), 1, 1, false );
        _type_UpdateTripResponse = new Element( _qname_UpdateTripResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.carpool.sweng500.psu.edu/xsd", "isSuccess" ), Type.BOOLEAN, 0, 1, true )}), 1, 1, false );
    }

    private static ComplexType _complexType( Element[] elements ) {
        ComplexType result = new ComplexType();
        result.elements = elements;
        return result;
    }
}
