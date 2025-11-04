package com.lms.repository;

import com.lms.model.LeavePolicy;
import com.lms.model.Role;
import com.lms.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long> {

    // Fetch policies for a specific role
    List<LeavePolicy> findByRole(Role role);

    // Fetch policy for a specific role and leave type
    LeavePolicy findByRoleAndLeaveType(Role role, LeaveType leaveType);
}
