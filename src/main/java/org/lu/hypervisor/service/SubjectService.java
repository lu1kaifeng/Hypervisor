package org.lu.hypervisor.service;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.exception.NoMatchingFaceException;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.model.VectorEntry;
import org.lu.hypervisor.repo.SubjectRepo;
import org.lu.hypervisor.repo.SubjectStore;
import org.lu.hypervisor.repo.VectorCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Objects;
import java.util.function.Supplier;

@Service
public class SubjectService {
    private SubjectRepo subjectRepo;
    private SubjectStore subjectStore;
    private VectorCache vectorCache;
    private FaceService faceService;
    private DistanceMeasure distanceCalc;
    private double threshold;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, SubjectStore subjectStore, VectorCache vectorCache, FaceService faceService, DistanceMeasure distanceCalc, Environment env) {
        this.subjectRepo = subjectRepo;
        this.subjectStore = subjectStore;
        this.vectorCache = vectorCache;
        this.faceService = faceService;
        this.distanceCalc = distanceCalc;
        this.threshold = Double.parseDouble(Objects.requireNonNull(env.getProperty("distance.threshold")));
        ;
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
        return subjectRepo.findById(minId).orElseThrow(new Supplier<NoMatchingFaceException>() {
            @Override
            public NoMatchingFaceException get() {
                return new NoMatchingFaceException();
            }
        });
    }
}
