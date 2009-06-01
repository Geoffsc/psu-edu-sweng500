
/**
 * CarPoolServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */
    package edu.psu.sweng500.carpool.generated.services;
    /**
     *  CarPoolServiceSkeletonInterface java skeleton interface for the axisService
     */
    public interface CarPoolServiceSkeletonInterface {
     
         
        /**
         * Auto generated method signature
         * 
                                    * @param getUserRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse getUser
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest getUserRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param findPlacesNearLocationRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse findPlacesNearLocation
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest findPlacesNearLocationRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param findPlacesRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse findPlaces
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest findPlacesRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getTripsByUserRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse getTripsByUser
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest getTripsByUserRequest
                 )
            throws CarPoolServiceException;
        
         }
    