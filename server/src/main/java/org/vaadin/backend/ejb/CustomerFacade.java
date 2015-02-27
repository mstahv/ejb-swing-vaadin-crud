package org.vaadin.backend.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.vaadin.domain.model.Customer;

/**
 * A simple facade for Customer entity, implemented with DeltaSpike Data module.
 *
 * @author Matti Tahvonen
 */
@Stateless
public class CustomerFacade implements CustomerFacadeRemote {

    @Inject
    CustomerRepository repository;
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Customer save(Customer customer) {
        if(customer.isPersisted()) {
            // At least OpenEJB don't properly return indenfier for deserrialized
            // entity and DeltaSpike Data don't notice this, workaround this by
            // manually calling merge
            return em.merge(customer);
        }
        return repository.save(customer);
    }

    @Override
    public Customer findById(int id) {
        return repository.findBy(id);
    }

    @Override
    public void remove(Customer customer) {
        repository.remove(repository.findBy(customer.getId()));
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Customer> findRange(int startIndex, int maxResults) {
        return repository.findAll(startIndex, maxResults);
    }
    
    @Override
    public int count() {
        return repository.count().intValue();
    }

}
