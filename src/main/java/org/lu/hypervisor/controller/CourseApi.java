package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Attendance;
import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.entity.Video;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.exception.NotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CourseApi {
    @RequestMapping(method = RequestMethod.POST, value = "/course")
    ResponseEntity<Void> postCourse(@RequestHeader("x-api-key") String x_api_key, @RequestParam String name, @RequestParam Long teacherId, @RequestParam String classroom, @RequestParam Short weekday, @RequestParam Short timeHr, @RequestParam Short timeMin, @RequestParam Short durationHr) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/{id}")
    ResponseEntity<Void> delCourse(@RequestHeader("x-api-key") String x_api_key, @PathVariable("id") Long id) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/course")
    ResponseEntity<List<Course>> getCourse(@RequestHeader("x-api-key") String x_api_key, @RequestParam(required = false) String name, @RequestParam(required = false) Long teacherId, @RequestParam(required = false) String classroom, @RequestParam(required = false) Short weekday, @RequestParam(required = false) Short timeHr, @RequestParam(required = false) Short timeMin) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.POST, value = "/course/attendance")
    ResponseEntity<Void> attendCourse(@RequestHeader("x-api-key") String x_api_key, @RequestParam Long courseId) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/attendance")
    ResponseEntity<Void> leaveCourse(@RequestHeader("x-api-key") String x_api_key, @RequestParam Long courseId) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/course/attendance")
    ResponseEntity<List<Attendance>> getAttendanceBySubjectId(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.GET, value = "/course/all")
    ResponseEntity<List<Course>> getAllCourse(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException;

    @RequestMapping(method = RequestMethod.POST, value = "/course/{courseId}/video/{title}")
    ResponseEntity<String> postVideo(@RequestHeader("x-api-key") String x_api_key,@PathVariable("courseId") Long courseId,@PathVariable("title") String title) throws NotAuthorizedException, ChangeSetPersister.NotFoundException, NotFoundException;

    @RequestMapping(method = RequestMethod.GET, value = "/course/{courseId}/video")
    ResponseEntity<List<Video>> getVideo(@RequestHeader("x-api-key") String x_api_key, @PathVariable("courseId") Long courseId) throws NotAuthorizedException, NotFoundException;
}
