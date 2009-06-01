
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:44 LKT)
 */

            package edu.psu.sweng500.carpool.generated.beans.xsd;
            /**
            *  ExtensionMapper class
            */
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "Trip".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.Trip.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "Organization".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.Organization.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "PlaceList".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.PlaceList.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "User".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.User.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "Place".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.Place.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://exceptions.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "CarPoolServiceException".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.exceptions.xsd.CarPoolServiceException.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "Participant".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.Participant.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://beans.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "TripList".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.TripList.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    