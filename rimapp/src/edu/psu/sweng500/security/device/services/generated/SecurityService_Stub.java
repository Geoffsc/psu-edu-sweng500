package edu.psu.sweng500.security.device.services.generated;

import javax.xml.rpc.JAXRPCException;
import javax.xml.namespace.QName;
import javax.microedition.xml.rpc.Operation;
import javax.microedition.xml.rpc.Type;
import javax.microedition.xml.rpc.ComplexType;
import javax.microedition.xml.rpc.Element;

public class SecurityService_Stub implements SecurityService, javax.xml.rpc.Stub {

    private String[] _propertyNames;
    private Object[] _propertyValues;

    public SecurityService_Stub() {
        _propertyNames = new String[] { ENDPOINT_ADDRESS_PROPERTY };
        _propertyValues = new Object[] { "http://geoffscholl.com:8080/sweng500_car_pool/services/SecurityService/" };
        // _propertyValues = new Object[] { "http://PLYW314928.internal.imsglobal.com:6187/sweng500_car_pool/services/SecurityService/" };
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

    public SimpleMessage login(String userName, String password) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            userName,
            password
        };

        Operation op = Operation.newInstance( _qname_operation_login, _type_loginElementRequest, _type_loginElementResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:login" );
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

        return SimpleMessage_fromObject((Object[])((Object[]) resultObj)[0]);
    }

    public SimpleMessage registerUser(User user) throws java.rmi.RemoteException {
        Object inputObject[] = new Object[] {
            User_toObject( user )
        };

        Operation op = Operation.newInstance( _qname_operation_registerUser, _type_registerUserRequest, _type_registerUserResponse );
        _prepOperation( op );
        op.setProperty( Operation.SOAPACTION_URI_PROPERTY, "urn:registerUser" );
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

        return SimpleMessage_fromObject((Object[])((Object[]) resultObj)[0]);
    }

    private static Object User_toObject( User obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 7 ];
        result[0] = obj.getId();
        result[1] = obj.getGivenName();
        result[2] = obj.getSurName();
        result[3] = obj.getUserName();
        result[4] = obj.getPassword();
        result[5] = obj.getType();
        result[6] = Organization_toObject( obj.getOrganization());
        return result;
    }

    private static Object Organization_toObject( Organization obj ) {
        if(obj == null) return null;
        Object result[] = new Object[ 2 ];
        result[0] = obj.getId();
        result[1] = obj.getName();
        return result;
    }

    private static SimpleMessage SimpleMessage_fromObject( Object obj[] ) {
        if(obj == null) return null;
        SimpleMessage result = new SimpleMessage();
        result.setIsSuccess((Boolean )obj[0]);
        result.setErrorCode((Integer )obj[1]);
        result.setErrorDescription((String )obj[2]);
        result.setMessage((String )obj[3]);
        result.setTraceMessage((String )obj[4]);
        return result;
    }

    protected static final QName _qname_operation_login = new QName( "http://services.generated.security.sweng500.psu.edu", "login" );
    protected static final QName _qname_operation_registerUser = new QName( "http://services.generated.security.sweng500.psu.edu", "registerUser" );
    protected static final QName _qname_registerUserRequest = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "registerUserRequest" );
    protected static final QName _qname_loginElementResponse = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "loginElementResponse" );
    protected static final QName _qname_loginElementRequest = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "loginElementRequest" );
    protected static final QName _qname_registerUserResponse = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "registerUserResponse" );
    protected static final Element _type_loginElementResponse;
    protected static final Element _type_registerUserRequest;
    protected static final Element _type_registerUserResponse;
    protected static final Element _type_loginElementRequest;

    static {
        _type_registerUserRequest = new Element( _qname_registerUserRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "user" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "givenName" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "surName" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "userName" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "password" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "type" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "organization" ), _complexType( new Element[] {
                    new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "id" ), Type.STRING ),
                    new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "name" ), Type.STRING, 0, 1, true )}), 0, 1, true )}), 1, 1, false )}), 1, 1, false );
        _type_loginElementResponse = new Element( _qname_loginElementResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "return" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "isSuccess" ), Type.BOOLEAN, 0, 1, false ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorCode" ), Type.INT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorDescription" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "message" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "traceMessage" ), Type.STRING, 0, 1, true )}), 0, 1, true )}), 1, 1, false );
        _type_loginElementRequest = new Element( _qname_loginElementRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "userName" ), Type.STRING ),
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "password" ), Type.STRING )}), 1, 1, false );
        _type_registerUserResponse = new Element( _qname_registerUserResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "return" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "isSuccess" ), Type.BOOLEAN, 0, 1, false ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorCode" ), Type.INT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorDescription" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "message" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "traceMessage" ), Type.STRING, 0, 1, true )}), 0, 1, true )}), 1, 1, false );
    }

    private static ComplexType _complexType( Element[] elements ) {
        ComplexType result = new ComplexType();
        result.elements = elements;
        return result;
    }
}
