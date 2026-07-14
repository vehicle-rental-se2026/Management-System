package com.vehiclerental.billing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingServiceTest {

    @Test
    public void testCalculateRentalCost() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateRentalCost(5);

        assertEquals(250.0, result);

    }

    @Test
    public void testCalculateRentalCostOneDay() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateRentalCost(1);

        assertEquals(50.0, result);

    }

    @Test
    public void testCalculateLatePenalty() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(3);

        assertEquals(60.0, result);

    }

    @Test
    public void testCalculateLatePenaltyZeroDays() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(0);

        assertEquals(0.0, result);

    }

    @Test
    public void testCalculateLatePenaltyNegativeDays() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(-2);

        assertEquals(0.0, result);

    }

}