package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    private String name;
    private LocalDate birthDate;
    private String notes;
    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerPet> customerPets = new HashSet<>();

    @ManyToMany(mappedBy = "pets", cascade = CascadeType.ALL)
    @Column(name = "schedule_id")
    private Set<Schedule> petSchedule = new HashSet<>();

    public void addCustomerPet(Customer customer) {
        CustomerPet customerPet = new CustomerPet();
        customerPet.setPet(this);
        customerPet.setCustomer(customer);
        customerPets.add(customerPet);
    }


}
