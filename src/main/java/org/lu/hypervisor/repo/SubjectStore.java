package org.lu.hypervisor.repo;

import org.lu.hypervisor.entity.Subject;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStore extends ContentStore<Subject, String> {
}
