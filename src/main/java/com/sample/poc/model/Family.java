package com.sample.poc.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by Bala on 14/07/2017.
 */
@JacksonXmlRootElement(localName = "family")
public class Family {

    @JacksonXmlProperty(localName = "person")
    private Person[] person;

    @JacksonXmlProperty(localName = "address")
    private Address[] address;

    public Person[] getPerson() {
        return person;
    }

    public void setPerson(Person[] person) {
        this.person = person;
    }

    public Address[] getAddress() {
        return address;
    }

    public void setAddress(Address[] address) {
        this.address = address;
    }
}
