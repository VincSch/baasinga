package com.stroodel.baasinga.model;

import javax.persistence.Entity;

/**
 * Created by vs on 18.06.15.
 */
@Entity
public class Address extends AbstractEntity {

    private String street;
    private String City;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }
}
