package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private CourseRepo courseRepo;

    @Autowired
    public CourseService(CourseRepo courseRepo) {
        this.courseRepo = courseRepo;
    }

    public Course newCourse(Course course) {
        return courseRepo.save(course);
    }

    public List<Course> getCourse(Course course) {
        return courseRepo.findAll(Example.of(course));
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepo.findById(id);
    }

    public void delCourse(Course course) {
        courseRepo.delete(this.getCourse(course).get(0));
    }

    public List<Course> findAll() {
        return courseRepo.findAll();
    }
}
