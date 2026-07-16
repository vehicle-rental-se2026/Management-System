package com.vehiclerental.strategy;

public class TruckValidationStrategy implements RentalValidationStrategy {

    private final boolean hasSpecialLicense;

    public TruckValidationStrategy(boolean hasSpecialLicense) {
        this.hasSpecialLicense = hasSpecialLicense;
    }

    @Override
    public boolean validate() {
        return hasSpecialLicense;
    }

}