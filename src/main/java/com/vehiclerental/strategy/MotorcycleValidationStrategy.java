package com.vehiclerental.strategy;

public class MotorcycleValidationStrategy implements RentalValidationStrategy {

    private final int driverAge;

    public MotorcycleValidationStrategy(int driverAge) {
        this.driverAge = driverAge;
    }

    @Override
    public boolean validate() {
        return driverAge >= 18;
    }

}