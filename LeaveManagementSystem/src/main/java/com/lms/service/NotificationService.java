package com.lms.service;

import com.lms.model.LeaveNotification;
import com.lms.model.User;
import java.util.List;

public interface NotificationService {
    LeaveNotification sendNotification(LeaveNotification notification);
    List<LeaveNotification> getUserNotifications(Long userId);
}
