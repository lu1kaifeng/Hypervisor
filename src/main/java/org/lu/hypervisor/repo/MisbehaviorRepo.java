package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.Misbehavior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MisbehaviorRepo extends JpaRepository<Misbehavior, Long> {
}
