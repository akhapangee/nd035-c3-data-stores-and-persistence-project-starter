package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.error.RecordNotFoundException;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new RecordNotFoundException("Pet with ID " + petId + " not found");
        }
    }

    public List<Pet> findPetsByCustomer(Long id) {
        return petRepository.findPetsByCustomerId(id);
    }

}
