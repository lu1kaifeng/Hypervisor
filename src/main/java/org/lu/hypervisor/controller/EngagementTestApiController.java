package org.lu.hypervisor.controller;

import net.bytebuddy.asm.Advice;
import org.lu.hypervisor.model.CourseShotEntry;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.model.TestCourseShotEntry;
import org.lu.hypervisor.service.FaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EngagementTestApiController implements EngagementTestApi {
    @Autowired
    public EngagementTestApiController(FaceService faceService) {
        this.faceService = faceService;
    }

    private FaceService faceService;

    @Override
    public ResponseEntity<List<Photo>> faceExtraction(String classroomPic) {
        List<Photo> photos = new ArrayList<>();
        for(String pic : faceService.faceExtraction(true,classroomPic)){
            Photo photo = new Photo();
            photo.setPhotoBase64(pic);
            photos.add(photo);
        }
        return new ResponseEntity<>(photos,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TestCourseShotEntry>> engagementAssess(List<Photo> entries) {
        List<TestCourseShotEntry> list = new ArrayList<>();
        for(Photo p : entries){
            TestCourseShotEntry entry = new TestCourseShotEntry();
            entry.setPhoto(p);
            entry.setEngaged(faceService.isEngaged(p.getPhotoBase64()));
            list.add(entry);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
