package com.vehiclerental.tests;

import com.vehiclerental.service.DateService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class DateServiceTest {

    @Test
    void testCurrentDateMock() {

        DateService dateService = mock(DateService.class);

        when(dateService.getCurrentDate())
                .thenReturn(LocalDate.of(2025, 7, 1));

        assertEquals(
                LocalDate.of(2025, 7, 1),
                dateService.getCurrentDate()
        );

    }

}