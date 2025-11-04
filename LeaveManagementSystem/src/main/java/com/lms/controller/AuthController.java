package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lms.model.User;
import com.lms.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * Displays the login page.
     * This simply loads the login.jsp view.
     */
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";  // login.jsp (in /WEB-INF/views/)
    }

    /**
     * Handles login form submission.
     * Validates user credentials and redirects based on role.
     */
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        // Step 1: Validate credentials via service layer
        User user = userService.login(email, password);

        // Step 2: If user found
        if (user != null) {
            // Save logged-in user to session
            session.setAttribute("loggedUser", user);

            // Step 3: Redirect based on user role
            String role = user.getRole().getRoleName().toUpperCase();

            switch (role) {
                case "ADMIN":
                    return "redirect:/admin/dashboard";
                case "MANAGER":
                    return "redirect:/manager/dashboard";
                case "EMPLOYEE":
                    return "redirect:/employee/dashboard";
                default:
                    model.addAttribute("error", "Invalid user role configured.");
                    return "login";
            }
        }

        // Step 4: If login failed
        model.addAttribute("error", "Invalid email or password.");
        return "login";
    }

    /**
     * Logs the user out by invalidating the session.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // removes session data
        return "redirect:/login"; // redirects to login page
    }
}

