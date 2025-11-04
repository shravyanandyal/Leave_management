package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

import com.lms.model.User;

@Controller
public class EmployeeController {

    @Autowired
    private HttpSession session;

    // 🟢 Employee Dashboard
    @GetMapping("/employee/dashboard")
    public String employeeDashboard(Model model) {
        User user = (User) session.getAttribute("loggedUser");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("title", "Employee Dashboard");
        model.addAttribute("message", "Welcome, " + user.getFirstName() + "!");
        return "employee-dashboard"; // JSP: /WEB-INF/views/employee-dashboard.jsp
    }

    // 🟢 View My Leaves
    @GetMapping("/employee/leaves")
    public String viewMyLeaves() {
        return "redirect:/leaves/me";
    }

    // 🟢 Apply for Leave
    @GetMapping("/employee/leaves/apply")
    public String applyLeave() {
        return "redirect:/leaves/apply";
    }

    // 🟢 Check Leave Balance
    @GetMapping("/employee/leaves/balance")
    public String checkBalance() {
        return "redirect:/leaves/balance";
    }

    // 🟢 View Leave Policies
    @GetMapping("/employee/leaves/policies")
    public String viewPolicies() {
        return "redirect:/leaves/policies";
    }
}
