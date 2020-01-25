package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.repo.MisbehaviorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MisbehaviorService {
    private MisbehaviorRepo misbehaviorRepo;
    private SubjectService subjectService;

    @Autowired
    public MisbehaviorService(MisbehaviorRepo misbehaviorRepo, SubjectService subjectService) {
        this.misbehaviorRepo = misbehaviorRepo;
        this.subjectService = subjectService;
    }

    public Misbehavior newMisbehavior(Misbehavior misbehavior) {
        return misbehaviorRepo.save(misbehavior);
    }

    public List<Misbehavior> getMisbehavior(Long subjectId) {
        if (!subjectService.getSubjectById(subjectId).isPresent()) return null;
        Subject subject = subjectService.getSubjectById(subjectId).get();
        Misbehavior misbehavior = new Misbehavior();
        misbehavior.setSubject(subject);
        return misbehaviorRepo.findAll(Example.of(misbehavior));
    }

    public List<Misbehavior> getAll() {
        return misbehaviorRepo.findAll();
    }
}
