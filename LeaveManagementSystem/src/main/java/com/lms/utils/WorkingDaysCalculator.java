package com.lms.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class to calculate working days between two dates.
 */
public class WorkingDaysCalculator {

    // Example holidays (you can load from DB or config)
    private static final Set<LocalDate> HOLIDAYS = new HashSet<>();

//    static {
//        // Add example holidays
//        HOLIDAYS.add(LocalDate.of(LocalDate.now().getYear(), 1, 26)); // Republic Day
//        HOLIDAYS.add(LocalDate.of(LocalDate.now().getYear(), 8, 15)); // Independence Day
//        HOLIDAYS.add(LocalDate.of(LocalDate.now().getYear(), 10, 2)); // Gandhi Jayanti
//    }

    /**
     * Returns count of working days between two dates (inclusive).
     */
    public static int getWorkingDaysBetween(LocalDate start, LocalDate end) {
        int workingDays = 0;

        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            DayOfWeek day = date.getDayOfWeek();

            // Skip weekends and holidays
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY && !HOLIDAYS.contains(date)) {
                workingDays++;
            }
        }

        return workingDays;
    }
}
