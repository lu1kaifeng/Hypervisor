package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.*;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.exception.NotFoundException;
import org.lu.hypervisor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Controller
public class CourseApiController implements CourseApi {
    private CourseService courseService;
    private SubjectService subjectService;
    private ClassroomService classroomService;
    private AttendanceService attendanceService;
    private SecurityService securityService;
    private VideoService videoService;

    @Autowired
    public CourseApiController(CourseService courseService, SubjectService subjectService, ClassroomService classroomService, AttendanceService attendanceService, SecurityService securityService, VideoService videoService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.attendanceService = attendanceService;
        this.securityService = securityService;
        this.videoService = videoService;
    }

    @Override
    public ResponseEntity<Void> postCourse(String x_api_key, String name, Long teacherId, String classroom, Short weekday, Short timeHr, Short timeMin, Short durationHr) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        Course course = new Course();
        course.setName(name);
        if (!subjectService.getSubjectById(teacherId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        course.setTeacher(subjectService.getSubjectById(teacherId).get());
        Classroom classroomSearch = new Classroom();
        classroomSearch.setIdentifier(classroom);
        if (!classroomService.findClassroom(classroomSearch).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        course.setClassroom(classroomService.findClassroom(classroomSearch).get());
        course.setDay(DayOfWeek.of(weekday));
        course.setTime(LocalTime.of(timeHr, timeMin));
        course.setDuration(Duration.ofHours(durationHr));
        courseService.newCourse(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delCourse(String x_api_key, Long id) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        Course courseSearch = new Course();
        courseSearch.setId(id);
        if (courseService.getCourse(courseSearch).isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        courseService.delCourse(courseSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Course>> getCourse(String x_api_key, String name, Long teacherId, String classroom, Short weekday, Short timeHr, Short timeMin) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        Course courseSearch = new Course();
        if (name != null) {
            courseSearch.setName(name);
        }
        if (teacherId != null) {
            if (!subjectService.getSubjectById(teacherId).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            courseSearch.setTeacher(subjectService.getSubjectById(teacherId).get());
        }
        if (classroom != null) {
            Classroom classroomSearch = new Classroom();
            classroomSearch.setIdentifier(classroom);
            if (!classroomService.findClassroom(classroomSearch).isPresent())
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            courseSearch.setClassroom(classroomService.findClassroom(classroomSearch).get());
        }
        if (weekday != null) {
            courseSearch.setDay(DayOfWeek.of(weekday));
        }
        if (timeHr != null && timeMin != null) {
            courseSearch.setTime(LocalTime.of(timeHr, timeMin));
        }
        if (courseService.getCourse(courseSearch).isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(courseService.getCourse(courseSearch), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> attendCourse(String x_api_key, Long courseId) throws NotAuthorizedException {
        Subject subject = securityService.tokenVerify(x_api_key);
        if (!courseService.getCourseById(courseId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        attendanceService.attendCourse(subject, courseService.getCourseById(courseId).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> leaveCourse(String x_api_key, Long courseId) throws NotAuthorizedException {
        Subject subject = securityService.tokenVerify(x_api_key);
        if (!courseService.getCourseById(courseId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        attendanceService.leaveCourse(subject, courseService.getCourseById(courseId).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Attendance>> getAttendanceBySubjectId(String x_api_key) throws NotAuthorizedException {
        Subject subject = securityService.tokenVerify(x_api_key);
        List<Attendance> attendances = attendanceService.getAttendance(subject);
        if (attendances.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Course>> getAllCourse(String x_api_key) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        return new ResponseEntity<>(courseService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> postVideo(String x_api_key, Long courseId, String title) throws NotAuthorizedException, NotFoundException {
        securityService.tokenVerify(x_api_key);
        Video video = new Video();
        video.setTitle(title);
        return ResponseEntity.ok(this.videoService.save(courseService.getCourseById(courseId).orElseThrow(NotFoundException::new),video));
    }

    @Override
    public ResponseEntity<List<Video>> getVideo(String x_api_key, Long courseId) throws NotAuthorizedException, NotFoundException {
        securityService.tokenVerify(x_api_key);
        return ResponseEntity.ok(courseService.getCourseById(courseId).orElseThrow(NotFoundException::new).getCourseVideo());
    }
}
