package com.vehiclerental.billing;

import com.vehiclerental.strategy.PricingStrategy;
import com.vehiclerental.strategy.StandardPricingStrategy;
/**
 * The BillingService class is responsible for calculating
 * rental costs, late penalties, and the total payment
 * using the selected pricing strategy.
 */
public class BillingService {

    private PricingStrategy pricingStrategy;

    private static final double LATE_PENALTY_PER_DAY = 15.0;

    public BillingService() {
        this.pricingStrategy = new StandardPricingStrategy();
    }

    public BillingService(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double calculateRentalCost(int rentalDays) {
        return pricingStrategy.calculatePrice(rentalDays);
    }

    public double calculateLatePenalty(int lateDays) {

        if (lateDays <= 0) {
            return 0;
        }

        return lateDays * LATE_PENALTY_PER_DAY;
    }

    public double calculateTotal(int rentalDays, int lateDays) {

        return calculateRentalCost(rentalDays)
                + calculateLatePenalty(lateDays);

    }

}