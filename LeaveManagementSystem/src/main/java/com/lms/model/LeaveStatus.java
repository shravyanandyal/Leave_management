package com.lms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leave_statuses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "status_name", unique = true, nullable = false)
    private String statusName; // Pending, Approved, Rejected, Cancelled
}
