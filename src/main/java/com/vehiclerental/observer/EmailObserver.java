package com.vehiclerental.observer;

public class EmailObserver implements NotificationObserver {

    @Override
    public void update(String message) {

        System.out.println("Email Notification: " + message);

    }

}