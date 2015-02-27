
package org.vaadin.backend.ejb;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CdiConfiguration {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "customer-db")
    public EntityManager entityManager;
    
}
