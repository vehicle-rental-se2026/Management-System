package com.vehiclerental.tests;
import com.vehiclerental.billing.BillingService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class BillingServiceTest {

    @Test
void testCalculateRentalCost() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateRentalCost(5);

        assertEquals(232.0, result);

    }

    @Test
   void testCalculateRentalCostOneDay() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateRentalCost(1);

        assertEquals(46.4, result);

    }

    @Test
   void testCalculateLatePenalty() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(3);

        assertEquals(45.0, result);

    }

    @Test
void testCalculateLatePenaltyZeroDays() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(0);

        assertEquals(0.0, result);

    }

    @Test
    void testCalculateLatePenaltyNegativeDays() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateLatePenalty(-2);

        assertEquals(0.0, result);

    }

    @Test
  void testCalculateTotal() {

        BillingService billingService = new BillingService();

        double result = billingService.calculateTotal(5,2);

        assertEquals(262.0, result);

    }

}