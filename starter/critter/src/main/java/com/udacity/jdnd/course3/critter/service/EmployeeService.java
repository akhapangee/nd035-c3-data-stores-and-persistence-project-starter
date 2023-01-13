package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.error.RecordNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException("Employee with ID " + employeeId + " not found!");
        }

    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Filter employees by skills and date(day)
     *
     * @param employeeDTO
     * @return
     */
    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        DayOfWeek dayOfWeekFromRequest = employeeDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> skillsFromRequest = employeeDTO.getSkills();

        List<Employee> employeesFromDatabase = getAllEmployees();

        // Filter employees based on skills and date
        Set<Employee> foundEmployeesByServiceAndDate = new HashSet<>();
        for (Employee employee : employeesFromDatabase) {
            Set<EmployeeSkill> employeeSkillsFromDatabase = employee.getSkills();
            Set<DayOfWeek> daysAvailableFromDatabase = employee.getDaysAvailable();

            if (employeeSkillsFromDatabase == null || daysAvailableFromDatabase == null) {
                continue;
            }

            if (skillsExists(skillsFromRequest, employeeSkillsFromDatabase)
                    && checkDaysAvailable(dayOfWeekFromRequest, daysAvailableFromDatabase)) {
                foundEmployeesByServiceAndDate.add(employee);
            }

        }
        return foundEmployeesByServiceAndDate.stream().collect(Collectors.toList());
    }

    private boolean checkDaysAvailable(DayOfWeek dayOfWeekFromRequest, Set<DayOfWeek> daysAvailableFromDatabase) {
        return daysAvailableFromDatabase.contains(dayOfWeekFromRequest);
    }

    private boolean skillsExists(Set<EmployeeSkill> skillsFromRequest, Set<EmployeeSkill> employeeSkillsFromDatabase) {
        for (EmployeeSkill employeeSkill : skillsFromRequest) {
            if (!employeeSkillsFromDatabase.contains(employeeSkill)) {
                return false;
            }
        }
        return true;
    }
}
