package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.HashMapToJsonConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Nationalized
    @NotNull
    private String name;
    @Convert(converter = HashMapToJsonConverter.class)
    private Map<String, Object> skills = new HashMap<>();
    @Convert(converter = HashMapToJsonConverter.class)
    private Map<String, Object> daysAvailable = new HashMap<>();
    @ManyToMany(mappedBy = "employees", targetEntity = Schedule.class)
    private List<Schedule> schedule;

    public void setSkills(Set<EmployeeSkill> employeeSkills) {
        skills.put("skills", employeeSkills);
    }

    public Set<EmployeeSkill> getSkills() {
        Object object = skills.get("skills");
        if (object instanceof List) {
            List<String> employeeSkills = (List<String>) object;
            return employeeSkills.stream().map(s ->
                    EmployeeSkill.valueOf(s)
            ).collect(Collectors.toSet());
        }
        return (Set<EmployeeSkill>) object;
    }

    public void setDaysAvailable(Set<DayOfWeek> days) {
        daysAvailable.put("daysAvailable", days);
    }

    public Set<DayOfWeek> getDaysAvailable() {
        Object object = daysAvailable.get("daysAvailable");
        if (object instanceof List) {
            List<String> dayOfWeeks = (List<String>) object;
            return dayOfWeeks.stream().map(s ->
                    DayOfWeek.valueOf(s)
            ).collect(Collectors.toSet());
        }
        return (Set<DayOfWeek>) object;
    }
}
