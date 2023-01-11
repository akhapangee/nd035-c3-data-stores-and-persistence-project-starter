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
    @Column(name = "employee_id")
    @JoinColumn(name = "employee_id", foreignKey = @ForeignKey(name = "EMPLOYEE_ID_SCHEDULE_FK"))
    private List<Employee> employees;
    @ManyToMany
    @Column(name = "pet_id")
    @JoinColumn(name = "pet_id", foreignKey = @ForeignKey(name = "PET_ID_SCHEDULE_FK"))
    private List<Pet> pets;

    @ElementCollection
    private Set<EmployeeSkill> activities;
    private LocalDate date;

}
