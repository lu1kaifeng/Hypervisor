package org.lu.hypervisor;

import org.lu.hypervisor.entity.*;
import org.lu.hypervisor.model.CourseShotEntry;
import org.lu.hypervisor.repo.*;
import org.lu.hypervisor.service.ClassroomService;
import org.lu.hypervisor.service.CourseService;
import org.lu.hypervisor.service.SubjectService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class ScheduledTasks {
    private CourseService courseService;
    private ClassroomService classroomService;
    private SubjectService subjectService;
    private AttendanceRepo attendanceRepo;
    private CourseRepo courseRepo;
    private LinkedBlockingQueue<CourseShotEntry> courseShotEntries;
    private CourseTriggerCache courseTriggerCache;
    private CourseAttendeeCache courseAttendeeCache;
    private MisbehaviorRepo misbehaviorRepo;
    private SubjectRepo subjectRepo;
    private Short signInStartXMinBeforeCourse;
    private Short eventTriggeringTimePeriod;

    public ScheduledTasks(@Qualifier("courseShotEntries") LinkedBlockingQueue<CourseShotEntry> courseShotEntries, CourseTriggerCache courseTriggerCache, AttendanceRepo attendanceRepo, CourseAttendeeCache courseAttendeeCache, MisbehaviorRepo misbehaviorRepo, CourseRepo courseRepo, SubjectRepo subjectRepo, Environment env) {
        this.courseShotEntries = courseShotEntries;
        this.courseTriggerCache = courseTriggerCache;
        this.attendanceRepo = attendanceRepo;
        this.courseAttendeeCache = courseAttendeeCache;
        this.misbehaviorRepo = misbehaviorRepo;
        this.courseRepo = courseRepo;
        this.subjectRepo = subjectRepo;
        this.eventTriggeringTimePeriod = Short.parseShort(Objects.requireNonNull(env.getProperty("event.time.period.inMinutes")));
    }

    @Scheduled(fixedDelayString = "${engagement.detection.interval.inMilli}")
    public void courseShotProcessing() {

    }

    @Scheduled(fixedDelayString = "${course.event.interval.inMilli}")
    public void courseEventInvoker() {
        Iterable<CourseTrigger> courseTriggers = courseTriggerCache.findAll();
        for (CourseTrigger courseTrigger : courseTriggers) {
            if (courseTrigger.getStartTime().minusMinutes(eventTriggeringTimePeriod).isBefore(LocalTime.now()) && courseTrigger.getStartTime().isAfter(LocalTime.now()) && !courseTrigger.getCreated() && courseTrigger.getDestroyed()) {
                for (Attendance attendance : attendanceRepo.findAll()) {
                    CourseAttendee courseAttendee = new CourseAttendee();
                    courseAttendee.setId(attendance.getId());
                    courseAttendee.setCourseId(attendance.getCourse().getId());
                    courseAttendeeCache.save(courseAttendee);
                }
                courseTrigger.setCreated(true);
                courseTrigger.setDestroyed(false);
                courseTriggerCache.save(courseTrigger);
            }
            if (courseTrigger.getStartTime().plusHours(courseTrigger.getDuration().toHours()).minusMinutes(eventTriggeringTimePeriod).isBefore(LocalTime.now()) && courseTrigger.getStartTime().plusHours(courseTrigger.getDuration().toHours()).isAfter(LocalTime.now()) && courseTrigger.getCreated() && !courseTrigger.getDestroyed()) {
                for (CourseAttendee courseAttendee : courseAttendeeCache.findAll()) {
                    if (courseTrigger.getId().equals(courseAttendee.getCourseId())) {
                        if (!courseAttendee.getPresent()) {
                            Misbehavior misbehavior = new Misbehavior();
                            misbehavior.setCourse(courseRepo.findById(courseAttendee.getCourseId()).get());
                            misbehavior.setType(BehaviorType.ABSENT);
                            misbehavior.setWhen(LocalDateTime.now());
                            misbehavior.setSubject(subjectRepo.findById(courseAttendee.getId()).get());
                            misbehaviorRepo.save(misbehavior);
                        }
                        courseAttendeeCache.delete(courseAttendee);
                    }
                }
                courseTrigger.setCreated(false);
                courseTrigger.setDestroyed(true);
                courseTriggerCache.save(courseTrigger);
            }
        }
    }
}
