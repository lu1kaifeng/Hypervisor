package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Classroom;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.service.ClassroomService;
import org.lu.hypervisor.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClassroomApiController implements ClassroomApi {
    private ClassroomService classroomService;
    private SecurityService securityService;

    @Autowired
    public ClassroomApiController(ClassroomService classroomService, SecurityService securityService) {
        this.classroomService = classroomService;
        this.securityService = securityService;
    }

    @Override
    public ResponseEntity<Void> postClassroom(String x_api_key, String identifier) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        Classroom classroom = new Classroom();
        classroom.setIdentifier(identifier);
        classroomService.newClassroom(classroom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delClassroom(String x_api_key, String identifier) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        Classroom classroom = new Classroom();
        classroom.setIdentifier(identifier);
        if (!classroomService.findClassroom(classroom).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        classroomService.delClassroom(classroom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Classroom>> getAllClassroom(String x_api_key) throws NotAuthorizedException {
        securityService.tokenVerify(x_api_key);
        return new ResponseEntity<>(classroomService.findAll(), HttpStatus.OK);
    }
}
