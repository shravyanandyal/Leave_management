package com.lms.service;

import com.lms.model.LeavePolicy;
import com.lms.model.Role;
import com.lms.repository.LeavePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeavePolicyServiceImpl implements LeavePolicyService {

    @Autowired private LeavePolicyRepository leavePolicyRepository;

    @Override
    public List<LeavePolicy> getPoliciesByRole(Role role) {
        return leavePolicyRepository.findByRole(role);
    }
}
