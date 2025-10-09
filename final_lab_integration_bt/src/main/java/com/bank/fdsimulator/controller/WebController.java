package com.bank.fdsimulator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/")
    public String home() {
        return "redirect:cashcached";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard-new";
    }
    
    @GetMapping("/customer/dashboard")
    public String customerDashboard() {
        return "customer-dashboard";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/cashcached")
    public String cashcached() {
        return "cashcached";
    }
    
    @GetMapping("/fd-calculator")
    public String fdCalculator() {
        return "fd-calculator";
    }
}
