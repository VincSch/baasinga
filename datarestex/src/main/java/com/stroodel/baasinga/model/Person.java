package com.stroodel.baasinga.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * Created by vs on 07.06.15.
 */

@Entity
public class Person extends AbstractEntity {

    private String name;
    private String birthday;

    @OneToOne
    private Address address;

    @OneToMany
    private List<Contact> contacts;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
