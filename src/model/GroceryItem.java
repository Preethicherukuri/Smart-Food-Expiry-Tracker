package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GroceryItem {
    private String name;
    private String category;
    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private int quantity;
    
    public GroceryItem(String name, String category, LocalDate purchaseDate, 
                      LocalDate expiryDate, int quantity) {
        this.name = name;
        this.category = category;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }
    
    // Check if item is expiring within specified days
    public boolean isExpiringSoon(int thresholdDays) {
        long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        return daysUntilExpiry >= 0 && daysUntilExpiry <= thresholdDays;
    }
    
    // Check if item is already expired
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    @Override
    public String toString() {
        return String.format("%-15s | %-12s | %-12s | %-12s | %3d", 
                           name, category, purchaseDate, expiryDate, quantity);
    }
    
    // For file storage
    public String toFileString() {
        return name + "," + category + "," + purchaseDate + "," + expiryDate + "," + quantity;
    }
    
    // Create from file string
    public static GroceryItem fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        return new GroceryItem(
            parts[0], 
            parts[1], 
            LocalDate.parse(parts[2]), 
            LocalDate.parse(parts[3]), 
            Integer.parseInt(parts[4])
        );
    }
}
