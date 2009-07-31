
/**
 * SecurityServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */
    package edu.psu.sweng500.security.generated.services;
    /**
     *  SecurityServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface SecurityServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param loginElementRequest
             * @throws SecurityServiceException : 
         */

        
                public edu.psu.sweng500.security.generated.services.xsd.LoginElementResponse login
                (
                  edu.psu.sweng500.security.generated.services.xsd.LoginElementRequest loginElementRequest
                 )
            throws SecurityServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param registerUserRequest
             * @throws SecurityServiceException : 
         */

        
                public edu.psu.sweng500.security.generated.services.xsd.RegisterUserResponse registerUser
                (
                  edu.psu.sweng500.security.generated.services.xsd.RegisterUserRequest registerUserRequest
                 )
            throws SecurityServiceException;
        
         }
    