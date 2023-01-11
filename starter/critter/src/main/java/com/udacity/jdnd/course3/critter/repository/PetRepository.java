package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("select p from CustomerPet cp JOIN cp.customer c JOIN cp.pet p where c.id =:customerId")
    List<Pet> findPetsByCustomerId(Long customerId);

}
