package com.vehiclerental.strategy;
/**
 * The PricingStrategy interface defines the
 * contract for calculating rental prices.
 */
public interface PricingStrategy {

    double calculatePrice(int rentalDays);

}