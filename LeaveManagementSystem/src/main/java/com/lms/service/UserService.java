package com.lms.service;

import com.lms.model.User;
import java.util.List;

public interface UserService {
	User addUser(User user);
	// Login method for authentication
    User login(String email, String password);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    List<User> getEmployeesUnderManager(Long managerId);
    User updateUserStatus(Long userId, boolean isActive);
}
