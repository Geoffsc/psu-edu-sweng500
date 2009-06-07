package edu.psu.sweng500.carpool.device.services.generated;

import javax.xml.namespace.QName;
public class Trip {

    private String id;

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private String title;

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private Place startAddress;

    public void setStartAddress( Place startAddress ) {
        this.startAddress = startAddress;
    }

    public Place getStartAddress() {
        return startAddress;
    }

    private Place endAddress;

    public void setEndAddress( Place endAddress ) {
        this.endAddress = endAddress;
    }

    public Place getEndAddress() {
        return endAddress;
    }

    private String startDate;

    public void setStartDate( String startDate ) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    private String endDate;

    public void setEndDate( String endDate ) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    private Organization organization;

    public void setOrganization( Organization organization ) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    private Participant[] participants;

    public void setParticipants( Participant[] participants ) {
        this.participants = participants;
    }

    public Participant[] getParticipants() {
        return participants;
    }

}
