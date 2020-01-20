package org.lu.hypervisor.model;

public class FacePair {
    private String face1Base64;
    private String face2Base64;

    public FacePair() {
        this(null, null);
    }

    public FacePair(String face1Base64, String face2Base64) {
        this.face1Base64 = face1Base64;
        this.face2Base64 = face2Base64;
    }

    public String getFace1Base64() {
        return face1Base64;
    }

    public void setFace1Base64(String face1Base64) {
        this.face1Base64 = face1Base64;
    }

    public String getFace2Base64() {
        return face2Base64;
    }

    public void setFace2Base64(String face2Base64) {
        this.face2Base64 = face2Base64;
    }
}
