package org.lu.hypervisor.service;

import org.lu.hypervisor.entity.Misbehavior;
import org.lu.hypervisor.exception.NoNotificationException;
import org.lu.hypervisor.model.Notification;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;

@Service
public class NotificationService {
    private LinkedBlockingQueue<Notification> notifications;

    public NotificationService(@Qualifier("notificationQueue") LinkedBlockingQueue<Notification> notifications) {
        this.notifications = notifications;
    }

    public void addNotification(Misbehavior misbehavior) {
        Notification notification = new Notification();
        notification.setMisbehavior(misbehavior);
        notifications.offer(notification);
    }

    public Notification getNotification() throws NoNotificationException {
        Notification notification = notifications.poll();
        if (notification == null) throw new NoNotificationException();
        return notification;
    }
}
