package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customerSaved = customerService.saveCustomer(convertCustomerDTOToEntity(customerDTO));
        return convertEntityToCustomerDTO(customerSaved);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(customer -> convertEntityToCustomerDTO(customer))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer customer = customerService.getOwnerByPet(petId);

        return convertEntityToCustomerDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.save(convertEmployeeDTOToEntity(employeeDTO));
        return convertEntityToEmployeeDTO(savedEmployee);
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        return convertEntityToEmployeeDTO(employee);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        Employee employee = employeeService.findEmployeeById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeService.save(employee);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    public static Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public CustomerDTO convertEntityToCustomerDTO(Customer customer) {

        List<Pet> pets = petService.findPetsByCustomer(customer.getId());

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        customerDTO.setPetIds(pets.stream().map(pet -> pet.getId()).collect(Collectors.toList()));
        customerDTO.setNotes(pets.stream().map(pet -> pet.getNotes()).distinct().collect(Collectors.joining(",")));

        return customerDTO;
    }

    public static Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }

    public static EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

}
