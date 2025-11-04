package com.lms.service;

import com.lms.model.LeaveNotification;
import com.lms.model.User;
import com.lms.repository.LeaveNotificationRepository;
import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired private LeaveNotificationRepository leaveNotificationRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public LeaveNotification sendNotification(LeaveNotification notification) {
        return leaveNotificationRepository.save(notification);
    }

    @Override
    public List<LeaveNotification> getUserNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return leaveNotificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
