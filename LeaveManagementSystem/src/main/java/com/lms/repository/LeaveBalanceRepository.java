package com.lms.repository;

import com.lms.model.LeaveBalance;
import com.lms.model.LeaveType;
import com.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    // Fetch a user’s balance for a given leave type & year
    LeaveBalance findByUserAndLeaveTypeAndFinancialYear(User user, LeaveType leaveType, int financialYear);

    // Fetch all balances for a user
    List<LeaveBalance> findByUser(User user);
}
