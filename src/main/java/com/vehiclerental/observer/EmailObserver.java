package com.vehiclerental.observer;
/**
 * The EmailObserver class receives notification
 * messages and displays them as email notifications.
 */
public class EmailObserver implements NotificationObserver {

    @Override
    public void update(String message) {

        System.out.println("Email Notification: " + message);

    }

}