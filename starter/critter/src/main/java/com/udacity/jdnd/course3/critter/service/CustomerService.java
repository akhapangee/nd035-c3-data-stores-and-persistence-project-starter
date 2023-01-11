package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.error.RecordNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findCustomer(Long id) {
        try {
            return customerRepository.findById(id);
        } catch (Exception e) {
            log.error("An error occurred while retrieving customer by id: " + id);
            e.printStackTrace();
            throw new RecordNotFoundException("Customer with ID " + id + " not found!");
        }

    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        return customerRepository.findCustomersByPetId(petId).get(0);
    }

}
