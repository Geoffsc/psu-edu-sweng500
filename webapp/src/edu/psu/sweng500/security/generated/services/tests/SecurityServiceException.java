
/**
 * SecurityServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */

package edu.psu.sweng500.security.generated.services.tests;

public class SecurityServiceException extends java.lang.Exception{
    
    private edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.SecurityServiceExceptionE faultMessage;
    
    public SecurityServiceException() {
        super("SecurityServiceException");
    }
           
    public SecurityServiceException(java.lang.String s) {
       super(s);
    }
    
    public SecurityServiceException(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.SecurityServiceExceptionE msg){
       faultMessage = msg;
    }
    
    public edu.psu.sweng500.security.generated.services.tests.SecurityServiceStub.SecurityServiceExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    