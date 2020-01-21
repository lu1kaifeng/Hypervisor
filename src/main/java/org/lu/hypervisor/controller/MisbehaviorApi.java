package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Misbehavior;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MisbehaviorApi {
    @RequestMapping(method = RequestMethod.GET, value = "/misbehavior")
    ResponseEntity<Misbehavior> getMisbehavior(@RequestParam Long SubjectId);

    @RequestMapping(method = RequestMethod.GET, value = "/misbehavior/all")
    ResponseEntity<List<Misbehavior>> getAllMisbehavior();
}
