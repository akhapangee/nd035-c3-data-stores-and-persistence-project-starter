package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private List<Employee> employees;
    @ManyToMany
    @JoinTable(
            name = "schedule_pets",
            joinColumns = @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    )
    private List<Pet> pets;

    @ElementCollection
    private Set<EmployeeSkill> activities;
    private LocalDate date;

}
