package com.vehiclerental.strategy;

public class StandardRentalValidationStrategy implements RentalValidationStrategy {

    @Override
    public boolean validate() {
        return true;
    }

}