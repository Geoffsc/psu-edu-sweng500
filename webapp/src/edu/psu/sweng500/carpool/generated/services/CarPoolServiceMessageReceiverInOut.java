
/**
 * CarPoolServiceMessageReceiverInOut.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 19, 2008 (10:13:39 LKT)
 */
        package edu.psu.sweng500.carpool.generated.services;

        /**
        *  CarPoolServiceMessageReceiverInOut message receiver
        */

        public class CarPoolServiceMessageReceiverInOut extends org.apache.axis2.receivers.AbstractInOutMessageReceiver{


        public void invokeBusinessLogic(org.apache.axis2.context.MessageContext msgContext, org.apache.axis2.context.MessageContext newMsgContext)
        throws org.apache.axis2.AxisFault{

        try {

        // get the implementation class for the Web Service
        Object obj = getTheImplementationObject(msgContext);

        CarPoolServiceSkeletonInterface skel = (CarPoolServiceSkeletonInterface)obj;
        //Out Envelop
        org.apache.axiom.soap.SOAPEnvelope envelope = null;
        //Find the axisOperation that has been set by the Dispatch phase.
        org.apache.axis2.description.AxisOperation op = msgContext.getOperationContext().getAxisOperation();
        if (op == null) {
        throw new org.apache.axis2.AxisFault("Operation is not located, if this is doclit style the SOAP-ACTION should specified via the SOAP Action to use the RawXMLProvider");
        }

        java.lang.String methodName;
        if((op.getName() != null) && ((methodName = org.apache.axis2.util.JavaUtils.xmlNameToJava(op.getName().getLocalPart())) != null)){

        

            if("updateTrip".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse updateTripResponse39 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               updateTripResponse39 =
                                                   
                                                   
                                                         skel.updateTrip(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), updateTripResponse39, false);
                                    } else 

            if("updateTripUser".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse simpleMessageResponse41 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               simpleMessageResponse41 =
                                                   
                                                   
                                                         skel.updateTripUser(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), simpleMessageResponse41, false);
                                    } else 

            if("getTrip".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse getTripResponse43 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTripResponse43 =
                                                   
                                                   
                                                         skel.getTrip(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTripResponse43, false);
                                    } else 

            if("getUser".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse getUserResponse45 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getUserResponse45 =
                                                   
                                                   
                                                         skel.getUser(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getUserResponse45, false);
                                    } else 

            if("findSimilarTrips".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse getTripsByUserResponse47 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTripsByUserResponse47 =
                                                   
                                                   
                                                         skel.findSimilarTrips(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTripsByUserResponse47, false);
                                    } else 

            if("findPlacesNearLocation".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse findPlacesNearLocationResponse49 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               findPlacesNearLocationResponse49 =
                                                   
                                                   
                                                         skel.findPlacesNearLocation(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), findPlacesNearLocationResponse49, false);
                                    } else 

            if("findTrips".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse getTripsByUserResponse51 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTripsByUserResponse51 =
                                                   
                                                   
                                                         skel.findTrips(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTripsByUserResponse51, false);
                                    } else 

            if("findPlaces".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse findPlacesResponse53 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               findPlacesResponse53 =
                                                   
                                                   
                                                         skel.findPlaces(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), findPlacesResponse53, false);
                                    } else 

            if("getTripsByUser".equals(methodName)){
                
                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse getTripsByUserResponse55 = null;
	                        edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest wrappedParam =
                                                             (edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest)fromOM(
                                    msgContext.getEnvelope().getBody().getFirstElement(),
                                    edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest.class,
                                    getEnvelopeNamespaces(msgContext.getEnvelope()));
                                                
                                               getTripsByUserResponse55 =
                                                   
                                                   
                                                         skel.getTripsByUser(wrappedParam)
                                                    ;
                                            
                                        envelope = toEnvelope(getSOAPFactory(msgContext), getTripsByUserResponse55, false);
                                    
            } else {
              throw new java.lang.RuntimeException("method not found");
            }
        

        newMsgContext.setEnvelope(envelope);
        }
        } catch (CarPoolServiceException e) {

            msgContext.setProperty(org.apache.axis2.Constants.FAULT_NAME,"CarPoolServiceException");
            org.apache.axis2.AxisFault f = createAxisFault(e);
            if (e.getFaultMessage() != null){
                f.setDetail(toOM(e.getFaultMessage(),false));
            }
            throw f;
            }
        
        catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
        }
        
        //
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse wrapupdateTrip(){
                                edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse wrapupdateTripUser(){
                                edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse wrapgetTrip(){
                                edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse wrapgetUser(){
                                edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrapfindSimilarTrips(){
                                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse wrapfindPlacesNearLocation(){
                                edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse();
                                return wrappedElement;
                         }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrapfindTrips(){
                                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse();
                                return wrappedElement;
                         }
                    
                    private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse param, boolean optimizeContent)
                        throws org.apache.axis2.AxisFault{
                      try{
                          org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                           
                                    emptyEnvelope.getBody().addChild(param.getOMElement(edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse.MY_QNAME,factory));
                                

                         return emptyEnvelope;
                    } catch(org.apache.axis2.databinding.ADBException e){
                        throw org.apache.axis2.AxisFault.makeFault(e);
                    }
                    }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse wrapfindPlaces(){
                                edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse();
                                return wrappedElement;
                         }
                    
                         private edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrapgetTripsByUser(){
                                edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse wrappedElement = new edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse();
                                return wrappedElement;
                         }
                    


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.UpdateTripUserRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.SimpleMessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetUserRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.FindSimilarTripsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesNearLocationResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.SearchTripsRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.FindPlacesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserRequest.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.GetTripsByUserResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.class.equals(type)){
                
                           return edu.psu.sweng500.carpool.generated.services.xsd.CarPoolServiceExceptionE.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    

        /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
        private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
        org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
        returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
        return returnMap;
        }

        private org.apache.axis2.AxisFault createAxisFault(java.lang.Exception e) {
        org.apache.axis2.AxisFault f;
        Throwable cause = e.getCause();
        if (cause != null) {
            f = new org.apache.axis2.AxisFault(e.getMessage(), cause);
        } else {
            f = new org.apache.axis2.AxisFault(e.getMessage());
        }

        return f;
    }

        }//end of class
    