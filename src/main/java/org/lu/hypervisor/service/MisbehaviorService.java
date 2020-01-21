package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.repo.MisbehaviorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MisbehaviorService {
    private MisbehaviorRepo misbehaviorRepo;

    @Autowired
    public MisbehaviorService(MisbehaviorRepo misbehaviorRepo) {
        this.misbehaviorRepo = misbehaviorRepo;
    }

    public Misbehavior newMisbehavior(Misbehavior misbehavior) {
        return misbehaviorRepo.save(misbehavior);
    }
}
