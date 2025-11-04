package com.lms.controller;

import com.lms.model.User;
import com.lms.model.LeaveRequest;
import com.lms.service.UserService;
import com.lms.service.LeaveService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private HttpSession session;

    // 🟢 Verify Admin Access
    private ResponseEntity<?> checkAdminAccess() {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");
        }
        if (!"ADMIN".equalsIgnoreCase(user.getRole().getRoleName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied. Admins only.");
        }
        return null;
    }

    // 🟢 Create User (Admin can create Manager or Employee)
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        ResponseEntity<?> access = checkAdminAccess();
        if (access != null) return access;

        User created = userService.addUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "User created successfully", "userId", created.getId()));
    }

    // 🟢 View All Reports (Manager & Employee)
    @GetMapping("/reports")
    public ResponseEntity<?> viewAllReports() {
        ResponseEntity<?> access = checkAdminAccess();
        if (access != null) return access;

        // Admin should see all leave requests (regardless of user role)
        List<LeaveRequest> allReports = leaveService.getAllLeaveRequests();
        return ResponseEntity.ok(allReports);
    }

    // 🟢 View Reports by User ID (specific employee/manager)
    @GetMapping("/reports/{userId}")
    public ResponseEntity<?> viewReportsByUser(@PathVariable Long userId) {
        ResponseEntity<?> access = checkAdminAccess();
        if (access != null) return access;

        List<LeaveRequest> userReports = leaveService.getLeaveRequestsByUser(userId);
        return ResponseEntity.ok(userReports);
    }
}
