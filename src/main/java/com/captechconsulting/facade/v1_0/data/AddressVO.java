package com.captechconsulting.facade.v1_0.data;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class AddressVO {

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "^(([0-9]{5})|([0-9]{3} [0-9]{2}))?$")
    private String zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
