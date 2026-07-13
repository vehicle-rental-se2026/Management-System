package com.vehiclerental.domain;

public class Rental {

    private Vehicle vehicle;
    private int rentalDays;
    private boolean active;
    private double totalCost;

    public Rental(Vehicle vehicle, int rentalDays) {
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
        this.active = true;
        this.totalCost = 0.0;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }


}