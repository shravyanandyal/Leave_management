package com.lms.repository;

import com.lms.model.LeaveDocument;
import com.lms.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveDocumentRepository extends JpaRepository<LeaveDocument, Long> {

    // All documents for a given leave request
    List<LeaveDocument> findByLeaveRequest(LeaveRequest leaveRequest);
}
