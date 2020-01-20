package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface CourseApi {
    @RequestMapping(method = RequestMethod.POST, value = "/course")
    ResponseEntity<Void> postCourse(@RequestParam String name, @RequestParam Long teacherId, @RequestParam String classroom, @RequestParam Short weekday, @RequestParam Short timeHr, @RequestParam Short timeMin, @RequestParam Short durationHr);

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/{id}")
    ResponseEntity<Void> delCourse(@PathVariable("id") Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/course")
    ResponseEntity<Course> getCourse(@RequestParam(required = false) String name, @RequestParam(required = false) Long teacherId, @RequestParam(required = false) String classroom, @RequestParam(required = false) Short weekday, @RequestParam(required = false) Short timeHr, @RequestParam(required = false) Short timeMin);

    @RequestMapping(method = RequestMethod.POST, value = "/course/attendance")
    ResponseEntity<Void> attendCourse(@RequestParam Long studentId, @RequestParam Long courseId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/attendance")
    ResponseEntity<Void> leaveCourse(@RequestParam Long studentId, @RequestParam Long courseId);
}
