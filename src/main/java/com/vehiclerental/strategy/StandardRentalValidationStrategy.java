package com.vehiclerental.strategy;
/**
 * The StandardRentalValidationStrategy class
 * validates rental requests using standard rules.
 */
public class StandardRentalValidationStrategy implements RentalValidationStrategy {

    @Override
    public boolean validate() {
        return true;
    }

}