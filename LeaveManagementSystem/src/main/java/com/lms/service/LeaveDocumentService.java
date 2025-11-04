package com.lms.service;

import com.lms.model.LeaveDocument;
import java.util.List;

public interface LeaveDocumentService {

    // Upload and save a document
    LeaveDocument uploadDocument(Long requestId, byte[] data, String filename, String contentType);

    // Get a specific document (for view/download)
    LeaveDocument getDocument(Long documentId);

    // Get all documents for a leave request
    List<LeaveDocument> getDocumentsByRequest(Long requestId);
}
