package edu.psu.sweng500.carpool.device.services.generated;

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
        _propertyValues = new Object[] { "http://192.168.1.6:6187/sweng500_car_pool/services/SecurityService/" };
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
    protected static final QName _qname_loginElementResponse = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "loginElementResponse" );
    protected static final QName _qname_loginElementRequest = new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "loginElementRequest" );
    protected static final Element _type_loginElementRequest;
    protected static final Element _type_loginElementResponse;

    static {
        _type_loginElementResponse = new Element( _qname_loginElementResponse, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "return" ), _complexType( new Element[] {
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "isSuccess" ), Type.BOOLEAN, 0, 1, false ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorCode" ), Type.INT, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "errorDescription" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "message" ), Type.STRING, 0, 1, true ),
                new Element( new QName( "http://beans.generated.security.sweng500.psu.edu/xsd", "traceMessage" ), Type.STRING, 0, 1, true )}), 1, 1, true )}), 1, 1, false );
        _type_loginElementRequest = new Element( _qname_loginElementRequest, _complexType( new Element[] {
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "userName" ), Type.STRING ),
            new Element( new QName( "http://services.generated.security.sweng500.psu.edu/xsd", "password" ), Type.STRING )}), 1, 1, false );
    }

    private static ComplexType _complexType( Element[] elements ) {
        ComplexType result = new ComplexType();
        result.elements = elements;
        return result;
    }
}
