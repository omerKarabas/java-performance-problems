package com.example.demo.modules.cache.generatekey.hashcodemismatch.domain.entity;

public enum ProductCategory {
    LAPTOP("Laptop"),
    DESKTOP("Desktop"),
    TABLET("Tablet"),
    SMARTPHONE("Smartphone"),
    MONITOR("Monitor"),
    KEYBOARD("Keyboard"),
    MOUSE("Mouse"),
    HEADPHONES("Headphones"),
    CAMERA("Camera"),
    PRINTER("Printer");
    
    private final String displayName;
    
    ProductCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
