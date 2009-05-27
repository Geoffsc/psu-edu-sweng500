package edu.psu.sweng500.carpool.tests;

import java.io.StringWriter;

import javax.xml.stream.XMLOutputFactory;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.junit.Test;

import edu.psu.sweng500.carpool.services.utilities.ServiceUtilities;

public class ServiceTests
{
	private static EndpointReference targetEPR =
        new EndpointReference(
               "http://localhost:6187/sweng500_car_pool/services/SecurityService");
	
	private static EndpointReference targetEPR1 =
        new EndpointReference(
               "http://localhost:6187/sweng500_car_pool/services/CarPoolService");
	
	@Test
	public void testLogin()
	{
		try
		{
			OMFactory factory = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = factory.createOMNamespace(
		                "http://services.generated.security.sweng500.psu.edu/xsd", "xsd");
		
		    OMElement method = factory.createOMElement("login", omNs);
		    
		    method = ServiceUtilities.addValue(
		    		factory, method, omNs, "userName", "eborisow");
		    method = ServiceUtilities.addValue(
                    factory, method, omNs, "password", "password");
		    
		    ServiceClient serviceClient = new ServiceClient();
		
		    Options options = new Options();
		    serviceClient.setOptions(options);
		    options.setTo(targetEPR);
		
		    //Blocking invocation
		    OMElement result = serviceClient.sendReceive(method);
		
		    StringWriter writer = new StringWriter();
		    result.serialize(XMLOutputFactory.newInstance()
		            .createXMLStreamWriter(writer));
		    writer.flush();
		
		    System.out.println("Response: " + writer.toString());
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
	
	
	public void testFindPlaces()
	{
		try
		{
			OMFactory factory = OMAbstractFactory.getOMFactory();
		    OMNamespace omNs = factory.createOMNamespace(
		                "http://services.generated.carpool.sweng500.psu.edu/xsd", "xsd");
		
		    OMElement method = factory.createOMElement("findPlaces", omNs);
		    
		    method = ServiceUtilities.addValue(
		    		factory, method, omNs, "userName", "eborisow");
		    method = ServiceUtilities.addValue(
                    factory, method, omNs, "type", "Start point");
		    
		    ServiceClient serviceClient = new ServiceClient();
		
		    Options options = new Options();
		    serviceClient.setOptions(options);
		    options.setTo(targetEPR1);
		
		    //Blocking invocation
		    OMElement result = serviceClient.sendReceive(method);
		
		    StringWriter writer = new StringWriter();
		    result.serialize(XMLOutputFactory.newInstance()
		            .createXMLStreamWriter(writer));
		    writer.flush();
		
		    System.out.println("Response: " + writer.toString());
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
}
