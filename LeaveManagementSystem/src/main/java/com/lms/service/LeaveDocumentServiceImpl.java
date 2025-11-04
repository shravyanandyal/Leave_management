package com.lms.service;

import com.lms.model.LeaveDocument;
import com.lms.model.LeaveRequest;
import com.lms.repository.LeaveDocumentRepository;
import com.lms.repository.LeaveRequestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveDocumentServiceImpl implements LeaveDocumentService {

    @Autowired
    private LeaveDocumentRepository leaveDocumentRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public LeaveDocument uploadDocument(Long requestId, byte[] data, String filename, String contentType) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));

        LeaveDocument document = new LeaveDocument();
        document.setLeaveRequest(request);
        document.setData(data);
        document.setFilename(filename);
        document.setContentType(contentType);
        document.setUploadedAt(LocalDateTime.now());

        return leaveDocumentRepository.save(document);
    }

    @Override
    public LeaveDocument getDocument(Long documentId) {
        return leaveDocumentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));
    }

    @Override
    public List<LeaveDocument> getDocumentsByRequest(Long requestId) {
        LeaveRequest request = leaveRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        return leaveDocumentRepository.findByLeaveRequest(request);
    }
}
