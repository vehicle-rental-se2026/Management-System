package com.vehiclerental.tests;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalServiceTest {

    private RentalService rentalService;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {

        rentalService = new RentalService();

        vehicle = new Vehicle(
                1,
                "Toyota",
                "Corolla",
                true
        );
    }

    @Test
    void testRentVehicleSuccessfully() {

        boolean result = rentalService.rentVehicle(vehicle, 5);

        assertTrue(result);
        assertFalse(vehicle.isAvailable());

    }

    @Test
    void testPreventDoubleBooking() {

        rentalService.rentVehicle(vehicle, 5);

        boolean result = rentalService.rentVehicle(vehicle, 3);

        assertFalse(result);

    }

    @Test
    void testRejectInvalidRentalDuration() {

        boolean result = rentalService.rentVehicle(vehicle, -1);

        assertFalse(result);

    }

    @Test
    void testReturnVehicle() {

        rentalService.rentVehicle(vehicle, 5);

        rentalService.returnVehicle(vehicle);

        assertTrue(vehicle.isAvailable());

    }
}