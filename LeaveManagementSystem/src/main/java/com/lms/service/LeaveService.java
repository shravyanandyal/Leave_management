package com.lms.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.lms.model.LeaveDocument;
import com.lms.model.LeaveRequest;
import com.lms.model.User;

public interface LeaveService {

    // Core leave operations
    LeaveRequest applyLeave(User user, Long leaveTypeId, String startDate, String endDate, String reason);
    LeaveRequest editLeaveRequest(User user, Long requestId, String startDate, String endDate, String reason);
    void cancelLeaveRequest(User user, Long requestId);
    List<LeaveRequest> getLeavesForUser(User user);

    // Balance & policy
    int getCurrentFinancialYear();
    Object getLeaveBalance(User user, Long leaveTypeId, int financialYear);
    Object getPolicies(User user);

    // Documents
    LeaveDocument saveDocument(User user, Long requestId, MultipartFile file);
    LeaveDocument loadDocument(Long documentId, User user);

    // 🟢 Admin report methods
    List<LeaveRequest> getAllLeaveRequests();
    List<LeaveRequest> getLeaveRequestsByUser(Long userId);
    
 // Manager-specific
    List<LeaveRequest> getPendingLeavesForManager(Long managerId);
    List<LeaveRequest> getProcessedLeavesByManager(Long managerId);
    LeaveRequest approveLeave(Long requestId, Long managerId);
    LeaveRequest rejectLeave(Long requestId, String reason);
}
