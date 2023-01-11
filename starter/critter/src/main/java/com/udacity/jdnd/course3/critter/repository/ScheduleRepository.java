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

    private static final String FIND_ALL =
            "select s from Schedule s ";


    public void persist(Schedule schedule) {
        entityManager.persist(schedule);
    }

    public Schedule find(Long id) {
        return entityManager.find(Schedule.class, id);
    }

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
