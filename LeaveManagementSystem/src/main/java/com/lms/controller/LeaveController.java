package com.lms.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lms.model.LeaveDocument;
import com.lms.model.LeaveRequest;
import com.lms.model.User;
import com.lms.service.LeaveService;
import com.lms.service.LeaveDocumentService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private LeaveDocumentService leaveDocumentService; // ✅ Added this

    @Autowired
    private HttpSession session;

    // 🟢 Apply for Leave
    @PostMapping("/apply")
    public ResponseEntity<?> applyLeave(@RequestParam Long leaveTypeId,
                                        @RequestParam String startDate,
                                        @RequestParam String endDate,
                                        @RequestParam String reason) {

        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        LeaveRequest created = leaveService.applyLeave(user, leaveTypeId, startDate, endDate, reason);

        return ResponseEntity.created(URI.create("/leaves/" + created.getRequestId()))
                .body(Map.of("message", "Leave applied successfully", "requestId", created.getRequestId()));
    }

    // 🟢 View My Leaves
    @GetMapping("/me")
    public ResponseEntity<?> getMyLeaves() {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        List<LeaveRequest> leaves = leaveService.getLeavesForUser(user);
        return ResponseEntity.ok(leaves);
    }

    // 🟢 Edit Leave Request
    @PutMapping("/{requestId}/edit")
    public ResponseEntity<?> editLeave(@PathVariable Long requestId,
                                       @RequestParam String startDate,
                                       @RequestParam String endDate,
                                       @RequestParam String reason) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        LeaveRequest updated = leaveService.editLeaveRequest(user, requestId, startDate, endDate, reason);
        return ResponseEntity.ok(Map.of("message", "Leave request updated", "requestId", updated.getRequestId()));
    }

    // 🟢 Cancel Leave Request
    @PutMapping("/{requestId}/cancel")
    public ResponseEntity<?> cancelLeave(@PathVariable Long requestId) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        leaveService.cancelLeaveRequest(user, requestId);
        return ResponseEntity.ok(Map.of("message", "Leave cancelled", "requestId", requestId));
    }

    // 🟢 Check Leave Balance
    @GetMapping("/balance")
    public ResponseEntity<?> getBalance(@RequestParam Long leaveTypeId,
                                        @RequestParam(required = false) Integer financialYear) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        int fy = (financialYear == null)
                ? leaveService.getCurrentFinancialYear()
                : financialYear;

        return ResponseEntity.ok(leaveService.getLeaveBalance(user, leaveTypeId, fy));
    }

    // 🟢 View Leave Policies
    @GetMapping("/policies")
    public ResponseEntity<?> getPolicies() {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        return ResponseEntity.ok(leaveService.getPolicies(user));
    }

    // 🟢 Upload Supporting Document
    @PostMapping(path = "/{requestId}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadDocument(@PathVariable Long requestId,
                                            @RequestPart("file") MultipartFile file) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please login first.");

        try {
            LeaveDocument savedDoc = leaveDocumentService.uploadDocument(
                    requestId,
                    file.getBytes(),
                    file.getOriginalFilename(),
                    file.getContentType()
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Document uploaded successfully",
                    "documentId", savedDoc.getDocumentId()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // 🟢 View Document
    @GetMapping("/document/{documentId}/view")
    public ResponseEntity<Resource> viewDocument(@PathVariable Long documentId) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        LeaveDocument docRes = leaveDocumentService.getDocument(documentId);

        ByteArrayResource resource = new ByteArrayResource(docRes.getData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(docRes.getContentType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"document-" + docRes.getDocumentId() + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    // 🟢 Download Document
    @GetMapping("/document/{documentId}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long documentId) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        LeaveDocument docRes = leaveDocumentService.getDocument(documentId);

        ByteArrayResource resource = new ByteArrayResource(docRes.getData());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(docRes.getContentType()));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"document-" + docRes.getDocumentId() + "\"");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
