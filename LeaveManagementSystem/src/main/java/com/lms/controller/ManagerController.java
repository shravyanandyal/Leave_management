package com.lms.controller;

import com.lms.model.LeaveRequest;
import com.lms.model.User;
import com.lms.service.LeaveService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private HttpSession session;

    // 🟢 Verify Manager Access
    private ResponseEntity<?> checkManagerAccess() {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");
        }
        if (!"MANAGER".equalsIgnoreCase(user.getRole().getRoleName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Managers only.");
        }
        return null;
    }

    // 🟢 View All Pending Leave Requests (from team)
    @GetMapping("/pending-requests")
    public ResponseEntity<?> viewPendingLeaves() {
        ResponseEntity<?> access = checkManagerAccess();
        if (access != null) return access;

        User manager = (User) session.getAttribute("loggedUser");
        List<LeaveRequest> pendingRequests = leaveService.getPendingLeavesForManager(manager.getId());
        return ResponseEntity.ok(pendingRequests);
    }

    // 🟢 Approve a Leave Request
    @PutMapping("/{requestId}/approve")
    public ResponseEntity<?> approveLeave(@PathVariable Long requestId) {
        ResponseEntity<?> access = checkManagerAccess();
        if (access != null) return access;

        User manager = (User) session.getAttribute("loggedUser");
        LeaveRequest approved = leaveService.approveLeave(requestId, manager.getId());
        return ResponseEntity.ok(Map.of(
                "message", "Leave approved successfully",
                "requestId", approved.getRequestId(),
                "status", approved.getStatus().getStatusName()
        ));
    }

    // 🟢 Reject a Leave Request
    @PutMapping("/{requestId}/reject")
    public ResponseEntity<?> rejectLeave(@PathVariable Long requestId,
                                         @RequestParam String reason) {
        ResponseEntity<?> access = checkManagerAccess();
        if (access != null) return access;

        LeaveRequest rejected = leaveService.rejectLeave(requestId, reason);
        return ResponseEntity.ok(Map.of(
                "message", "Leave rejected successfully",
                "requestId", rejected.getRequestId(),
                "status", rejected.getStatus().getStatusName(),
                "reason", rejected.getRejectionReason()
        ));
    }

    // 🟢 View All Processed Requests (Approved/Rejected)
    @GetMapping("/processed-requests")
    public ResponseEntity<?> viewProcessedRequests() {
        ResponseEntity<?> access = checkManagerAccess();
        if (access != null) return access;

        User manager = (User) session.getAttribute("loggedUser");
        List<LeaveRequest> processed = leaveService.getProcessedLeavesByManager(manager.getId());
        return ResponseEntity.ok(processed);
    }
}
