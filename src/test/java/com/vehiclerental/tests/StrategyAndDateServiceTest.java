package com.vehiclerental.tests;

import com.vehiclerental.service.DateService;
import com.vehiclerental.strategy.ElectricPricingStrategy;
import com.vehiclerental.strategy.MotorcycleValidationStrategy;
import com.vehiclerental.strategy.StandardRentalValidationStrategy;
import com.vehiclerental.strategy.TruckValidationStrategy;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StrategyAndDateServiceTest {

    @Test
    void testGetCurrentDate() {
        DateService service = new DateService();
        LocalDate today = LocalDate.now();

        assertEquals(today, service.getCurrentDate());
    }

    @Test
    void testElectricPricingStrategy() {
        ElectricPricingStrategy strategy = new ElectricPricingStrategy();

        assertEquals(58.0, strategy.calculatePrice(1), 0.01);
        assertEquals(116.0, strategy.calculatePrice(2), 0.01);
    }

    @Test
    void testMotorcycleValidationValidAge() {
        MotorcycleValidationStrategy strategy =
                new MotorcycleValidationStrategy(20);

        assertTrue(strategy.validate());
    }

    @Test
    void testMotorcycleValidationInvalidAge() {
        MotorcycleValidationStrategy strategy =
                new MotorcycleValidationStrategy(17);

        assertFalse(strategy.validate());
    }

    @Test
    void testStandardRentalValidation() {
        StandardRentalValidationStrategy strategy =
                new StandardRentalValidationStrategy();

        assertTrue(strategy.validate());
    }

    @Test
    void testTruckValidationWithLicense() {
        TruckValidationStrategy strategy =
                new TruckValidationStrategy(true);

        assertTrue(strategy.validate());
    }

    @Test
    void testTruckValidationWithoutLicense() {
        TruckValidationStrategy strategy =
                new TruckValidationStrategy(false);

        assertFalse(strategy.validate());
    }
}