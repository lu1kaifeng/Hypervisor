package org.lu.hypervisor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "faceNet", url = "${faceNet.url}")
public interface FaceNetClient {
    @PostMapping(value = "/faceNet", produces = "application/json")
    double[] fetchFaceVector(String faceImgBase64);
}
