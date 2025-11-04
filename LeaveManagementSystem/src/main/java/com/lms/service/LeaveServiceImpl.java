package com.lms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.model.*;
import com.lms.repository.*;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired private LeaveRequestRepository leaveRequestRepository;
    @Autowired private LeaveTypeRepository leaveTypeRepository;
    @Autowired private LeaveBalanceRepository leaveBalanceRepository;
    @Autowired private LeavePolicyRepository leavePolicyRepository;
    @Autowired private LeaveStatusRepository leaveStatusRepository;
    @Autowired private LeaveDocumentRepository leaveDocumentRepository;
    @Autowired private MandatoryHolidayRepository mandatoryHolidayRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public LeaveRequest applyLeave(User user, Long leaveTypeId, String startDate, String endDate, String reason) {
        LeaveType leaveType = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Invalid leave type"));

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        LeaveRequest request = new LeaveRequest();
        request.setUser(user);
        request.setLeaveType(leaveType);
        request.setStartDate(start);
        request.setEndDate(end);
        request.setReason(reason);
        request.setStatus(leaveStatusRepository.findByStatusName("Pending"));

        return leaveRequestRepository.save(request);
    }

    @Override
    public LeaveRequest editLeaveRequest(User user, Long requestId, String startDate, String endDate, String reason) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (!request.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only edit your own leave requests.");
        }

        request.setStartDate(LocalDate.parse(startDate));
        request.setEndDate(LocalDate.parse(endDate));
        request.setReason(reason);
        request.setStatus(leaveStatusRepository.findByStatusName("Pending"));

        return leaveRequestRepository.save(request);
    }

    @Override
    public void cancelLeaveRequest(User user, Long requestId) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        if (!request.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You can only cancel your own leave requests.");
        }

        request.setStatus(leaveStatusRepository.findByStatusName("Cancelled"));
        leaveRequestRepository.save(request);
    }

    @Override
    public List<LeaveRequest> getLeavesForUser(User user) {
        return leaveRequestRepository.findByUser(user);
    }

    @Override
    public int getCurrentFinancialYear() {
        int year = LocalDate.now().getYear();
        if (LocalDate.now().getMonthValue() < 4) { // financial year starts in April
            year -= 1;
        }
        return year;
    }

    @Override
    public Object getLeaveBalance(User user, Long leaveTypeId, int financialYear) {
        LeaveType type = leaveTypeRepository.findById(leaveTypeId)
                .orElseThrow(() -> new RuntimeException("Leave type not found"));
        LeaveBalance balance = leaveBalanceRepository.findByUserAndLeaveTypeAndFinancialYear(user, type, financialYear);
        return balance;
    }

    @Override
    public Object getPolicies(User user) {
        return leavePolicyRepository.findByRole(user.getRole());
    }

    @Override
    public LeaveDocument saveDocument(User user, Long requestId, MultipartFile file) {
        try {
            LeaveRequest request = leaveRequestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Leave request not found"));

            if (!request.getUser().getId().equals(user.getId())) {
                throw new RuntimeException("Unauthorized access to leave document upload");
            }

            LeaveDocument document = new LeaveDocument();
            document.setLeaveRequest(request);
            document.setFilename(file.getOriginalFilename());
            document.setContentType(file.getContentType());
            document.setData(file.getBytes());

            return leaveDocumentRepository.save(document);

        } catch (IOException e) {
            throw new RuntimeException("Error saving document", e);
        }
    }

    @Override
    public LeaveDocument loadDocument(Long documentId, User user) {
        LeaveDocument document = leaveDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        if (!document.getLeaveRequest().getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to document");
        }

        return document;
    }
    
    @Override
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRequestRepository.findAll();
    }

    @Override
    public List<LeaveRequest> getLeaveRequestsByUser(Long userId) {
        return leaveRequestRepository.findByUser_UserId(userId);
    }
    
    @Override
    public List<LeaveRequest> getPendingLeavesForManager(Long managerId) {
        return leaveRequestRepository.findByManagerIdAndStatus_StatusName(managerId, "Pending");
    }

    @Override
    public List<LeaveRequest> getProcessedLeavesByManager(Long managerId) {
        return leaveRequestRepository.findByManagerIdAndStatus_StatusNameIn(managerId, List.of("Approved", "Rejected"));
    }

    @Override
    public LeaveRequest approveLeave(Long requestId, Long managerId) {
        LeaveRequest request = leaveRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(leaveStatusRepository.findByStatusName("Approved"));
        request.setApprovedBy(userRepository.findById(managerId).orElse(null));
        request.setApprovalDate(java.time.LocalDateTime.now());
        // Update leave balance logic here
        return leaveRequestRepository.save(request);
    }

    @Override
    public LeaveRequest rejectLeave(Long requestId, String reason) {
        LeaveRequest request = leaveRequestRepository.findById(requestId).orElseThrow();
        request.setStatus(leaveStatusRepository.findByStatusName("Rejected"));
        request.setRejectionReason(reason);
        return leaveRequestRepository.save(request);
    }

}
