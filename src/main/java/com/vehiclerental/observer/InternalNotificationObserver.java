package com.vehiclerental.observer;

public class InternalNotificationObserver implements NotificationObserver {

    @Override
    public void update(String message) {

        System.out.println("Internal Notification: " + message);

    }

}