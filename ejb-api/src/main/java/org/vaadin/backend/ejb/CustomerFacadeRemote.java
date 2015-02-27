/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vaadin.backend.ejb;

import java.util.List;
import javax.ejb.Remote;
import org.vaadin.domain.model.Customer;

@Remote
public interface CustomerFacadeRemote {

    Customer save(Customer customer);

    Customer findById(int id);

    void remove(Customer customer);

    List<Customer> findAll();

    List<Customer> findRange(int startIndex, int maxResults);

    int count();

}
