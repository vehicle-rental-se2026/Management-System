package com.vehiclerental.observer;

import java.util.logging.Logger;

/**
 * The InternalNotificationObserver class receives
 * notification messages and displays them as
 * internal system notifications.
 */
public class InternalNotificationObserver implements NotificationObserver {

    private static final Logger LOGGER =
            Logger.getLogger(InternalNotificationObserver.class.getName());

    @Override
    public void update(String message) {
        LOGGER.info("Internal Notification: " + message);
    }
}