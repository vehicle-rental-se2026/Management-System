package com.vehiclerental.observer;

import java.util.logging.Logger;

/**
 * The EmailObserver class receives notification
 * messages and displays them as email notifications.
 */
public class EmailObserver implements NotificationObserver {

    private static final Logger LOGGER =
            Logger.getLogger(EmailObserver.class.getName());

    @Override
    public void update(String message) {
        LOGGER.info("Email Notification: " + message);
    }
}