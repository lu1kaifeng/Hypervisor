package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.service.MisbehaviorService;
import org.lu.hypervisor.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MisbehaviorApiController implements MisbehaviorApi {
    private MisbehaviorService misbehaviorService;
    private SecurityService securityService;

    @Autowired
    public MisbehaviorApiController(MisbehaviorService misbehaviorService, SecurityService securityService) {
        this.misbehaviorService = misbehaviorService;
        this.securityService = securityService;
    }

    @Override
    public ResponseEntity<Misbehavior> getMisbehavior(String x_api_key, Long subjectId) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        if (misbehaviorService.getMisbehavior(subjectId) != null) {
            return new ResponseEntity<>(misbehaviorService.getMisbehavior(subjectId), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<List<Misbehavior>> getAllMisbehavior(String x_api_key) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        return new ResponseEntity<>(misbehaviorService.getAll(), HttpStatus.OK);
    }
}
