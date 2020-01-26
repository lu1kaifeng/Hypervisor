package org.lu.hypervisor.controller;

import org.lu.hypervisor.exception.NoNotificationException;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.model.Notification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface NotificationApi {
    @RequestMapping(method = RequestMethod.GET, value = "/notification")
    ResponseEntity<Notification> getNotification(@RequestHeader("x-api-key") String x_api_key) throws NotAuthorizedException, NoNotificationException;
}
