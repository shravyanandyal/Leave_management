package com.lms.repository;

import com.lms.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {
    LeaveStatus findByStatusName(String statusName);
}
