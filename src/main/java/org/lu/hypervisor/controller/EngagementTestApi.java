package org.lu.hypervisor.controller;

import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.model.TestCourseShotEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public interface EngagementTestApi {
    @RequestMapping(method = RequestMethod.POST, value = "/face/extraction")
    ResponseEntity<List<Photo>> faceExtraction(@RequestBody String classroomPic);

    @RequestMapping(method = RequestMethod.POST, value = "/engagement/detect")
    ResponseEntity<List<TestCourseShotEntry>> engagementAssess(@RequestBody List<Photo> entries);
}
