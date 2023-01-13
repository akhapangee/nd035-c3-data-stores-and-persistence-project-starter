package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CustomerRepository {
    @PersistenceContext
    EntityManager entityManager;

    private static final String FIND_ALL_CUSTOMERS = "select c from Customer c ";
    private static final String FIND_CUSTOMER_BY_PET_ID =
            "select c from Customer c INNER JOIN c.pets p where p.id =:petId";

    public Customer findCustomersByPetId(long petId) {
        TypedQuery<Customer> query = entityManager.createQuery(FIND_CUSTOMER_BY_PET_ID, Customer.class);
        query.setParameter("petId", petId);
        return query.getSingleResult();
    }

    public List<Customer> findAll() {
        TypedQuery<Customer> query = entityManager.createQuery(FIND_ALL_CUSTOMERS, Customer.class);
        return query.getResultList();
    }

    public Customer save(Customer customer) {
        // Fixing detached entity passed to persist issue
        customer.setId(null);
        entityManager.persist(customer);
        return findById(customer.getId());
    }

    public Customer findById(Long id) {
        return entityManager.find(Customer.class, id);
    }


    public void delete(Long id) {
        Customer customer = findById(id);
        entityManager.remove(customer);
    }


}
