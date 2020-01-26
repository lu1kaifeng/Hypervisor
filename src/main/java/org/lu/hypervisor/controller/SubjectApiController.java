package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.service.CourseService;
import org.lu.hypervisor.service.SecurityService;
import org.lu.hypervisor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class SubjectApiController implements SubjectApi {
    private SubjectService subjectService;
    private CourseService courseService;
    private SecurityService securityService;

    @Autowired
    public SubjectApiController(SubjectService subjectService, CourseService courseService, SecurityService securityService) {
        this.subjectService = subjectService;
        this.courseService = courseService;
        this.securityService = securityService;
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
    public ResponseEntity<Subject> getSubject(String photoBase64) {
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

    @Override
    public ResponseEntity<List<Subject>> getAllSubject(String x_api_key) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        return new ResponseEntity<>(subjectService.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getLogin(String name, String password) throws NotAuthorizedException {
        Subject subject = new Subject();
        subject.setName(name);
        subject.setPassword(password);
        if (!subjectService.getSubject(subject).isPresent()) throw new NotAuthorizedException();
        return new ResponseEntity<>(securityService.tokenCreate(subjectService.getSubject(subject).get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Subject> getSubjectInfo(String apiKey) throws NotAuthorizedException {
        return new ResponseEntity<>(securityService.tokenVerify(apiKey), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Photo> getSubjectPhoto(String apiKey) throws NotAuthorizedException, IOException, ClassNotFoundException {
        return new ResponseEntity<>(subjectService.getPhoto(securityService.tokenVerify(apiKey)), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Photo> getSubjectPhotoById(String apiKey, Long subjectId) throws NotAuthorizedException, IOException, ClassNotFoundException {
        if (!subjectService.getSubjectById(subjectId).isPresent()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Subject subject = subjectService.getSubjectById(subjectId).get();
        return new ResponseEntity<>(subjectService.getPhoto(subject), HttpStatus.OK);
    }
}
