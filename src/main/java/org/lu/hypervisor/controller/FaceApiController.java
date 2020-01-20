package org.lu.hypervisor.controller;

import org.lu.hypervisor.model.FacePair;
import org.lu.hypervisor.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class FaceApiController implements FaceApi {
    private FaceService faceService;

    @Autowired
    public FaceApiController(FaceService faceService) {
        this.faceService = faceService;
    }

    @Override
    public ResponseEntity<String> fetchComparison(FacePair facePair) {
        return new ResponseEntity<>(faceService.compare(facePair), HttpStatus.OK);
    }
}
