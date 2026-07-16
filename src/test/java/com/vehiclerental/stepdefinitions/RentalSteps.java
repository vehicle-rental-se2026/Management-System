package com.vehiclerental.stepdefinitions;

import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.vehicletype.Car;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class RentalSteps {

    private RentalService rentalService;
    private Car car;
    private boolean result;

    @Given("an available vehicle exists")
    public void anAvailableVehicleExists() {

        rentalService = new RentalService(new EmailNotificationService());

        car = new Car(
                1,
                "Toyota",
                "Corolla",
                true
        );

    }

    @When("the manager rents the vehicle for {int} days")
    public void theManagerRentsTheVehicleForDays(Integer days) {

        result = rentalService.rentVehicle(car, days);

    }

    @Then("the rental should be successful")
    public void theRentalShouldBeSuccessful() {

        assertTrue(result);

    }

    @Then("the vehicle should become unavailable")
    public void theVehicleShouldBecomeUnavailable() {

        assertFalse(car.isAvailable());

    }

    @Then("the rental should be rejected")
    public void theRentalShouldBeRejected() {

        assertFalse(result);

    }

}