package com.lms.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility for date validation and parsing.
 */
public class DateValidator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Validates if the given date string is in a proper format.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses the date string to LocalDate.
     */
    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    /**
     * Ensures startDate is before endDate.
     */
    public static boolean isValidRange(String startDate, String endDate) {
        LocalDate start = parseDate(startDate);
        LocalDate end = parseDate(endDate);
        return !start.isAfter(end);
    }
}
