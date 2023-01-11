package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerService customerService;

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Pet findPetById(long petId) {
        return petRepository.findById(petId).get();
    }

    public List<Pet> findPetsByCustomer(Long id) {
        return petRepository.findPetsByCustomerId(id);
    }

}
