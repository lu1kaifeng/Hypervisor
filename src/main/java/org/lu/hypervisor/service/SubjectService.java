package org.lu.hypervisor.service;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.lu.hypervisor.entity.*;
import org.lu.hypervisor.exception.NoMatchingFaceException;
import org.lu.hypervisor.model.CourseShotEntry;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.repo.CourseAttendeeCache;
import org.lu.hypervisor.repo.SubjectRepo;
import org.lu.hypervisor.repo.SubjectStore;
import org.lu.hypervisor.repo.VectorCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class SubjectService {
    private SubjectRepo subjectRepo;
    private CourseService courseService;
    private SubjectStore subjectStore;
    private VectorCache vectorCache;
    private FaceService faceService;
    private DistanceMeasure distanceCalc;
    private CourseAttendeeCache courseAttendeeCache;
    private MisbehaviorService misbehaviorService;
    private LinkedBlockingQueue<CourseShotEntry> courseShotEntries;
    private double threshold;
    private Short lateArrivalThreshold;

    @Autowired
    public SubjectService(SubjectRepo subjectRepo, SubjectStore subjectStore, VectorCache vectorCache, FaceService faceService, DistanceMeasure distanceCalc, @Qualifier("courseShotEntries") LinkedBlockingQueue<CourseShotEntry> courseShotEntries, CourseAttendeeCache courseAttendeeCache, CourseService courseService, @Lazy MisbehaviorService misbehaviorService, Environment env) {
        this.subjectRepo = subjectRepo;
        this.subjectStore = subjectStore;
        this.vectorCache = vectorCache;
        this.faceService = faceService;
        this.distanceCalc = distanceCalc;
        this.threshold = Double.parseDouble(Objects.requireNonNull(env.getProperty("distance.threshold")));
        this.lateArrivalThreshold = Short.parseShort(Objects.requireNonNull(env.getProperty("lateArrival.threshold")));
        this.courseAttendeeCache = courseAttendeeCache;
        this.courseShotEntries = courseShotEntries;
        this.courseService = courseService;
        this.misbehaviorService = misbehaviorService;
    }

    public Optional<Subject> getSubjectById(Long id) {
        return subjectRepo.findById(id);
    }

    public Optional<Subject> getSubject(Subject subject) {
        return subjectRepo.findOne(Example.of(subject));
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

    public void studentEngagement(String studentPic) {
        String[] colorFace = faceService.faceExtraction(false, studentPic);
        String[] monoFace = faceService.faceExtraction(true, studentPic);
        int i = 0;
        while (i < colorFace.length) {
            Photo photo = new Photo();
            photo.setPhotoBase64(colorFace[i]);
            Subject s = this.identify(photo);
            boolean engaged = faceService.isEngaged(monoFace[i]);
            i++;
            CourseShotEntry courseShotEntry = new CourseShotEntry();
            courseShotEntry.setEngaged(engaged);
            courseShotEntry.setTarget(s);
            courseShotEntries.add(courseShotEntry);
        }
    }

    public Subject signIn(String srcPic) {
        Photo p = new Photo();
        p.setPhotoBase64(srcPic);
        Subject subject = this.identify(p);
        if (!courseAttendeeCache.findById(subject.getId()).isPresent()) return null;
        CourseAttendee courseAttendee = courseAttendeeCache.findById(subject.getId()).get();
        courseAttendee.setPresent(true);
        Course course = courseService.getCourseById(courseAttendee.getId()).get();
        if (LocalTime.now().getMinute() - course.getTime().getMinute() > this.lateArrivalThreshold) {
            Misbehavior misbehavior = new Misbehavior();
            misbehavior.setSubject(subjectRepo.findById(courseAttendee.getId()).get());
            misbehavior.setWhen(LocalDateTime.now());
            misbehavior.setType(BehaviorType.LATE);
            misbehavior.setCourse(course);
            misbehaviorService.newMisbehavior(misbehavior);
        }
        courseAttendeeCache.save(courseAttendee);
        return subject;
    }

    public Photo getPhoto(Subject subject) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(subjectStore.getContent(subject));
        return (Photo) ois.readObject();
    }

    public List<Subject> findAll() {
        return subjectRepo.findAll();
    }
}
