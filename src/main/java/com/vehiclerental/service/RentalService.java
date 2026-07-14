package com.vehiclerental.service;

import com.vehiclerental.billing.BillingService;
import com.vehiclerental.domain.Rental;
import com.vehiclerental.domain.Vehicle;
import com.vehiclerental.notification.NotificationService;
import com.vehiclerental.observer.EmailObserver;
import com.vehiclerental.observer.InternalNotificationObserver;
import com.vehiclerental.observer.NotificationManager;
import com.vehiclerental.repository.RentalRepository;

import java.util.List;

public class RentalService {

    private final RentalRepository rentalRepository;
    private final NotificationService notificationService;
    private final BillingService billingService;
    private final NotificationManager notificationManager;

    public RentalService(NotificationService notificationService) {

        this.notificationService = notificationService;
        this.rentalRepository = new RentalRepository();
        this.billingService = new BillingService();

        this.notificationManager = new NotificationManager();

        notificationManager.addObserver(new EmailObserver());
        notificationManager.addObserver(new InternalNotificationObserver());

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

        notificationManager.notifyObservers(
                "Vehicle rented successfully."
        );

        return true;
    }

    public void returnVehicle(Rental rental) {

        rental.getVehicle().setAvailable(true);

        rental.setActive(false);

        notificationManager.notifyObservers(
                "Vehicle returned successfully."
        );

    }

    public double calculateRentalCost(Rental rental) {

        double total =
                billingService.calculateRentalCost(
                        rental.getRentalDays());

        rental.setTotalCost(total);

        return total;
    }

    public double calculateLatePenalty(int lateDays) {

        return billingService.calculateLatePenalty(lateDays);

    }

    public void sendRentalReminder(Vehicle vehicle) {

        String message =
                "Rental for "
                        + vehicle.getBrand()
                        + " "
                        + vehicle.getModel()
                        + " is about to expire.";

        notificationService.sendReminder(message);

        notificationManager.notifyObservers(message);

    }

    public boolean isVehicleRented(Vehicle vehicle) {

        return !vehicle.isAvailable();

    }
    public List<Rental> getActiveRentals() {

        return rentalRepository
                .getAllRentals()
                .stream()
                .filter(Rental::isActive)
                .toList();

    }

}