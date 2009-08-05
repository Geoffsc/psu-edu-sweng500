
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
                                    * @param updateTripRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse updateTrip
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest updateTripRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateUserRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse updateUser
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.UpdateUserRequest updateUserRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateTripUserRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse updateTripUser
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest updateTripUserRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param getTripRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse getTrip
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest getTripRequest
                 )
            throws CarPoolServiceException;
        
         
        /**
         * Auto generated method signature
         * 
                                    * @param updateSavedAddressesRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse updateSavedAddresses
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.UpdateSavedAddressesRequest updateSavedAddressesRequest
                 )
            throws CarPoolServiceException;
        
         
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
                                    * @param findSimilarTripsRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse findSimilarTrips
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest findSimilarTripsRequest
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
                                    * @param searchTripsRequest
             * @throws CarPoolServiceException : 
         */

        
                public edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse findTrips
                (
                  edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest searchTripsRequest
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
    