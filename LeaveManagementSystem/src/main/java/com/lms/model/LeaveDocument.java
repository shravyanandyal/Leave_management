package com.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "leave_documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long documentId;

    @ManyToOne
    @JoinColumn(name = "request_id", nullable = false)
    private LeaveRequest leaveRequest;

    @Lob
    @Column(name = "data", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] data;  // ✅ renamed from imageData to data

    @Column(name = "filename")
    private String filename;  // ✅ added for file download/view names

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "uploaded_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime uploadedAt;
}
