package com.vehiclerental.stepdefinitions;

import com.vehiclerental.vehicletype.ElectricVehicle;
import com.vehiclerental.vehicletype.Motorcycle;
import com.vehiclerental.vehicletype.Truck;
import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeSteps {

    private Motorcycle motorcycle;
    private Truck truck;
    private ElectricVehicle electricVehicle;

    private boolean result;

    @Given("a motorcycle exists")
    public void aMotorcycleExists() {

        motorcycle = new Motorcycle(1, "Yamaha", "R1", true);

    }

    @When("the driver age is 20")
    public void theDriverAgeIs20() {

        result = motorcycle.validateAge(20);

    }

    @Then("the motorcycle can be rented")
    public void theMotorcycleCanBeRented() {

        assertTrue(result);

    }

    @Given("a truck exists")
    public void aTruckExists() {

        truck = new Truck(2, "Volvo", "FH16", true);

    }

    @When("the driver has a special license")
    public void theDriverHasASpecialLicense() {

        result = truck.hasSpecialLicense(true);

    }

    @Then("the truck can be rented")
    public void theTruckCanBeRented() {

        assertTrue(result);

    }

    @Given("an electric vehicle exists")
    public void anElectricVehicleExists() {

        electricVehicle =
                new ElectricVehicle(3, "Tesla", "Model 3", true);

    }

    @When("the battery level is 80")
    public void theBatteryLevelIs80() {

        result = electricVehicle.batteryCheck(80);

    }

    @Then("the vehicle is ready for rental")
    public void theVehicleIsReadyForRental() {

        assertTrue(result);

    }

}