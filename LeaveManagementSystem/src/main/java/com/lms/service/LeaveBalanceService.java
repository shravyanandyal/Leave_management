package com.lms.service;

import com.lms.model.LeaveBalance;
import com.lms.model.User;
import java.util.List;

public interface LeaveBalanceService {
    LeaveBalance getBalanceForUser(Long userId, Long leaveTypeId, int year);
    List<LeaveBalance> getAllBalancesForUser(Long userId);
}
