package com.bank.fdsimulator.entity;

public enum Currency {
    USD("USD", "$", 2, "US Dollar"),
    INR("INR", "₹", 2, "Indian Rupee"),
    KWD("KWD", "د.ك", 3, "Kuwaiti Dinar");
    
    private final String code;
    private final String symbol;
    private final int decimalPlaces;
    private final String description;
    
    Currency(String code, String symbol, int decimalPlaces, String description) {
        this.code = code;
        this.symbol = symbol;
        this.decimalPlaces = decimalPlaces;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getSymbol() {
        return symbol;
    }
    
    public int getDecimalPlaces() {
        return decimalPlaces;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static Currency fromCode(String code) {
        for (Currency currency : values()) {
            if (currency.code.equals(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unknown currency code: " + code);
    }
}
