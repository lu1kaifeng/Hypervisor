package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.service.MisbehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MisbehaviorApiController implements MisbehaviorApi {
    private MisbehaviorService misbehaviorService;

    @Autowired
    public MisbehaviorApiController(MisbehaviorService misbehaviorService) {
        this.misbehaviorService = misbehaviorService;
    }

    @Override
    public ResponseEntity<Misbehavior> getMisbehavior(Long subjectId) {
        if (misbehaviorService.getMisbehavior(subjectId) != null) {
            return new ResponseEntity<>(misbehaviorService.getMisbehavior(subjectId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Misbehavior>> getAllMisbehavior() {
        return new ResponseEntity<>(misbehaviorService.getAll(), HttpStatus.OK);
    }
}
