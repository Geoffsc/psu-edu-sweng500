package edu.psu.sweng500.carpool.exceptions;

/**
 * This class is used to return security-type errors during failure
 *
 * @author Eric Borisow
 */
public class SecurityException 
    extends Exception 
    implements java.io.Serializable
{
    /**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6494022129138530647L;

	/**
     * Indicates that the user specified could not be found
     */
    public static final int USER_NOT_FOUND = 6000;
    public static final int INVALID_PASSWORD = 6001;

    /**
     * An internal error occurred in the service
     */
    public static final int INTERNAL_ERROR = 6999;

    private int errorCode;

    private String errorDescription;
    
    private String traceMessage;

    /**
     * Create a new SecurityException object
     */
    public SecurityException()
    {
    }

    /**
     * Create a new SecurityException object.
     *
     * @param newErrorCode The error code associated with this exception
     * @param newErrorDescription The error description associated with this exception
     */
    public SecurityException(final int newErrorCode,
        final String newErrorDescription)
    {
        this.errorCode = newErrorCode;
        this.errorDescription = newErrorDescription;
    }
    
    /**
     * Create a new SecurityException object.
     *
     * @param newErrorCode The error code associated with this exception
     * @param newErrorDescription The error description associated with this exception
     * @param traceMessage The error trace that occurred
     */
    public SecurityException(final int newErrorCode,
        final String newErrorDescription,
        final String traceMessage)
    {
        this.errorCode = newErrorCode;
        this.errorDescription = newErrorDescription;
        this.traceMessage = traceMessage;
    }

    /**
     * Return the error code from this exception
     *
     * @return Return the error code.
     */
    public int getErrorCode()
    {
        return this.errorCode;
    }

    /**
     * Set the error code for this exception
     *
     * @param errorCode The error code to set for this exception
     */
    public void setErrorCode(final int errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * Retrieve the error description
     *
     * @return Returns the error description.
     */
    public String getErrorDescription()
    {
        return this.errorDescription;
    }

    /**
     * Set the description for this exception
     *
     * @param errorDescription The error description to set
     */
    public void setErrorDescription(final String errorDescription)
    {
        this.errorDescription = errorDescription;
    }
    
    public String getTraceMessage()
    {
        return this.traceMessage;
    }
    
    public void setTraceMessage(String traceMessage)
    {
        this.traceMessage = traceMessage;
    }
    
    @Override
    public String getMessage()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("An error occurred in the directory service: " + 
            this.getErrorCode() + " - " + this.getErrorDescription());
        if (this.getTraceMessage() != null)
        {
            buf.append("\n\nTrace message: " + this.getTraceMessage());
        }
        return buf.toString();
    }
}
