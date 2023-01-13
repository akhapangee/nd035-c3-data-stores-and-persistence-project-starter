package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Here 'customerPets' is the variable name from Customer entity. Customer has one-to-many relationship with CustomerPet
    // and Pet has same type of relationship with CustomerPet
    @Query("select p from Customer c join c.customerPets cp join cp.pet p where c.id =:customerId")
    List<Pet> findPetsByCustomerId(Long customerId);
}
