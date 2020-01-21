package org.lu.hypervisor.service;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.entity.VectorEntry;
import org.lu.hypervisor.exception.NoMatchingFaceException;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.repo.SubjectRepo;
import org.lu.hypervisor.repo.SubjectStore;
import org.lu.hypervisor.repo.VectorCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectService {
    private SubjectRepo subjectRepo;
    private SubjectStore subjectStore;
    private VectorCache vectorCache;
    private FaceService faceService;
    private DistanceMeasure distanceCalc;
    private double threshold;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, SubjectStore subjectStore, VectorCache vectorCache, @Lazy FaceService faceService, DistanceMeasure distanceCalc, Environment env) {
        this.subjectRepo = subjectRepo;
        this.subjectStore = subjectStore;
        this.vectorCache = vectorCache;
        this.faceService = faceService;
        this.distanceCalc = distanceCalc;
        this.threshold = Double.parseDouble(Objects.requireNonNull(env.getProperty("distance.threshold")));
    }

    public Optional<Subject> getSubject(Long id) {
        return subjectRepo.findById(id);
    }

    public Subject newSubject(Subject subject, Photo photo) throws IOException {
        photo.setFaceVec(faceService.computeVector(photo));
        subject = subjectRepo.save(subject);
        photo.setId(subject.getId());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(photo);
        oos.flush();
        oos.close();
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        subjectStore.setContent(subject, is);
        subject = subjectRepo.save(subject);
        vectorCache.save(photo.toVector());
        return subject;
    }

    public Subject identify(Photo photo) throws NoMatchingFaceException {
        if (photo.getFaceVec() == null) photo.setFaceVec(faceService.computeVector(photo));
        double[] queryVector = photo.getFaceVec();
        Iterable<VectorEntry> vectorEntries = vectorCache.findAll();
        double minDistance = 4.0;
        Long minId = -1L;
        for (VectorEntry vectorEntry : vectorEntries) {
            double distance = this.distanceCalc.compute(queryVector, vectorEntry.getFaceVec());
            if (distance < minDistance) {
                minDistance = distance;
                minId = vectorEntry.getId();
            }
        }
        if (minDistance > threshold) {
            throw new NoMatchingFaceException();
        }
        return subjectRepo.findById(minId).orElseThrow(NoMatchingFaceException::new);
    }

    public Map<Subject, Boolean> studentEngagement(String studentPic) {
        String[] colorFace = faceService.faceExtraction(false, studentPic);
        String[] monoFace = faceService.faceExtraction(true, studentPic);
        int i = 0;
        Map<Subject, Boolean> faceMap = new HashMap<>();
        while (i < colorFace.length) {
            Photo photo = new Photo();
            photo.setPhotoBase64(colorFace[i]);
            Subject s = this.identify(photo);
            Boolean engaged = faceService.isEngaged(monoFace[i]);
            faceMap.put(s, engaged);
            i++;
        }
        return faceMap;
    }
}
