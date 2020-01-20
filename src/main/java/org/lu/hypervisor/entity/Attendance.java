package org.lu.hypervisor.entity;

import javax.persistence.*;

@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private Course course;
    @OneToOne
    private Subject attendee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Subject getAttendee() {
        return attendee;
    }

    public void setAttendee(Subject attendee) {
        this.attendee = attendee;
    }
}
