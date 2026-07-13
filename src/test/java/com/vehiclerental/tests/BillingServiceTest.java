package com.vehiclerental.tests;

import com.vehiclerental.billing.BillingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BillingServiceTest {

    private BillingService billingService;

    @BeforeEach
    void setUp() {
        billingService = new BillingService();
    }

    @Test
    void testCalculateRentalCost() {

        double total = billingService.calculateRentalCost(5);

        assertEquals(232.0, total);

    }

    @Test
    void testCalculateLatePenalty() {

        double penalty = billingService.calculateLatePenalty(2);

        assertEquals(30.0, penalty);

    }

    @Test
    void testNoLatePenalty() {

        double penalty = billingService.calculateLatePenalty(0);

        assertEquals(0.0, penalty);

    }

    @Test
    void testCalculateTotal() {

        double total = billingService.calculateTotal(5, 2);

        assertEquals(262.0, total);

    }

}