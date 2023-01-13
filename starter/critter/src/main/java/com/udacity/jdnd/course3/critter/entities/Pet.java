package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private PetType type;
    @Nationalized
    @NotNull
    private String name;
    private LocalDate birthDate;
    private String notes;
    @ManyToOne
    private Customer customer;

    @ManyToMany(mappedBy = "pets", cascade = CascadeType.ALL)
    @Column(name = "schedule_id")
    private Set<Schedule> petSchedule = new HashSet<>();


}
