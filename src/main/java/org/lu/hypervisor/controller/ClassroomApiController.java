package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Classroom;
import org.lu.hypervisor.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClassroomApiController implements ClassroomApi {
    private ClassroomService classroomService;

    @Autowired
    public ClassroomApiController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @Override
    public ResponseEntity<Void> postClassroom(String identifier) {
        Classroom classroom = new Classroom();
        classroom.setIdentifier(identifier);
        classroomService.newClassroom(classroom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delClassroom(String identifier) {
        Classroom classroom = new Classroom();
        classroom.setIdentifier(identifier);
        if (!classroomService.findClassroom(classroom).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        classroomService.delClassroom(classroom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Classroom>> getAllClassroom() {
        return new ResponseEntity<>(classroomService.findAll(), HttpStatus.OK);
    }
}
