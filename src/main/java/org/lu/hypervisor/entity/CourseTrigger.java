package org.lu.hypervisor.entity;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

@RedisHash("CourseTrigger")
public class CourseTrigger implements Serializable {
    private Long id;
    private LocalTime startTime;
    private Duration duration;
    private Boolean isCreated = Boolean.FALSE;
    private Boolean isDestroyed = Boolean.TRUE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Boolean getCreated() {
        return isCreated;
    }

    public void setCreated(Boolean created) {
        isCreated = created;
    }

    public Boolean getDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(Boolean destroyed) {
        isDestroyed = destroyed;
    }
}
