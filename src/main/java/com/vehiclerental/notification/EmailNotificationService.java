package com.vehiclerental.notification;
/**
 * The EmailNotificationService class implements the
 * NotificationService interface to send rental
 * reminder messages via email.
 */
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendReminder(String message) {

        System.out.println("Reminder Email: " + message);

    }

}