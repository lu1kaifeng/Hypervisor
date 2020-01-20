package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Classroom;
import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.service.AttendanceService;
import org.lu.hypervisor.service.ClassroomService;
import org.lu.hypervisor.service.CourseService;
import org.lu.hypervisor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

@Controller
public class CourseApiController implements CourseApi {
    private CourseService courseService;
    private SubjectService subjectService;
    private ClassroomService classroomService;
    private AttendanceService attendanceService;

    @Autowired
    public CourseApiController(CourseService courseService, SubjectService subjectService, ClassroomService classroomService, AttendanceService attendanceService) {
        this.courseService = courseService;
        this.subjectService = subjectService;
        this.classroomService = classroomService;
        this.attendanceService = attendanceService;
    }

    @Override
    public ResponseEntity<Void> postCourse(String name, Long teacherId, String classroom, Short weekday, Short timeHr, Short timeMin, Short durationHr) {
        Course course = new Course();
        course.setName(name);
        if (!subjectService.getSubject(teacherId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        course.setTeacher(subjectService.getSubject(teacherId).get());
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
    public ResponseEntity<Void> delCourse(Long id) {
        Course courseSearch = new Course();
        courseSearch.setId(id);
        if (!courseService.getCourse(courseSearch).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        courseService.delCourse(courseSearch);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Course> getCourse(String name, Long teacherId, String classroom, Short weekday, Short timeHr, Short timeMin) {
        Course courseSearch = new Course();
        if (name != null) {
            courseSearch.setName(name);
        }
        if (teacherId != null) {
            if (!subjectService.getSubject(teacherId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            courseSearch.setTeacher(subjectService.getSubject(teacherId).get());
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
        if (!courseService.getCourse(courseSearch).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(courseService.getCourse(courseSearch).get(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> attendCourse(Long studentId, Long courseId) {
        if (!subjectService.getSubject(studentId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (!courseService.getCourseById(courseId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        attendanceService.attendCourse(subjectService.getSubject(studentId).get(), courseService.getCourseById(courseId).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> leaveCourse(Long studentId, Long courseId) {
        if (!subjectService.getSubject(studentId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (!courseService.getCourseById(courseId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        attendanceService.leaveCourse(subjectService.getSubject(studentId).get(), courseService.getCourseById(courseId).get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
