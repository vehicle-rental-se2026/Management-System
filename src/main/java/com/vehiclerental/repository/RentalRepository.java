package com.vehiclerental.repository;

import com.vehiclerental.domain.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    private final List<Rental> rentals;

    public RentalRepository() {
        rentals = new ArrayList<>();
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public List<Rental> getAllRentals() {
        return rentals;
    }
}