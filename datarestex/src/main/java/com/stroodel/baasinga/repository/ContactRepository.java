package com.stroodel.baasinga.repository;

import com.stroodel.baasinga.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by vs on 19.06.15.
 */
@RestResource
public interface ContactRepository extends CrudRepository<Contact, Long> {
}
