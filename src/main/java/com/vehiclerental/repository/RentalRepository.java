package com.vehiclerental.repository;

import com.vehiclerental.domain.Rental;

import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    private static final List<Rental> rentals = new ArrayList<>();

    public RentalRepository() {
    }

    public void addRental(Rental rental) {

        rentals.add(rental);

    }

    public List<Rental> getAllRentals() {

        return rentals;

    }

    public void removeRental(Rental rental) {

        rentals.remove(rental);

    }

}