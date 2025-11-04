package com.lms.repository;

import com.lms.model.MandatoryHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface MandatoryHolidayRepository extends JpaRepository<MandatoryHoliday, Long> {

    // Check if a given date is a holiday
    boolean existsByHolidayDate(LocalDate date);
}
