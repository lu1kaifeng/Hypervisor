package org.lu.hypervisor.controller;

import org.lu.hypervisor.entity.Subject;
import org.lu.hypervisor.exception.NoNotificationException;
import org.lu.hypervisor.exception.NotAuthorizedException;
import org.lu.hypervisor.model.Notification;
import org.lu.hypervisor.service.NotificationService;
import org.lu.hypervisor.service.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationApiController implements NotificationApi {
    private NotificationService notificationService;
    private SecurityService securityService;

    public NotificationApiController(NotificationService notificationService, SecurityService securityService) {
        this.notificationService = notificationService;
        this.securityService = securityService;
    }

    @Override
    public ResponseEntity<Notification> getNotification(String x_api_key) throws NotAuthorizedException, NoNotificationException {
        Subject subject = securityService.tokenVerify(x_api_key);
        if (!subject.getRole().equals("god")) throw new NotAuthorizedException();
        return new ResponseEntity<>(notificationService.getNotification(), HttpStatus.OK);
    }
}
