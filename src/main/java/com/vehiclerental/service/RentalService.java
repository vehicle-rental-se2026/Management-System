package com.vehiclerental.service;

import com.vehiclerental.billing.BillingService;
import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.NotificationService;
import com.vehiclerental.repository.RentalRepository;

public class RentalService {

    private final RentalRepository rentalRepository;
    private final NotificationService notificationService;
    private final BillingService billingService;

    public RentalService(NotificationService notificationService) {

        this.rentalRepository = new RentalRepository();
        this.notificationService = notificationService;
        this.billingService = new BillingService();

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

    public void returnVehicle(Rental rental) {

        rental.getVehicle().setAvailable(true);

        rental.setActive(false);

    }

    public double calculateRentalCost(Rental rental) {

        double total = billingService.calculateRentalCost(
                rental.getRentalDays());

        rental.setTotalCost(total);

        return total;
    }

    public double calculateLatePenalty(int lateDays) {

        return billingService.calculateLatePenalty(lateDays);

    }

    public void sendRentalReminder(Vehicle vehicle) {

        notificationService.sendReminder(
                "Rental for " + vehicle.getBrand() + " " +
                        vehicle.getModel() +
                        " is about to expire."
        );

    }

    public boolean isVehicleRented(Vehicle vehicle) {

        return !vehicle.isAvailable();

    }
}