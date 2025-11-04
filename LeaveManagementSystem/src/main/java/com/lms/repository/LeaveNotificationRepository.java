package com.lms.repository;

import com.lms.model.LeaveNotification;
import com.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveNotificationRepository extends JpaRepository<LeaveNotification, Long> {

    // Get notifications for a user
    List<LeaveNotification> findByUserOrderByCreatedAtDesc(User user);
}
