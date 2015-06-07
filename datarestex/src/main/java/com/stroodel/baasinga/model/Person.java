package com.stroodel.baasinga.model;

import javax.persistence.Entity;

/**
 * Created by vs on 07.06.15.
 */

@Entity
public class Person extends AbstractEntity{

    private String name;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
