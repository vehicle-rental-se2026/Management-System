package com.vehiclerental.notification;

public class EmailNotificationService implements NotificationService {

    @Override
    public void sendReminder(String message) {

        System.out.println("Reminder Email: " + message);

    }

}