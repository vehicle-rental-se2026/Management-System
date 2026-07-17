package com.vehiclerental.strategy;
/**
 * The StandardPricingStrategy class calculates
 * rental prices using the standard pricing rules.
 */
public class StandardPricingStrategy implements PricingStrategy {

    private static final double DAILY_RATE = 40.0;
    private static final double TAX = 0.16;

    @Override
    public double calculatePrice(int rentalDays) {

        double subtotal = rentalDays * DAILY_RATE;

        return subtotal + (subtotal * TAX);
    }
}