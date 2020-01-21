package org.lu.hypervisor;

import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.entity.CourseTrigger;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ObjectInputStream;
import java.util.List;

@Component
public class InitializerBean {
    private SubjectRepo subjectRepo;
    private SubjectStore subjectStore;
    private VectorCache vectorCache;
    private CourseRepo courseRepo;
    private CourseTriggerCache courseTriggerCache;

    @Autowired
    public InitializerBean(SubjectRepo subjectRepo, SubjectStore subjectStore, VectorCache vectorCache, CourseTriggerCache courseTriggerCache, CourseRepo courseRepo) {
        this.subjectRepo = subjectRepo;
        this.subjectStore = subjectStore;
        this.vectorCache = vectorCache;
        this.courseTriggerCache = courseTriggerCache;
        this.courseRepo = courseRepo;
    }

    @PostConstruct
    public void vectorCaching() throws Exception {
        vectorCache.deleteAll();
        List<Subject> subjects = subjectRepo.findAll();
        for (Subject s : subjects) {
            ObjectInputStream ois = new ObjectInputStream(subjectStore.getContent(s));
            Photo photo = (Photo) ois.readObject();
            vectorCache.save(photo.toVector());
        }
    }

    @PostConstruct
    public void courseCaching() {
        courseTriggerCache.deleteAll();
        List<Course> courses = courseRepo.findAll();
        for (Course course : courses) {
            CourseTrigger courseTrigger = new CourseTrigger();
            courseTrigger.setDuration(course.getDuration());
            courseTrigger.setStartTime(course.getTime());
            courseTrigger.setId(course.getId());
        }
    }
}
