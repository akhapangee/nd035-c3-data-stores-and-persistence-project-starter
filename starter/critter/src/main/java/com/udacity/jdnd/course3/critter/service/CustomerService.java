package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.error.RecordNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetService petService;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findCustomer(Long id) {
        return Optional.ofNullable(customerRepository.findById(id)).orElseThrow(() ->
                new RecordNotFoundException("Customer not found!")
        );
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId) {
        // Check if pet exists
        petService.findPetById(petId);

        try {
            return customerRepository.findCustomersByPetId(petId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RecordNotFoundException("No owner found for pet " + petId);
        }

    }

}
