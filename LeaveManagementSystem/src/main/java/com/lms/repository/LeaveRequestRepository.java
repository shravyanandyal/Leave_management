package com.lms.repository;

import com.lms.model.LeaveRequest;
import com.lms.model.User;
import com.lms.model.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    // Get all leaves by user
    List<LeaveRequest> findByUser(User user);

    // Get all leaves by user and status
    List<LeaveRequest> findByUserAndStatus(User user, LeaveStatus status);

    // Get all pending leaves under a manager (for approval dashboard)
    List<LeaveRequest> findByApprovedBy(User manager);
    
    List<LeaveRequest> findByUser_UserId(Long userId);
    
    List<LeaveRequest> findByManagerIdAndStatus_StatusName(Long managerId, String statusName);
    List<LeaveRequest> findByManagerIdAndStatus_StatusNameIn(Long managerId, List<String> statusNames);
}
