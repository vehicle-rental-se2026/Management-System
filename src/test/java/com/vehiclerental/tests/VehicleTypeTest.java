package com.vehiclerental.tests;

import com.vehiclerental.vehicletype.Car;
import com.vehiclerental.vehicletype.ElectricVehicle;
import com.vehiclerental.vehicletype.Motorcycle;
import com.vehiclerental.vehicletype.Truck;
import org.junit.jupiter.api.Test;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.enums.VehicleType;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTypeTest {

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
    @Test
    void testVehicleConstructor() {

        Vehicle vehicle = new Vehicle(
                10,
                "Toyota",
                "Corolla",
                VehicleType.CAR,
                true
        );

        assertEquals(10, vehicle.getId());
        assertEquals("Toyota", vehicle.getBrand());
        assertEquals("Corolla", vehicle.getModel());
        assertEquals(VehicleType.CAR, vehicle.getVehicleType());
        assertTrue(vehicle.isAvailable());

    }

    @Test
    void testVehicleSetters() {

        Vehicle vehicle = new Vehicle();

        vehicle.setId(20);
        vehicle.setBrand("Tesla");
        vehicle.setModel("Model S");
        vehicle.setVehicleType(VehicleType.ELECTRIC);
        vehicle.setAvailable(false);

        assertEquals(20, vehicle.getId());
        assertEquals("Tesla", vehicle.getBrand());
        assertEquals("Model S", vehicle.getModel());
        assertEquals(VehicleType.ELECTRIC, vehicle.getVehicleType());
        assertFalse(vehicle.isAvailable());

    }

    @Test
    void testVehicleToString() {

        Vehicle vehicle = new Vehicle(
                7,
                "BMW",
                "X5",
                VehicleType.CAR,
                true
        );

        assertEquals(
                "7 - BMW X5 (CAR)",
                vehicle.toString()
        );

    }

    @Test
    void testDefaultVehicleConstructor() {

        Vehicle vehicle = new Vehicle();

        assertEquals(0, vehicle.getId());
        assertNull(vehicle.getBrand());
        assertNull(vehicle.getModel());
        assertNull(vehicle.getVehicleType());
        assertFalse(vehicle.isAvailable());

    }

}