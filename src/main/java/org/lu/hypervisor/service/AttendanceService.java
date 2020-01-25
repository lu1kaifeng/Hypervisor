package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Attendance;
import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.repo.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {
    private AttendanceRepo attendanceRepo;

    @Autowired
    public AttendanceService(AttendanceRepo attendanceRepo) {
        this.attendanceRepo = attendanceRepo;
    }

    public Attendance attendCourse(Subject student, Course course) {
        Attendance attendance = new Attendance();
        attendance.setAttendee(student);
        attendance.setCourse(course);
        return attendanceRepo.save(attendance);
    }

    public boolean ifAttended(Attendance attendance) {
        return attendanceRepo.findOne(Example.of(attendance)).isPresent();
    }

    public void leaveCourse(Subject student, Course course) {
        Attendance attendance = new Attendance();
        attendance.setAttendee(student);
        attendance.setCourse(course);
        attendanceRepo.delete(attendanceRepo.findOne(Example.of(attendance)).get());
    }

    public List<Attendance> getAttendance(Subject subject) {
        Attendance attendance = new Attendance();
        attendance.setAttendee(subject);
        return attendanceRepo.findAll(Example.of(attendance));
    }
}
