package com.vehiclerental.strategy;
/**
 * The MotorcycleValidationStrategy class validates
 * rental requests for motorcycles.
 */
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