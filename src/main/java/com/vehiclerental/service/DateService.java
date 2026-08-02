package com.vehiclerental.service;

import java.time.LocalDate;
import java.time.ZoneId;

/**
 * The DateService class provides date-related
 * operations used by the rental system.
 */
public class DateService {

    public LocalDate getCurrentDate() {
        return LocalDate.now(ZoneId.systemDefault());
    }

}