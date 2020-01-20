package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Classroom;
import org.lu.hypervisor.repo.ClassroomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassroomService {
    private ClassroomRepo classroomRepo;

    @Autowired
    public ClassroomService(ClassroomRepo classroomRepo) {
        this.classroomRepo = classroomRepo;
    }

    public Classroom newClassroom(Classroom classroom) {
        return classroomRepo.save(classroom);
    }

    public void delClassroom(Classroom classroom) {
        classroomRepo.delete(this.findClassroom(classroom).get());
    }

    public Optional<Classroom> findClassroom(Classroom classroom) {
        return classroomRepo.findOne(Example.of(classroom));
    }
}
