package com.bank.fdsimulator.entity;

public enum Language {
    EN("en", "English", "English"),
    JA("ja", "日本語", "Japanese");
    
    private final String code;
    private final String displayName;
    private final String englishName;
    
    Language(String code, String displayName, String englishName) {
        this.code = code;
        this.displayName = displayName;
        this.englishName = englishName;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getEnglishName() {
        return englishName;
    }
    
    public static Language fromCode(String code) {
        for (Language language : values()) {
            if (language.code.equals(code)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Unknown language code: " + code);
    }
}
