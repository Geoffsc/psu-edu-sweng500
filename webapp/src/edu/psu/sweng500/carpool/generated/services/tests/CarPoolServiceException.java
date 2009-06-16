
/**
 * CarPoolServiceException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */

package edu.psu.sweng500.carpool.generated.services.tests;

public class CarPoolServiceException extends java.lang.Exception{
    
    private edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.CarPoolServiceExceptionE faultMessage;
    
    public CarPoolServiceException() {
        super("CarPoolServiceException");
    }
           
    public CarPoolServiceException(java.lang.String s) {
       super(s);
    }
    
    public CarPoolServiceException(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.CarPoolServiceExceptionE msg){
       faultMessage = msg;
    }
    
    public edu.psu.sweng500.carpool.generated.services.tests.CarPoolServiceStub.CarPoolServiceExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    