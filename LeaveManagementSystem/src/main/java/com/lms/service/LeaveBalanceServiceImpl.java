package com.lms.service;

import com.lms.model.*;
import com.lms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private LeaveTypeRepository leaveTypeRepository;

    @Override
    public LeaveBalance getBalanceForUser(Long userId, Long leaveTypeId, int year) {
        User user = userRepository.findById(userId).orElseThrow();
        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId).orElseThrow();
        return leaveBalanceRepository.findByUserAndLeaveTypeAndFinancialYear(user, leaveType, year);
    }

    @Override
    public List<LeaveBalance> getAllBalancesForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return leaveBalanceRepository.findByUser(user);
    }
}
