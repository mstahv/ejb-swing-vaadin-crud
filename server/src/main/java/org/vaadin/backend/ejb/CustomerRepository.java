
package org.vaadin.backend.ejb;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.vaadin.domain.model.Customer;

@Repository(forEntity = Customer.class)
public interface CustomerRepository extends EntityRepository<Customer, Integer> {
    
}