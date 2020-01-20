package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

public interface SubjectApi {
    @RequestMapping(method = RequestMethod.POST, value = "/subject")
    ResponseEntity<Void> postSubject(@RequestParam("name") String name, @RequestParam("role") String role, @RequestBody String photoBase64) throws IOException;

    @RequestMapping(method = RequestMethod.GET, value = "/subject")
    ResponseEntity<Subject> getId(@RequestBody String photoBase64);
}
