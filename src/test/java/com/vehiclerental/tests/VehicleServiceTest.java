package com.vehiclerental.tests;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleServiceTest {

    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        vehicleService = new VehicleService();
    }

    @Test
    void testGetAvailableVehicles() {

        List<Vehicle> vehicles = vehicleService.getAvailableVehicles();

        assertFalse(vehicles.isEmpty());

        for (Vehicle vehicle : vehicles) {
            assertTrue(vehicle.isAvailable());
        }

    }

}