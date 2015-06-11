package com.stroodel.baasinga.service;

import com.stroodel.baasinga.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by vs on 07.06.15.
 */
@RestResource
public interface PersonRepository extends CrudRepository<Person, Long>{
}
