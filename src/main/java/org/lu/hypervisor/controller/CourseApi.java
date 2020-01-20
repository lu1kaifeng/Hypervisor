package org.lu.hypervisor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface CourseApi {
    @RequestMapping(method = RequestMethod.POST, value = "/course")
    ResponseEntity<Void> postCourse();

    @RequestMapping(method = RequestMethod.DELETE, value = "/course")
    ResponseEntity<Void> delCourse();

    @RequestMapping(method = RequestMethod.POST, value = "/course/attendance")
    ResponseEntity<Void> attendCourse();

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/attendance")
    ResponseEntity<Void> leaveCourse();
}
