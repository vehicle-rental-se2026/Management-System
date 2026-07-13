package com.vehiclerental.billing;

public class BillingService {

    private static final double DAILY_RATE = 40.0;
    private static final double TAX_RATE = 0.16;
    private static final double LATE_PENALTY_PER_DAY = 15.0;

    public double calculateRentalCost(int rentalDays) {

        double subtotal = rentalDays * DAILY_RATE;
        double tax = subtotal * TAX_RATE;

        return subtotal + tax;
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