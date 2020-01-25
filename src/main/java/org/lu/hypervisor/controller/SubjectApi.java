package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.model.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

public interface SubjectApi {
    @RequestMapping(method = RequestMethod.POST, value = "/subject")
    ResponseEntity<Void> postSubject(@RequestParam("name") String name, @RequestParam("role") String role, @RequestBody String photoBase64) throws IOException;

    @RequestMapping(method = RequestMethod.GET, value = "/subject")
    ResponseEntity<Subject> getSubject(@RequestBody String photoBase64);

    @RequestMapping(method = RequestMethod.POST, value = "/subject/signIn")
    ResponseEntity<Subject> postSignIn(@RequestBody String photoBase64, Long courseId);

    @RequestMapping(method = RequestMethod.POST, value = "/subject/engagement")
    ResponseEntity<Void> postEngagement(@RequestBody String photoBase64);

    @RequestMapping(method = RequestMethod.GET, value = "/subject/all")
    ResponseEntity<List<Subject>> getAllSubject(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/subject/logIn")
    ResponseEntity<String> getLogin(@RequestParam String name, @RequestParam String password) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/subject/info")
    ResponseEntity<Subject> getSubjectInfo(@RequestHeader("x-api-key") String apiKey) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/subject/photo")
    ResponseEntity<Photo> getSubjectPhoto(@RequestHeader("x-api-key") String apiKey) throws NotAuthorizedException, IOException, ClassNotFoundException;
}
