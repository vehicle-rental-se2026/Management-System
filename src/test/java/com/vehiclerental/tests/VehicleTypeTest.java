package com.vehiclerental.tests;

import com.vehiclerental.vehicletype.Car;
import com.vehiclerental.vehicletype.ElectricVehicle;
import com.vehiclerental.vehicletype.Motorcycle;
import com.vehiclerental.vehicletype.Truck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTypeTest {

    @Test
    void testMotorcycleAgeValidation() {

        Motorcycle motorcycle = new Motorcycle(1, "Yamaha", "R1", true);

        assertTrue(motorcycle.validateAge(20));
        assertFalse(motorcycle.validateAge(16));

    }

    @Test
    void testTruckLicenseValidation() {

        Truck truck = new Truck(2, "Volvo", "FH16", true);

        assertTrue(truck.hasSpecialLicense(true));
        assertFalse(truck.hasSpecialLicense(false));

    }

    @Test
    void testElectricVehicleBatteryCheck() {

        ElectricVehicle vehicle =
                new ElectricVehicle(3, "Tesla", "Model 3", true);

        assertTrue(vehicle.batteryCheck(80));
        assertFalse(vehicle.batteryCheck(10));

    }

    @Test
    void testCarCreation() {

        Car car = new Car(4, "Toyota", "Corolla", true);

        assertEquals("Toyota", car.getBrand());

    }

}