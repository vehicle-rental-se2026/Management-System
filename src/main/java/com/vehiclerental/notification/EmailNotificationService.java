package com.vehiclerental.notification;

import java.util.logging.Logger;

/**
 * The EmailNotificationService class implements the
 * NotificationService interface to send rental
 * reminder messages via email.
 */
public class EmailNotificationService implements NotificationService {

    private static final Logger LOGGER =
            Logger.getLogger(EmailNotificationService.class.getName());

    @Override
    public void sendReminder(String message) {
        LOGGER.info("Reminder Email: " + message);
    }
}