package com.vehiclerental.stepdefinitions;

import com.vehiclerental.billing.BillingService;
import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class BillingSteps {

    private RentalService rentalService;
    private BillingService billingService;
    private Rental rental;
    private Vehicle vehicle;

    private double totalCost;
    private double penalty;

    @Given("a rented vehicle exists")
    public void aRentedVehicleExists() {

        rentalService = new RentalService(new EmailNotificationService());
        billingService = new BillingService();

        vehicle = new Vehicle(1, "Toyota", "Corolla", false);

        rental = new Rental(vehicle, 5);

    }

    @When("the manager returns the vehicle")
    public void theManagerReturnsTheVehicle() {

        rentalService.returnVehicle(rental);

    }

    @Then("the vehicle becomes available")
    public void theVehicleBecomesAvailable() {

        assertTrue(vehicle.isAvailable());

    }

    @Then("the rental is closed")
    public void theRentalIsClosed() {

        assertFalse(rental.isActive());

    }

    @Given("a rental for 5 days")
    public void aRentalForFiveDays() {

        billingService = new BillingService();

    }

    @When("the rental cost is calculated")
    public void theRentalCostIsCalculated() {

        totalCost = billingService.calculateRentalCost(5);

    }

    @Then("the total cost should be 232.0")
    public void theTotalCostShouldBe232() {

        assertEquals(232.0, totalCost);

    }

    @Given("a rental is returned 2 days late")
    public void aRentalReturnedTwoDaysLate() {

        billingService = new BillingService();

    }

    @When("the late penalty is calculated")
    public void theLatePenaltyIsCalculated() {

        penalty = billingService.calculateLatePenalty(2);

    }

    @Then("the penalty should be 30.0")
    public void thePenaltyShouldBe30() {

        assertEquals(30.0, penalty);

    }

}