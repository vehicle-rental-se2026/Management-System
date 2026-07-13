package com.vehiclerental.service;

import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.VehicleRepository;

import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }
    public List<Vehicle> getAvailableVehicles() {

        List<Vehicle> availableVehicles = new ArrayList<>();

        for (Vehicle vehicle : vehicleRepository.getAllVehicles()) {

            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }

        }

        return availableVehicles;
    }

}