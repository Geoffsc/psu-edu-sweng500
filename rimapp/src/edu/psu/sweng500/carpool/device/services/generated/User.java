package edu.psu.sweng500.carpool.device.services.generated;

import javax.xml.namespace.QName;
public class User {

    private String id;

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    private String givenName;

    public void setGivenName( String givenName ) {
        this.givenName = givenName;
    }

    public String getGivenName() {
        return givenName;
    }

    private String surName;

    public void setSurName( String surName ) {
        this.surName = surName;
    }

    public String getSurName() {
        return surName;
    }

    private String type;

    public void setType( String type ) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private Organization organization;

    public void setOrganization( Organization organization ) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    private Place[] savedPlaces;

    public void setSavedPlaces( Place[] savedPlaces ) {
        this.savedPlaces = savedPlaces;
    }

    public Place[] getSavedPlaces() {
        return savedPlaces;
    }

}
