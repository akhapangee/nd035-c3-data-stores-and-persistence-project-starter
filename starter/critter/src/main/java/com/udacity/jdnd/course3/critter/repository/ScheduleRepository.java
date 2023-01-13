package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class ScheduleRepository {
    @PersistenceContext
    EntityManager entityManager;
    private static final String FIND_SCHEDULE_BY_EMPLOYEE_ID =
            "select s from Schedule s join s.employees es  where es.id =:employeeId";

    public List<Schedule> findScheduleByEmployeeId(long employeeId) {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_SCHEDULE_BY_EMPLOYEE_ID, Schedule.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }

    private static final String FIND_SCHEDULE_BY_PET_ID =
            "select s from Schedule s join s.pets es  where es.id =:petId";

    public List<Schedule> findScheduleByPetId(long petId) {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_SCHEDULE_BY_PET_ID, Schedule.class);
        query.setParameter("petId", petId);
        return query.getResultList();
    }

    private static final String FIND_SCHEDULE_BY_CUSTOMER_ID =
            "select s from Schedule s join s.pets ps where ps.id in " +
                    "(select p.id from Customer c join c.pets p where c.id =:customerId)";

    public List<Schedule> findScheduleByCustomerId(Long customerId) {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_SCHEDULE_BY_CUSTOMER_ID, Schedule.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    public void persist(Schedule schedule) {
        entityManager.persist(schedule);
    }

    public Schedule find(Long id) {
        return entityManager.find(Schedule.class, id);
    }

    private static final String FIND_ALL = "select s from Schedule s ";

    public List<Schedule> findAll() {
        TypedQuery<Schedule> query = entityManager.createQuery(FIND_ALL, Schedule.class);
        return query.getResultList();
    }

    public void delete(Long id) {
        Schedule schedule = find(id);
        entityManager.remove(schedule);
    }

    public Schedule merge(Schedule schedule) {
        return entityManager.merge(schedule);
    }

}
