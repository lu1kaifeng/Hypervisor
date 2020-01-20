package org.lu.hypervisor;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.model.Photo;
import org.lu.hypervisor.repo.SubjectRepo;
import org.lu.hypervisor.repo.SubjectStore;
import org.lu.hypervisor.repo.VectorCache;
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

    @Autowired
    public InitializerBean(SubjectRepo subjectRepo, SubjectStore subjectStore, VectorCache vectorCache) {
        this.subjectRepo = subjectRepo;
        this.subjectStore = subjectStore;
        this.vectorCache = vectorCache;
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
}
