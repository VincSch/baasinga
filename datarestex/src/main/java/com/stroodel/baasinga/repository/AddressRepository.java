package com.stroodel.baasinga.repository;

import com.stroodel.baasinga.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by vs on 18.06.15.
 */
@RestResource
public interface AddressRepository extends CrudRepository<Address, Long> {
}
