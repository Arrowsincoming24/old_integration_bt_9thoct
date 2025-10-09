package com.bank.fdsimulator.service;

import com.bank.fdsimulator.entity.Currency;
import com.bank.fdsimulator.entity.FdProduct;
import com.bank.fdsimulator.entity.FdStatus;
import com.bank.fdsimulator.entity.FixedDeposit;
import com.bank.fdsimulator.entity.User;
import com.bank.fdsimulator.repository.FixedDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FixedDepositService {
    
    @Autowired
    private FixedDepositRepository fdRepository;
    
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private SmsService smsService;
    
    public FixedDeposit createFixedDeposit(User user, BigDecimal principalAmount, 
                                         BigDecimal interestRate, Integer tenureInMonths, Currency currency) {
        FixedDeposit fd = new FixedDeposit(user, principalAmount, interestRate, tenureInMonths, FdStatus.ACTIVE);
        fd.setCurrency(currency);
        
        // Calculate maturity amount
        BigDecimal interestAmount = calculateInterest(principalAmount, interestRate, tenureInMonths);
        BigDecimal maturityAmount = principalAmount.add(interestAmount);
        
        fd.setInterestAmount(interestAmount);
        fd.setMaturityAmount(maturityAmount);
        
        FixedDeposit savedFd = fdRepository.save(fd);
        
        // Log audit
        auditService.logUserAction(user, "FD_CREATED", "FixedDeposit");
        
        // Send notification
        String message = String.format("Your Fixed Deposit of ₹%s has been created successfully. " +
                                     "Maturity amount: ₹%s, Maturity date: %s",
                                     principalAmount, maturityAmount, savedFd.getMaturityDate());
        if (user.getPhoneNumber() != null) {
            smsService.sendNotification(user.getPhoneNumber(), message);
        }
        
        return savedFd;
    }
    
    public List<FixedDeposit> getFixedDepositsByUser(User user) {
        return fdRepository.findByUser(user);
    }
    
    public List<FixedDeposit> getFixedDepositsByUserAndStatus(User user, FdStatus status) {
        return fdRepository.findByUserAndStatus(user, status);
    }
    
    public List<FixedDeposit> getAllFixedDeposits() {
        return fdRepository.findAll();
    }
    
    public List<FixedDeposit> getFixedDepositsByStatus(FdStatus status) {
        return fdRepository.findByStatus(status);
    }
    
    public FixedDeposit getFixedDepositById(Long id) {
        return fdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed Deposit not found"));
    }
    
    public FixedDeposit updateFixedDepositStatus(Long id, FdStatus newStatus, User user) {
        FixedDeposit fd = getFixedDepositById(id);
        FdStatus oldStatus = fd.getStatus();
        
        fd.setStatus(newStatus);
        FixedDeposit updatedFd = fdRepository.save(fd);
        
        // Log audit
        auditService.logUserAction(user, "FD_STATUS_UPDATED", "FixedDeposit", 
                                 "Status: " + oldStatus, "Status: " + newStatus);
        
        return updatedFd;
    }
    
    public void closeFixedDeposit(Long id, User user) {
        FixedDeposit fd = getFixedDepositById(id);
        
        if (fd.getStatus() == FdStatus.ACTIVE) {
            fd.setStatus(FdStatus.PREMATURE_CLOSED);
            fdRepository.save(fd);
            
            // Log audit
            auditService.logUserAction(user, "FD_CLOSED", "FixedDeposit");
            
            // Send notification
            String message = String.format("Your Fixed Deposit #%d has been closed. " +
                                         "Principal amount: ₹%s will be returned.",
                                         id, fd.getPrincipalAmount());
            if (user.getPhoneNumber() != null) {
                smsService.sendNotification(user.getPhoneNumber(), message);
            }
        }
    }
    
    public BigDecimal calculateInterest(BigDecimal principal, BigDecimal rate, Integer months) {
        // Simple interest calculation: P * R * T / 100
        // Where P = Principal, R = Rate per annum, T = Time in years
        BigDecimal timeInYears = BigDecimal.valueOf(months).divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        return principal.multiply(rate).multiply(timeInYears).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
    
    public BigDecimal calculateMaturityAmount(BigDecimal principal, BigDecimal rate, Integer months) {
        BigDecimal interest = calculateInterest(principal, rate, months);
        return principal.add(interest);
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    public void processMaturedFds() {
        List<FixedDeposit> maturedFds = fdRepository.findMaturedFds(LocalDateTime.now());
        
        for (FixedDeposit fd : maturedFds) {
            fd.setStatus(FdStatus.MATURED);
            fdRepository.save(fd);
            
            // Send maturity notification
            String message = String.format("Your Fixed Deposit #%d has matured! " +
                                         "Maturity amount: ₹%s is ready for withdrawal.",
                                         fd.getId(), fd.getMaturityAmount());
            if (fd.getUser().getPhoneNumber() != null) {
                smsService.sendNotification(fd.getUser().getPhoneNumber(), message);
            }
        }
    }
    
    public Long getFdCountByUser(User user) {
        return fdRepository.countByUser(user);
    }
    
    public Double getTotalActiveAmountByUser(User user) {
        Double total = fdRepository.getTotalActiveAmountByUser(user);
        return total != null ? total : 0.0;
    }
    
    public List<FixedDeposit> getFixedDepositsByProduct(FdProduct product) {
        return fdRepository.findByProduct(product);
    }
}
