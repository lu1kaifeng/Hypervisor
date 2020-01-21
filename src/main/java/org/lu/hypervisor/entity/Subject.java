package org.lu.hypervisor.entity;

import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String role;
    private Long numEngagement;
    private Long numDisengagement;
    @ContentId
    private String photoId;
    @ContentLength
    private Long photoLength;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Long getPhotoLength() {
        return photoLength;
    }

    public void setPhotoLength(Long photoLength) {
        this.photoLength = photoLength;
    }

    public Long getNumEngagement() {
        return numEngagement;
    }

    public void setNumEngagement(Long numEngagement) {
        this.numEngagement = numEngagement;
    }

    public Long getNumDisengagement() {
        return numDisengagement;
    }

    public void setNumDisengagement(Long numDisengagement) {
        this.numDisengagement = numDisengagement;
    }
}
