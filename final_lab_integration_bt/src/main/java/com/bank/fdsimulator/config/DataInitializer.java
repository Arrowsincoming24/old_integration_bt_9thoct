package com.bank.fdsimulator.config;

import com.bank.fdsimulator.entity.Currency;
import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.entity.Language;
import com.bank.fdsimulator.entity.Role;
import com.bank.fdsimulator.entity.User;
import com.bank.fdsimulator.repository.FdProductRepository;
import com.bank.fdsimulator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private FdProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize default admin user
        initializeAdminUser();
        
        // Check if products already exist
        if (productRepository.count() == 0) {
            initializeProducts();
        }
    }
    
    private void initializeAdminUser() {
        // Check if admin already exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@cashcached.com");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);
            admin.setPreferredCurrency(Currency.USD);
            admin.setPreferredLanguage(Language.EN);
            admin.setEnabled(true);
            userRepository.save(admin);
            
            System.out.println("\n" + "=".repeat(60));
            System.out.println("✓ DEFAULT ADMIN ACCOUNT CREATED");
            System.out.println("=".repeat(60));
            System.out.println("Username: admin");
            System.out.println("Password: Admin@123");
            System.out.println("Email: admin@cashcached.com");
            System.out.println("=".repeat(60) + "\n");
        }
    }
    
    private void initializeProducts() {
        // Product 1: CashCached Short Term
        FdProduct product1 = new FdProduct(
            "CashCached Short Term",
            "Perfect for short-term savings with flexible tenure options",
            new BigDecimal("5000"),
            new BigDecimal("100000"),
            3,
            12,
            new BigDecimal("5.5")
        );
        productRepository.save(product1);
        
        // Product 2: CashCached Regular
        FdProduct product2 = new FdProduct(
            "CashCached Regular",
            "Standard fixed deposit with competitive interest rates",
            new BigDecimal("10000"),
            new BigDecimal("500000"),
            6,
            24,
            new BigDecimal("6.5")
        );
        productRepository.save(product2);
        
        // Product 3: CashCached Senior
        FdProduct product3 = new FdProduct(
            "CashCached Senior",
            "Exclusive FD scheme for senior citizens with higher returns",
            new BigDecimal("25000"),
            new BigDecimal("1000000"),
            12,
            60,
            new BigDecimal("7.5")
        );
        productRepository.save(product3);
        
        // Product 4: CashCached Tax Saver
        FdProduct product4 = new FdProduct(
            "CashCached Tax Saver",
            "5-year lock-in period with tax benefits under Section 80C",
            new BigDecimal("10000"),
            new BigDecimal("150000"),
            60,
            60,
            new BigDecimal("6.75")
        );
        productRepository.save(product4);
        
        // Product 5: CashCached Flexi
        FdProduct product5 = new FdProduct(
            "CashCached Flexi",
            "Flexible deposit with partial withdrawal facility",
            new BigDecimal("50000"),
            new BigDecimal("2000000"),
            12,
            36,
            new BigDecimal("6.25")
        );
        productRepository.save(product5);
        
        // Product 6: CashCached Premium
        FdProduct product6 = new FdProduct(
            "CashCached Premium",
            "Premium FD for high-value deposits with attractive rates",
            new BigDecimal("500000"),
            new BigDecimal("10000000"),
            12,
            60,
            new BigDecimal("7.25")
        );
        productRepository.save(product6);
        
        // Product 7: CashCached Monthly Income
        FdProduct product7 = new FdProduct(
            "CashCached Monthly Income",
            "Earn monthly interest payouts for regular income",
            new BigDecimal("100000"),
            new BigDecimal("5000000"),
            12,
            60,
            new BigDecimal("6.85")
        );
        productRepository.save(product7);
        
        // Product 8: CashCached Youth
        FdProduct product8 = new FdProduct(
            "CashCached Youth",
            "Special FD scheme for youth (18-30 years) with bonus rates",
            new BigDecimal("5000"),
            new BigDecimal("200000"),
            6,
            36,
            new BigDecimal("6.0")
        );
        productRepository.save(product8);
        
        // Product 9: CashCached Corporate
        FdProduct product9 = new FdProduct(
            "CashCached Corporate",
            "Bulk deposits for corporate entities with premium rates",
            new BigDecimal("1000000"),
            new BigDecimal("50000000"),
            12,
            60,
            new BigDecimal("7.75")
        );
        productRepository.save(product9);
        
        // Product 10: CashCached Cumulative
        FdProduct product10 = new FdProduct(
            "CashCached Cumulative",
            "Interest compounded quarterly for maximum returns",
            new BigDecimal("25000"),
            new BigDecimal("1000000"),
            12,
            60,
            new BigDecimal("7.0")
        );
        productRepository.save(product10);
        
        System.out.println("✓ Initialized 10 CashCached products successfully");
    }
}
