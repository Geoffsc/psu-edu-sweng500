package edu.psu.sweng500.carpool.services.utilities;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMText;

public class ServiceUtilities
{
	private ServiceUtilities() {}
	
	public static OMElement addValue(
			final OMFactory factory, 
			final OMElement parent, 
			final OMNamespace ns, 
			final String elementName, 
			final String value)
	{
		OMElement element = factory.createOMElement(elementName, ns);
		OMText textValue = factory.createOMText(value);
		
		element.addChild(textValue);
		parent.addChild(element);
		
		return parent;
	}
	
	public static OMElement addValue(
			final OMFactory factory, 
			final OMElement parent, 
			final OMNamespace ns, 
			final String elementName, 
			final boolean value)
	{
		OMElement element = factory.createOMElement(elementName, ns);
		OMText textValue = factory.createOMText(new Boolean(value).toString());
		
		element.addChild(textValue);
		parent.addChild(element);
		
		return parent;
	}
}
