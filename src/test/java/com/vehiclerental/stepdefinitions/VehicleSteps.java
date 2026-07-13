package com.vehiclerental.stepdefinitions;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.service.VehicleService;
import io.cucumber.java.en.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleSteps {

    private final VehicleService vehicleService = new VehicleService();

    private List<Vehicle> vehicles;

    @Given("the vehicle list exists")
    public void theVehicleListExists() {
    }

    @When("the manager views available vehicles")
    public void theManagerViewsAvailableVehicles() {
        vehicles = vehicleService.getAvailableVehicles();
    }

    @Then("only available vehicles should be displayed")
    public void onlyAvailableVehiclesShouldBeDisplayed() {

        assertFalse(vehicles.isEmpty());

        for (Vehicle vehicle : vehicles) {
            assertTrue(vehicle.isAvailable());
        }
    }
}