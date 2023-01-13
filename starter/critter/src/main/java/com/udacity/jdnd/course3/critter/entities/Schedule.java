package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "schedule_employees",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    )
    @Column(name = "employee_id")
    @NotNull
    private List<Employee> employees;
    @ManyToMany
    @JoinTable(
            name = "schedule_pets",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    )
    @NotNull
    private List<Pet> pets;

    @ElementCollection
    @CollectionTable(
            name = "schedule_activities",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    )
    @NotNull
    private Set<EmployeeSkill> activities;
    @NotNull
    private LocalDate date;

}
