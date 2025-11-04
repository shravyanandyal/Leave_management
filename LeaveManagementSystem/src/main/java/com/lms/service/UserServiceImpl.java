package com.lms.service;

import com.lms.model.User;
import com.lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getEmployeesUnderManager(Long managerId) {
        return userRepository.findByManager_Id(managerId);
    }

    @Override
    public User updateUserStatus(Long userId, boolean isActive) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setIsActive(isActive);
        return userRepository.save(user);
    }

    @Override
    public User login(String email, String password) {
        // Step 1: Find user by email
        User user = userRepository.findByEmail(email);

        // Step 2: Check if user exists and password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        // Step 3: Invalid login
        return null;
    }
}
