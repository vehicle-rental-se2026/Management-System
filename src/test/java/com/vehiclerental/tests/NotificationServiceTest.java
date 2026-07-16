package com.vehiclerental.tests;

import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.notification.NotificationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NotificationServiceTest {

    @Test
    void testSendReminder() {

        NotificationService notificationService =
                new EmailNotificationService();

        assertDoesNotThrow(() ->
                notificationService.sendReminder("Rental expires tomorrow"));

    }

}