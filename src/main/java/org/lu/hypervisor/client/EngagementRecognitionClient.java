package org.lu.hypervisor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "engagement", url = "${engagement.url}")
public interface EngagementRecognitionClient {
    @PostMapping(value = "/faceNet")
    double[] engagementPrediction(@RequestBody String inputBase64);
}
