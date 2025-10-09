package com.bank.fdsimulator.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class FdCreateRequest {
    
    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1000.0", message = "Minimum principal amount is 1000")
    private BigDecimal principalAmount;
    
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate must be positive")
    private BigDecimal interestRate;
    
    @NotNull(message = "Tenure is required")
    @Min(value = 1, message = "Tenure must be at least 1 month")
    private Integer tenureInMonths;
    
    private String currency = "USD";
    
    // Constructors
    public FdCreateRequest() {}
    
    public FdCreateRequest(BigDecimal principalAmount, BigDecimal interestRate, Integer tenureInMonths) {
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.tenureInMonths = tenureInMonths;
    }
    
    // Getters and Setters
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
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
