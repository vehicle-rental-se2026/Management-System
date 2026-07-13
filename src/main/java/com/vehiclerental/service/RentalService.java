package com.vehiclerental.service;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.repository.RentalRepository;

public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService() {
        rentalRepository = new RentalRepository();
    }

    public boolean rentVehicle(Vehicle vehicle, int rentalDays) {

        if (rentalDays <= 0 || rentalDays > 30) {
            return false;
        }

        if (!vehicle.isAvailable()) {
            return false;
        }

        Rental rental = new Rental(vehicle, rentalDays);

        rentalRepository.addRental(rental);

        vehicle.setAvailable(false);

        return true;
    }

    public void returnVehicle(Vehicle vehicle) {
        vehicle.setAvailable(true);
    }

    public boolean isVehicleRented(Vehicle vehicle) {
        return !vehicle.isAvailable();
    }
}