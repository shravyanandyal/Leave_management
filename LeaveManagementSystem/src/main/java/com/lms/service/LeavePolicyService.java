package com.lms.service;

import com.lms.model.LeavePolicy;
import com.lms.model.Role;
import java.util.List;

public interface LeavePolicyService {
    List<LeavePolicy> getPoliciesByRole(Role role);
}
