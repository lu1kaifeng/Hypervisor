package org.lu.hypervisor.service;

import org.apache.commons.math3.ml.distance.DistanceMeasure;
import org.lu.hypervisor.client.EngagementRecognitionClient;
import org.lu.hypervisor.client.FaceNetClient;
import org.lu.hypervisor.model.FacePair;
import org.lu.hypervisor.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaceService {
    private EngagementRecognitionClient engagementRecognitionClient;
    private FaceNetClient faceNetClient;
    private DistanceMeasure distanceCalc;

    @Autowired
    public FaceService(FaceNetClient faceNetClient, DistanceMeasure distanceMeasure, EngagementRecognitionClient engagementRecognitionClient) {
        this.faceNetClient = faceNetClient;
        this.distanceCalc = distanceMeasure;
        this.engagementRecognitionClient = engagementRecognitionClient;
    }

    public String compare(FacePair facePair) {
        return Double.toString(this.distanceCalc.compute(faceNetClient.fetchFaceVector(facePair.getFace1Base64()),
                faceNetClient.fetchFaceVector(facePair.getFace2Base64())));
    }

    public String[] faceExtraction(boolean isMonochrome, String srcPic) {
        if (isMonochrome) {
            return this.faceNetClient.facesExtractionMono(srcPic);
        } else {
            return this.faceNetClient.facesExtractionRGB(srcPic);
        }
    }

    public boolean isEngaged(String srcFace) {
        double[] result = engagementRecognitionClient.engagementPrediction(srcFace);
        return !(result[0] > result[1]);
    }

    public double[] computeVector(Photo photo) {
        return faceNetClient.fetchFaceVector(photo.getPhotoBase64());
    }
}
