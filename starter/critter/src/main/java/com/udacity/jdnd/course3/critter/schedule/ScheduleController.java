package com.udacity.jdnd.course3.critter.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.error.RecordNotFoundException;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PetService petService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule savedSchedule = scheduleService.createSchedule(convertScheduleDTOToEntity(scheduleDTO));
        return convertEntityToScheduleDTO(savedSchedule);
    }

    private ScheduleDTO convertEntityToScheduleDTO(Schedule savedSchedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        BeanUtils.copyProperties(savedSchedule, scheduleDTO);

        List<Employee> test = savedSchedule.getEmployees();
        List<Pet> fdfd = savedSchedule.getPets();

        List<Long> employeeList = savedSchedule.getEmployees().stream()
                .map(employee -> {
                    return employee.getId();
                })
                .collect(Collectors.toList());

        List<Long> petList = savedSchedule.getPets().stream()
                .map(pet -> {
                    return pet.getId();
                }).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeList);
        scheduleDTO.setPetIds(petList);

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Employee> employeeList = scheduleDTO.getEmployeeIds().stream()
                .map(employeeId -> {
                    return employeeService.findEmployeeById(employeeId);
                })
                .collect(Collectors.toList());

        // Check if requested activities are available based on employee records
        Set<EmployeeSkill> allSkillsFromRequest = scheduleDTO.getActivities();
        Set<EmployeeSkill> availableSkillsForRequestedEmployees = new HashSet<>();
        employeeList.forEach(employee -> {
            availableSkillsForRequestedEmployees.addAll(employee.getSkills());
        });

        allSkillsFromRequest.forEach(employeeSkill -> {
            if (!availableSkillsForRequestedEmployees.contains(employeeSkill)) {
                throw new RecordNotFoundException(employeeSkill + " is not available for the all requested employees!");
            }
        });

        List<Pet> petList = scheduleDTO.getPetIds().stream()
                .map(petId -> {
                    return petService.findPetById(petId);
                }).collect(Collectors.toList());
        schedule.setEmployees(employeeList);
        schedule.setPets(petList);

        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(schedule -> convertEntityToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(schedule -> {
                    return convertEntityToScheduleDTO(schedule);
                }).collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream()
                .map(schedule -> {
                    return convertEntityToScheduleDTO(schedule);
                }).collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream()
                .map(schedule -> {
                    return convertEntityToScheduleDTO(schedule);
                }).collect(Collectors.toList());
    }
}
