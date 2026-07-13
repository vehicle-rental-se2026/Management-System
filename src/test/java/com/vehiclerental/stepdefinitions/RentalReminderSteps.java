package com.vehiclerental.stepdefinitions;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.NotificationService;
import com.vehiclerental.service.RentalService;
import io.cucumber.java.en.*;

import static org.mockito.Mockito.*;

public class RentalReminderSteps {

    private RentalService rentalService;
    private NotificationService notificationService;
    private Vehicle vehicle;

    @Given("a rented vehicle")
    public void aRentedVehicle() {

        notificationService = mock(NotificationService.class);

        rentalService = new RentalService(notificationService);

        vehicle = new Vehicle(
                1,
                "Toyota",
                "Corolla",
                false
        );
    }

    @When("the rental reminder is generated")
    public void theRentalReminderIsGenerated() {

        rentalService.sendRentalReminder(vehicle);

    }

    @Then("a reminder should be sent")
    public void aReminderShouldBeSent() {

        verify(notificationService)
                .sendReminder(anyString());

    }
}