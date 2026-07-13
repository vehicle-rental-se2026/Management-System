package com.vehiclerental.tests;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.NotificationService;
import com.vehiclerental.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NotificationServiceTest {

    private NotificationService notificationService;
    private RentalService rentalService;

    @BeforeEach
    void setUp() {

        notificationService = mock(NotificationService.class);

        rentalService = new RentalService(notificationService);

    }

    @Test
    void testReminderIsSent() {

        Vehicle vehicle = new Vehicle(
                1,
                "Toyota",
                "Corolla",
                true
        );

        rentalService.sendRentalReminder(vehicle);

        verify(notificationService)
                .sendReminder(anyString());

    }

}