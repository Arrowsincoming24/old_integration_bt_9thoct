package com.bank.fdsimulator.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fixed_deposits")
public class FixedDeposit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private FdProduct product;
    
    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1000.0", message = "Minimum principal amount is 1000")
    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal principalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency = Currency.USD;
    
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate must be positive")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    @Column(nullable = false)
    private Integer tenureInMonths;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FdStatus status;
    
    @Column(precision = 15, scale = 3)
    private BigDecimal maturityAmount;
    
    @Column(precision = 15, scale = 3)
    private BigDecimal interestAmount;
    
    @Column(nullable = false)
    private LocalDateTime startDate;
    
    @Column(nullable = false)
    private LocalDateTime maturityDate;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Constructors
    public FixedDeposit() {}
    
    public FixedDeposit(User user, BigDecimal principalAmount, BigDecimal interestRate, 
                       Integer tenureInMonths, FdStatus status) {
        this.user = user;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureInMonths = tenureInMonths;
        this.status = status;
        this.startDate = LocalDateTime.now();
        this.maturityDate = this.startDate.plusMonths(tenureInMonths);
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }
    
    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }
    
    public BigDecimal getInterestRate() {
        return interestRate;
    }
    
    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
    
    public Integer getTenureInMonths() {
        return tenureInMonths;
    }
    
    public void setTenureInMonths(Integer tenureInMonths) {
        this.tenureInMonths = tenureInMonths;
    }
    
    public FdStatus getStatus() {
        return status;
    }
    
    public void setStatus(FdStatus status) {
        this.status = status;
    }
    
    public BigDecimal getMaturityAmount() {
        return maturityAmount;
    }
    
    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }
    
    public BigDecimal getInterestAmount() {
        return interestAmount;
    }
    
    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getMaturityDate() {
        return maturityDate;
    }
    
    public void setMaturityDate(LocalDateTime maturityDate) {
        this.maturityDate = maturityDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    
    public FdProduct getProduct() {
        return product;
    }
    
    public void setProduct(FdProduct product) {
        this.product = product;
    }
}
