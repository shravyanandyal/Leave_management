package com.lms.repository;

import com.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // For login authentication
    User findByEmail(String email);

    // Get all employees under a manager
    List<User> findByManager_Id(Long managerId);

    // Get all active users
    List<User> findByIsActiveTrue();
}
