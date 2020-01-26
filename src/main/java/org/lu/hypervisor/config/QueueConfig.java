package org.lu.hypervisor.config;

import org.lu.hypervisor.model.CourseShotEntry;
import org.lu.hypervisor.model.Notification;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class QueueConfig {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LinkedBlockingQueue<CourseShotEntry> courseShotEntries() {
        return new LinkedBlockingQueue<>();
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public LinkedBlockingQueue<Notification> notificationQueue() {
        return new LinkedBlockingQueue<>();
    }
}
