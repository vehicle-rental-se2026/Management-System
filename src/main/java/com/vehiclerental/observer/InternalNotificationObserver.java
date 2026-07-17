package com.vehiclerental.observer;
/**
 * The InternalNotificationObserver class receives
 * notification messages and displays them as
 * internal system notifications.
 */
public class InternalNotificationObserver implements NotificationObserver {

    @Override
    public void update(String message) {

        System.out.println("Internal Notification: " + message);

    }

}