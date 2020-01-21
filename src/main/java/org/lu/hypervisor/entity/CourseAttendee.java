package org.lu.hypervisor.entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("CourseAttendee")
public class CourseAttendee implements Serializable {
    private Long id;
    private Long courseId;
    private Boolean isPresent = Boolean.FALSE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }
}
