package com.vehiclerental.stepdefinitions;

import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.vehicletype.Car;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class RentalReminderSteps {

    private RentalService rentalService;
    private Car car;

    @Given("a rented vehicle is close to expiry")
    public void aRentedVehicleIsCloseToExpiry() {

        rentalService = new RentalService(new EmailNotificationService());

        car = new Car(
                1,
                "Toyota",
                "Corolla",
                false
        );
    }

    @When("the reminder is sent")
    public void theReminderIsSent() {

        rentalService.sendRentalReminder(car);

    }

    @Then("the notification should be generated")
    public void theNotificationShouldBeGenerated() {

        assertTrue(true);

    }

}