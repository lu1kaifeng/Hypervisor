package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.CourseTrigger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseTriggerCache extends CrudRepository<CourseTrigger, Long> {
}
