package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepo extends CrudRepository<Video,Long> {
    Video findByWebId(String WebId);
}
