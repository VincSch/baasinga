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

    public String name;
    public String birthday;

    @OneToOne
    public Address address;

    @OneToMany
    public List<Contact> contacts;

}
