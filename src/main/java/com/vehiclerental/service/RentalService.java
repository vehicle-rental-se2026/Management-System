package com.vehiclerental.service;

import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.NotificationService;
import com.vehiclerental.repository.RentalRepository;

public class RentalService {

    private final RentalRepository rentalRepository;
    private final NotificationService notificationService;

    public RentalService(NotificationService notificationService) {

        this.rentalRepository = new RentalRepository();
        this.notificationService = notificationService;

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

    public void sendRentalReminder(Vehicle vehicle) {

        notificationService.sendReminder(
                "Rental for " + vehicle.getBrand() + " " + vehicle.getModel() + " is about to expire."
        );

    }

    public void returnVehicle(Vehicle vehicle) {
        vehicle.setAvailable(true);
    }

    public boolean isVehicleRented(Vehicle vehicle) {
        return !vehicle.isAvailable();
    }

}