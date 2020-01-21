package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.service.CourseService;
import org.lu.hypervisor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class SubjectApiController implements SubjectApi {
    private SubjectService subjectService;
    private CourseService courseService;

    @Autowired
    public SubjectApiController(SubjectService subjectService, CourseService courseService) {
        this.subjectService = subjectService;
        this.courseService = courseService;
    }

    @Override
    public ResponseEntity<Void> postSubject(String name, String role, String photoBase64) throws IOException {
        Subject subject = new Subject();
        Photo photo = new Photo();
        subject.setName(name);
        subject.setRole(role);
        photo.setPhotoBase64(photoBase64);
        this.subjectService.newSubject(subject, photo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> getId(String photoBase64) {
        Photo photo = new Photo();
        photo.setPhotoBase64(photoBase64);
        return new ResponseEntity<>(subjectService.identify(photo), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> postSignIn(String photoBase64, Long courseId) {
        if (!courseService.getCourseById(courseId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Subject subject = subjectService.signIn(photoBase64);
        if (subject == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postEngagement(String photoBase64) {
        subjectService.studentEngagement(photoBase64);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
