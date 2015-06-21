package com.stroodel.baasinga.model;

import javax.persistence.Entity;

/**
 * Created by vs on 19.06.15.
 */
@Entity
public class Contact extends AbstractEntity{

    String name;
    String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
