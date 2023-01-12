package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(Schedule schedule) {
        scheduleRepository.persist(schedule);
        return scheduleRepository.find(schedule.getId());
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.findScheduleByEmployeeId(employeeId);
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.findScheduleByPetId(petId);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        return scheduleRepository.findScheduleByCustomerId(customerId);
    }
}
