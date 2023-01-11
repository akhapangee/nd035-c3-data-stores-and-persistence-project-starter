package com.udacity.jdnd.course3.critter.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer-sequence-generator"
    )
    @SequenceGenerator(
            name = "customer-sequence-generator",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String phoneNumber;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CustomerPet> customerPets = new HashSet<>();

    public void addCustomerPet(Pet pet) {
        CustomerPet customerPet = new CustomerPet();
        customerPet.setPet(pet);
        customerPet.setCustomer(this);
        customerPets.add(customerPet);
    }


}
