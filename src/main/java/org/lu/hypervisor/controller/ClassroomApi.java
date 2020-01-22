package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Classroom;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClassroomApi {
    @RequestMapping(method = RequestMethod.POST, value = "/classroom")
    ResponseEntity<Void> postClassroom(@RequestHeader("x-api-key") String x_api_key, @RequestParam String identifier) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.DELETE, value = "/classroom")
    ResponseEntity<Void> delClassroom(@RequestHeader("x-api-key") String x_api_key, @RequestParam String identifier) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/classroom")
    ResponseEntity<List<Classroom>> getAllClassroom(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException;

}
