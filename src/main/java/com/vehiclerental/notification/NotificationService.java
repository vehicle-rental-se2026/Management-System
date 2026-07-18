package com.vehiclerental.notification;
/**
 * The NotificationService interface defines the
 * contract for sending notification messages
 * to users.
 */
public interface NotificationService {

    void sendReminder(String message);

}