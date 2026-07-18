package com.vehiclerental.strategy;
/**
 * The ElectricPricingStrategy class calculates
 * rental prices for electric vehicles.
 */
public class ElectricPricingStrategy implements PricingStrategy {

    private static final double DAILY_RATE = 50.0;
    private static final double TAX = 0.16;

    @Override
    public double calculatePrice(int rentalDays) {

        double subtotal = rentalDays * DAILY_RATE;

        return subtotal + (subtotal * TAX);
    }
}