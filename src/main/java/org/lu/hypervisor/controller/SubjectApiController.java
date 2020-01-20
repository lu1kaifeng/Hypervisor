package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class SubjectApiController implements SubjectApi {
    private SubjectService subjectService;

    @Autowired
    public SubjectApiController(SubjectService subjectService) {
        this.subjectService = subjectService;
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
}
