package com.bank.fdsimulator.controller;

import com.bank.fdsimulator.dto.LoginRequest;
import com.bank.fdsimulator.dto.LoginResponse;
import com.bank.fdsimulator.dto.OtpRequest;
import com.bank.fdsimulator.dto.RegisterRequest;
import com.bank.fdsimulator.entity.Currency;
import com.bank.fdsimulator.entity.Language;
import com.bank.fdsimulator.entity.Role;
import com.bank.fdsimulator.entity.User;
import com.bank.fdsimulator.security.JwtUtil;
import com.bank.fdsimulator.service.OtpService;
import com.bank.fdsimulator.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private OtpService otpService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            // Check if user already exists
            if (userService.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest().body("Username already exists");
            }
            
            if (userService.existsByEmail(request.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }
            
            if (request.getPhoneNumber() != null && userService.existsByPhoneNumber(request.getPhoneNumber())) {
                return ResponseEntity.badRequest().body("Phone number already exists");
            }
            
            // Create new user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setPassword(request.getPassword());
            user.setRole(Role.CUSTOMER);
            user.setPreferredCurrency(Currency.fromCode(request.getPreferredCurrency()));
            user.setPreferredLanguage(Language.fromCode(request.getPreferredLanguage()));
            
            User savedUser = userService.createUser(user, passwordEncoder);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("userId", savedUser.getId());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            User user = userService.findByUsername(request.getUsername()).orElseThrow();
            UserDetails userDetails = userService.loadUserByUsername(request.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);
            
            LoginResponse response = new LoginResponse();
            response.setToken(jwt);
            response.setUsername(user.getUsername());
            response.setEmail(user.getEmail());
            response.setRole(user.getRole().name());
            response.setPhoneNumber(user.getPhoneNumber());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpRequest request) {
        try {
            otpService.sendOtp(request.getPhoneNumber());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }
    
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpRequest request) {
        try {
            boolean isValid = otpService.verifyOtp(request.getPhoneNumber(), request.getOtp());
            
            if (isValid) {
                // Find user by phone number
                User user = userService.findByPhoneNumber(request.getPhoneNumber()).orElse(null);
                
                if (user != null) {
                    UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
                    String jwt = jwtUtil.generateToken(userDetails);
                    
                    LoginResponse response = new LoginResponse();
                    response.setToken(jwt);
                    response.setUsername(user.getUsername());
                    response.setEmail(user.getEmail());
                    response.setRole(user.getRole().name());
                    response.setPhoneNumber(user.getPhoneNumber());
                    
                    return ResponseEntity.ok(response);
                } else {
                    return ResponseEntity.badRequest().body("User not found with this phone number");
                }
            } else {
                return ResponseEntity.badRequest().body("Invalid OTP");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("OTP verification failed: " + e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}
