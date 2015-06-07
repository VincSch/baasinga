package com.stroodel.baasinga.service;

import com.stroodel.baasinga.model.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by vs on 07.06.15.
 */
public interface PersonRepository extends CrudRepository<Person, Long>{
}
