package org.lu.hypervisor.service;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.lu.hypervisor.client.FaceNetClient;
import org.lu.hypervisor.model.FacePair;
import org.lu.hypervisor.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceService {
    private FaceNetClient client;
    private DistanceMeasure distanceCalc;

    @Autowired
    public FaceService(FaceNetClient client, DistanceMeasure distanceMeasure) {
        this.client = client;
        this.distanceCalc = distanceMeasure;
    }

    public String compare(FacePair facePair) {
        return Double.toString(this.distanceCalc.compute(client.fetchFaceVector(facePair.getFace1Base64()),
                client.fetchFaceVector(facePair.getFace2Base64())));
    }

    public double[] computeVector(Photo photo) {
        return client.fetchFaceVector(photo.getPhotoBase64());
    }
}
