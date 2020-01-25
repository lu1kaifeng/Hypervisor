package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MisbehaviorApi {
    @RequestMapping(method = RequestMethod.GET, value = "/misbehavior")
    ResponseEntity<List<Misbehavior>> getMisbehavior(@RequestHeader("x-api-key") String x_api_key, @RequestParam Long SubjectId) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/misbehavior/all")
    ResponseEntity<List<Misbehavior>> getAllMisbehavior(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException;
}
