package com.vehiclerental.stepdefinitions;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class RentalSteps {

    private RentalService rentalService;
    private Vehicle vehicle;
    private boolean result;

    @Given("a vehicle is available")
    public void aVehicleIsAvailable() {

        rentalService = new RentalService(new EmailNotificationService());

        vehicle = new Vehicle(
                1,
                "Toyota",
                "Corolla",
                true
        );
    }

    @Given("a vehicle is already rented")
    public void aVehicleIsAlreadyRented() {

        rentalService = new RentalService(new EmailNotificationService());

        vehicle = new Vehicle(
                1,
                "Toyota",
                "Corolla",
                true
        );

        rentalService.rentVehicle(vehicle, 5);
    }

    @When("the manager rents the vehicle for {int} days")
    public void theManagerRentsTheVehicleForDays(int days) {

        result = rentalService.rentVehicle(vehicle, days);

    }

    @When("the manager tries to rent the same vehicle")
    public void theManagerTriesToRentTheSameVehicle() {

        result = rentalService.rentVehicle(vehicle, 5);

    }

    @Then("the rental should be successful")
    public void theRentalShouldBeSuccessful() {

        assertTrue(result);

    }

    @Then("the vehicle should become unavailable")
    public void theVehicleShouldBecomeUnavailable() {

        assertFalse(vehicle.isAvailable());

    }

    @Then("the rental should be rejected")
    public void theRentalShouldBeRejected() {

        assertFalse(result);

    }

    @Then("the rental should fail")
    public void theRentalShouldFail() {

        assertFalse(result);

    }
}