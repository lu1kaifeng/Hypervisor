package org.lu.hypervisor.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Vector")
public class VectorEntry implements Serializable {
    private Long id;
    private double[] faceVec;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double[] getFaceVec() {
        return faceVec;
    }

    public void setFaceVec(double[] faceVec) {
        this.faceVec = faceVec;
    }
}
