package service;

import model.GroceryItem;
import util.FileHandler;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class GroceryManager {
    private List<GroceryItem> groceryItems;
    private int alertThresholdDays;
    
    // Common categories for suggestions
    private static final List<String> COMMON_CATEGORIES = Arrays.asList(
        "Dairy", "Fruits", "Vegetables", "Meat & Poultry", "Bakery", 
        "Grains & Pasta", "Beverages", "Snacks", "Frozen Foods", 
        "Condiments & Spices", "Canned Goods", "Other"
    );
    
    public GroceryManager() {
        this.groceryItems = FileHandler.loadItems();
        this.alertThresholdDays = 3; // Default: 3 days
    }
    
    // Add a new grocery item
    public boolean addItem(GroceryItem item) {
        if (item != null) {
            groceryItems.add(item);
            FileHandler.saveItems(groceryItems);
            return true;
        }
        return false;
    }
    
    // Delete item by name
    public boolean deleteItem(String itemName) {
        boolean removed = groceryItems.removeIf(item -> 
            item.getName().equalsIgnoreCase(itemName));
        if (removed) {
            FileHandler.saveItems(groceryItems);
        }
        return removed;
    }
    
    // Search items by name
    public List<GroceryItem> searchByName(String name) {
        return groceryItems.stream()
            .filter(item -> item.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    // Search items by category
    public List<GroceryItem> searchByCategory(String category) {
        return groceryItems.stream()
            .filter(item -> item.getCategory().equalsIgnoreCase(category))
            .collect(Collectors.toList());
    }
    
    // Get items expiring soon
    public List<GroceryItem> getExpiringSoonItems() {
        return groceryItems.stream()
            .filter(item -> item.isExpiringSoon(alertThresholdDays))
            .sorted(Comparator.comparing(GroceryItem::getExpiryDate))
            .collect(Collectors.toList());
    }
    
    // Get expired items
    public List<GroceryItem> getExpiredItems() {
        return groceryItems.stream()
            .filter(GroceryItem::isExpired)
            .sorted(Comparator.comparing(GroceryItem::getExpiryDate))
            .collect(Collectors.toList());
    }
    
    // Sort by expiry date
    public List<GroceryItem> sortByExpiryDate() {
        return groceryItems.stream()
            .sorted(Comparator.comparing(GroceryItem::getExpiryDate))
            .collect(Collectors.toList());
    }
    
    // Sort by category
    public List<GroceryItem> sortByCategory() {
        return groceryItems.stream()
            .sorted(Comparator.comparing(GroceryItem::getCategory))
            .collect(Collectors.toList());
    }
    
    // Sort by name
    public List<GroceryItem> sortByName() {
        return groceryItems.stream()
            .sorted(Comparator.comparing(GroceryItem::getName))
            .collect(Collectors.toList());
    }
    
    // Get all items
    public List<GroceryItem> getAllItems() {
        return new ArrayList<>(groceryItems);
    }
    
    // Get all unique categories from existing items
    public List<String> getAllCategories() {
        return groceryItems.stream()
            .map(GroceryItem::getCategory)
            .distinct()
            .sorted()
            .collect(Collectors.toList());
    }
    
    // Get available categories (both common and existing)
    public List<String> getAvailableCategories() {
        Set<String> categories = new TreeSet<>(COMMON_CATEGORIES);
        categories.addAll(getAllCategories());
        return new ArrayList<>(categories);
    }
    
    // Category suggestions
    public List<String> getCategorySuggestions(String input) {
        return COMMON_CATEGORIES.stream()
            .filter(category -> category.toLowerCase().startsWith(input.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    // Statistics
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalItems", groceryItems.size());
        stats.put("expiredItems", getExpiredItems().size());
        stats.put("expiringSoon", getExpiringSoonItems().size());
        
        // Category distribution
        Map<String, Long> categoryCount = groceryItems.stream()
            .collect(Collectors.groupingBy(GroceryItem::getCategory, Collectors.counting()));
        stats.put("categoryDistribution", categoryCount);
        
        return stats;
    }
    
    // Set alert threshold
    public void setAlertThresholdDays(int days) {
        this.alertThresholdDays = days;
    }
    
    public int getAlertThresholdDays() {
        return alertThresholdDays;
    }
    
    // Create backup
    public void createBackup() {
        FileHandler.createBackup(groceryItems);
    }
}
