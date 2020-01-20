package org.lu.hypervisor.controller;

import org.lu.hypervisor.model.FacePair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface FaceApi {
    @RequestMapping(method = RequestMethod.POST, value = "/face/comparison")
    ResponseEntity<String> fetchComparison(@RequestBody FacePair facePair);
}
