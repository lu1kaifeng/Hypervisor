package org.lu.hypervisor.model;

import org.lu.hypervisor.entity.VectorEntry;

import java.io.Serializable;

public class Photo implements Serializable {
    private Long id;
    private String photoBase64;
    private double[] faceVec;

    public String getPhotoBase64() {
        return photoBase64;
    }

    public void setPhotoBase64(String photoBase64) {
        this.photoBase64 = photoBase64;
    }

    public double[] getFaceVec() {
        return faceVec;
    }

    public void setFaceVec(double[] faceVec) {
        this.faceVec = faceVec;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VectorEntry toVector() {
        VectorEntry v = new VectorEntry();
        v.setFaceVec(this.faceVec);
        v.setId(this.id);
        return v;
    }
}
