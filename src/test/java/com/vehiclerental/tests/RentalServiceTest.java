package com.vehiclerental.tests;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.notification.EmailNotificationService;
import com.vehiclerental.service.RentalService;
import com.vehiclerental.vehicletype.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {

    private RentalService rentalService;
    private Car car;

    @BeforeEach
    void setUp() {

        rentalService = new RentalService(new EmailNotificationService());

        car = new Car(
                1,
                "Toyota",
                "Corolla",
                true
        );
    }

    @Test
    void testRentVehicleSuccessfully() {

        boolean result = rentalService.rentVehicle(car, 5);

        assertTrue(result);
        assertFalse(car.isAvailable());

    }

    @Test
    void testPreventDoubleBooking() {

        rentalService.rentVehicle(car, 5);

        boolean result = rentalService.rentVehicle(car, 3);

        assertFalse(result);

    }

    @Test
    void testRejectInvalidRentalDuration() {

        boolean result = rentalService.rentVehicle(car, -1);

        assertFalse(result);

    }

    @Test
    void testReturnVehicle() {

        Rental rental = new Rental(car, 5);

        car.setAvailable(false);

        rentalService.returnVehicle(rental);

        assertTrue(car.isAvailable());
        assertFalse(rental.isActive());

    }

}