
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
                  "Place".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.beans.xsd.Place.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://exceptions.generated.carpool.sweng500.psu.edu/xsd".equals(namespaceURI) &&
                  "CarPoolServiceException".equals(typeName)){
                   
                            return  edu.psu.sweng500.carpool.generated.exceptions.xsd.CarPoolServiceException.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    