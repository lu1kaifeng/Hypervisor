package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.CourseAttendee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseAttendeeCache extends CrudRepository<CourseAttendee, Long> {
}
