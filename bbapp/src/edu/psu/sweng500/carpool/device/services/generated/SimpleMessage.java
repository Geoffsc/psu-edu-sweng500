package edu.psu.sweng500.carpool.device.services.generated;

import javax.xml.namespace.QName;
public class SimpleMessage {

    private Boolean isSuccess;

    public void setIsSuccess( Boolean isSuccess ) {
        this.isSuccess = isSuccess;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    private Integer errorCode;

    public void setErrorCode( Integer errorCode ) {
        this.errorCode = errorCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    private String errorDescription;

    public void setErrorDescription( String errorDescription ) {
        this.errorDescription = errorDescription;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    private String message;

    public void setMessage( String message ) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    private String traceMessage;

    public void setTraceMessage( String traceMessage ) {
        this.traceMessage = traceMessage;
    }

    public String getTraceMessage() {
        return traceMessage;
    }

}
