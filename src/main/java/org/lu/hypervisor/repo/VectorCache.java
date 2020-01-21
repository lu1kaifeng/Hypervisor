package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.VectorEntry;
import org.springframework.data.repository.CrudRepository;

public interface VectorCache extends CrudRepository<VectorEntry, Long> {
}
