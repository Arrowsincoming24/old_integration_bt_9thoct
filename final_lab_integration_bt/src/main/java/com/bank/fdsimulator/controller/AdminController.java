package com.bank.fdsimulator.controller;

import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.entity.FdStatus;
import com.bank.fdsimulator.entity.FixedDeposit;
import com.bank.fdsimulator.entity.Role;
import com.bank.fdsimulator.entity.User;
import com.bank.fdsimulator.service.AuditService;
import com.bank.fdsimulator.service.FdProductService;
import com.bank.fdsimulator.service.FixedDepositService;
import com.bank.fdsimulator.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private FixedDepositService fdService;
    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private FdProductService productService;
    
    // User Management
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/users/customers")
    public ResponseEntity<List<User>> getAllCustomers() {
        List<User> customers = userService.getActiveUsersByRole(Role.CUSTOMER);
        return ResponseEntity.ok(customers);
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user, 
                                         Authentication authentication, HttpServletRequest request) {
        // Verify user exists
        userService.findById(id);
        User currentUser = (User) authentication.getPrincipal();
        
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        
        auditService.logUserAction(currentUser, "USER_UPDATED", "User", id, request);
        
        return ResponseEntity.ok(updatedUser);
    }
    
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication, 
                                      HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        
        auditService.logUserAction(currentUser, "USER_DELETED", "User", id, request);
        userService.deleteUser(id);
        
        return ResponseEntity.ok("User deleted successfully");
    }
    
    // Fixed Deposit Management
    @GetMapping("/fixed-deposits")
    public ResponseEntity<List<FixedDeposit>> getAllFixedDeposits() {
        List<FixedDeposit> fds = fdService.getAllFixedDeposits();
        return ResponseEntity.ok(fds);
    }
    
    @GetMapping("/fixed-deposits/status/{status}")
    public ResponseEntity<List<FixedDeposit>> getFixedDepositsByStatus(@PathVariable FdStatus status) {
        List<FixedDeposit> fds = fdService.getFixedDepositsByStatus(status);
        return ResponseEntity.ok(fds);
    }
    
    @GetMapping("/fixed-deposits/{id}")
    public ResponseEntity<FixedDeposit> getFixedDepositById(@PathVariable Long id) {
        FixedDeposit fd = fdService.getFixedDepositById(id);
        return ResponseEntity.ok(fd);
    }
    
    @PutMapping("/fixed-deposits/{id}/status")
    public ResponseEntity<FixedDeposit> updateFixedDepositStatus(@PathVariable Long id, 
                                                               @RequestParam FdStatus status,
                                                               Authentication authentication, 
                                                               HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        FixedDeposit updatedFd = fdService.updateFixedDepositStatus(id, status, currentUser);
        
        auditService.logUserAction(currentUser, "FD_STATUS_UPDATED", "FixedDeposit", id, request);
        
        return ResponseEntity.ok(updatedFd);
    }
    
    @PostMapping("/fixed-deposits/{id}/close")
    public ResponseEntity<?> closeFixedDeposit(@PathVariable Long id, Authentication authentication, 
                                             HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        
        fdService.closeFixedDeposit(id, currentUser);
        auditService.logUserAction(currentUser, "FD_CLOSED", "FixedDeposit", id, request);
        
        return ResponseEntity.ok("Fixed Deposit closed successfully");
    }
    
    // Dashboard Statistics
    @GetMapping("/dashboard/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        
        List<User> allUsers = userService.getAllUsers();
        List<User> customers = userService.getActiveUsersByRole(Role.CUSTOMER);
        List<FixedDeposit> allFds = fdService.getAllFixedDeposits();
        List<FixedDeposit> activeFds = fdService.getFixedDepositsByStatus(FdStatus.ACTIVE);
        
        stats.put("totalUsers", allUsers.size());
        stats.put("totalCustomers", customers.size());
        stats.put("totalFixedDeposits", allFds.size());
        stats.put("activeFixedDeposits", activeFds.size());
        
        // Calculate total amount
        double totalAmount = allFds.stream()
                .mapToDouble(fd -> fd.getPrincipalAmount().doubleValue())
                .sum();
        stats.put("totalAmount", totalAmount);
        
        return ResponseEntity.ok(stats);
    }
    
    // Audit Logs
    @GetMapping("/audit-logs")
    public ResponseEntity<Page<com.bank.fdsimulator.entity.AuditLog>> getAuditLogs(Pageable pageable) {
        Page<com.bank.fdsimulator.entity.AuditLog> auditLogs = auditService.getAllAuditLogs(pageable);
        return ResponseEntity.ok(auditLogs);
    }
    
    @GetMapping("/audit-logs/user/{userId}")
    public ResponseEntity<Page<com.bank.fdsimulator.entity.AuditLog>> getAuditLogsByUser(
            @PathVariable Long userId, Pageable pageable) {
        User user = userService.findById(userId);
        Page<com.bank.fdsimulator.entity.AuditLog> auditLogs = auditService.getAuditLogsByUser(user, pageable);
        return ResponseEntity.ok(auditLogs);
    }
    
    // FD Product Management
    @GetMapping("/products")
    public ResponseEntity<List<FdProduct>> getAllProducts() {
        List<FdProduct> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/active")
    public ResponseEntity<List<FdProduct>> getActiveProducts() {
        List<FdProduct> products = productService.getActiveProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/products/{id}")
    public ResponseEntity<FdProduct> getProductById(@PathVariable Long id) {
        FdProduct product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    @PostMapping("/products")
    public ResponseEntity<FdProduct> createProduct(@RequestBody FdProduct product, 
                                                   Authentication authentication, 
                                                   HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        FdProduct createdProduct = productService.createProduct(product);
        
        auditService.logUserAction(currentUser, "PRODUCT_CREATED", "FdProduct", 
                                   createdProduct.getId(), request);
        
        return ResponseEntity.ok(createdProduct);
    }
    
    @PutMapping("/products/{id}")
    public ResponseEntity<FdProduct> updateProduct(@PathVariable Long id, 
                                                   @RequestBody FdProduct product,
                                                   Authentication authentication, 
                                                   HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        FdProduct updatedProduct = productService.updateProduct(id, product);
        
        auditService.logUserAction(currentUser, "PRODUCT_UPDATED", "FdProduct", id, request);
        
        return ResponseEntity.ok(updatedProduct);
    }
    
    @DeleteMapping("/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, 
                                          Authentication authentication, 
                                          HttpServletRequest request) {
        User currentUser = (User) authentication.getPrincipal();
        
        auditService.logUserAction(currentUser, "PRODUCT_DELETED", "FdProduct", id, request);
        productService.deleteProduct(id);
        
        return ResponseEntity.ok("Product deleted successfully");
    }
    
    @GetMapping("/products/{id}/users")
    public ResponseEntity<List<User>> getProductUsers(@PathVariable Long id) {
        FdProduct product = productService.getProductById(id);
        List<FixedDeposit> deposits = fdService.getFixedDepositsByProduct(product);
        
        // Extract unique users from deposits
        List<User> users = deposits.stream()
                .map(FixedDeposit::getUser)
                .distinct()
                .toList();
        
        return ResponseEntity.ok(users);
    }
}
