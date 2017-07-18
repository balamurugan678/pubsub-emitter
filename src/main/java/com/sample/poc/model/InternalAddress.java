package com.sample.poc.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * Created by Bala on 14/07/2017.
 */
public class InternalAddress {

    @JacksonXmlProperty(isAttribute = true)
    private String href;

    @JacksonXmlText(value = true)
    private String value;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
