package com.bank.fdsimulator.service;

import com.bank.fdsimulator.entity.Currency;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class CurrencyService {
    
    // Exchange rates (in real application, these would be fetched from an API)
    private static final BigDecimal USD_TO_INR = new BigDecimal("83.25");
    private static final BigDecimal USD_TO_KWD = new BigDecimal("0.31");
    private static final BigDecimal INR_TO_USD = new BigDecimal("0.012");
    private static final BigDecimal KWD_TO_USD = new BigDecimal("3.23");
    
    public BigDecimal convertAmount(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) {
            return amount;
        }
        
        // Convert to USD first, then to target currency
        BigDecimal usdAmount = convertToUSD(amount, fromCurrency);
        return convertFromUSD(usdAmount, toCurrency);
    }
    
    private BigDecimal convertToUSD(BigDecimal amount, Currency currency) {
        switch (currency) {
            case USD:
                return amount;
            case INR:
                return amount.multiply(INR_TO_USD).setScale(3, RoundingMode.HALF_UP);
            case KWD:
                return amount.multiply(KWD_TO_USD).setScale(3, RoundingMode.HALF_UP);
            default:
                return amount;
        }
    }
    
    private BigDecimal convertFromUSD(BigDecimal usdAmount, Currency currency) {
        switch (currency) {
            case USD:
                return usdAmount.setScale(2, RoundingMode.HALF_UP);
            case INR:
                return usdAmount.multiply(USD_TO_INR).setScale(2, RoundingMode.HALF_UP);
            case KWD:
                return usdAmount.multiply(USD_TO_KWD).setScale(3, RoundingMode.HALF_UP);
            default:
                return usdAmount;
        }
    }
    
    public String formatAmount(BigDecimal amount, Currency currency) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(currency.getDecimalPlaces());
        formatter.setMaximumFractionDigits(currency.getDecimalPlaces());
        
        return currency.getSymbol() + formatter.format(amount);
    }
    
    public String formatAmountWithLocale(BigDecimal amount, Currency currency, Locale locale) {
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        formatter.setMinimumFractionDigits(currency.getDecimalPlaces());
        formatter.setMaximumFractionDigits(currency.getDecimalPlaces());
        
        return currency.getSymbol() + formatter.format(amount);
    }
    
    public BigDecimal getMinimumAmount(Currency currency) {
        switch (currency) {
            case USD:
                return new BigDecimal("1000.00");
            case INR:
                return new BigDecimal("1000.00");
            case KWD:
                return new BigDecimal("1000.000");
            default:
                return new BigDecimal("1000.00");
        }
    }
    
    public BigDecimal getMaximumAmount(Currency currency) {
        switch (currency) {
            case USD:
                return new BigDecimal("1000000.00");
            case INR:
                return new BigDecimal("1000000.00");
            case KWD:
                return new BigDecimal("1000000.000");
            default:
                return new BigDecimal("1000000.00");
        }
    }
}
