package ui;

import model.GroceryItem;
import service.GroceryManager;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenuUI {
    private GroceryManager groceryManager;
    private Scanner scanner;
    
    public MenuUI() {
        this.groceryManager = new GroceryManager();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        displayWelcomeMessage();
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1 -> addItem();
                case 2 -> viewAllItems();
                case 3 -> viewExpiringSoon();
                case 4 -> searchItems();
                case 5 -> deleteItem();
                case 6 -> showStatistics();
                case 7 -> manageSettings();
                case 8 -> createBackup();
                case 0 -> {
                    System.out.println("Thank you for using S.E.T! Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayWelcomeMessage() {
        System.out.println("=".repeat(50));
        System.out.println("   SMART FOOD EXPIRY TRACKER (S.E.T)");
        System.out.println("=".repeat(50));
        System.out.println("Today's Date: " + LocalDate.now());
        
        // Auto-check for expiring items
        List<GroceryItem> expiringSoon = groceryManager.getExpiringSoonItems();
        List<GroceryItem> expired = groceryManager.getExpiredItems();
        
        if (!expiringSoon.isEmpty() || !expired.isEmpty()) {
            System.out.println("\n*** ALERTS ***");
            if (!expired.isEmpty()) {
                System.out.println("[EXPIRED] " + expired.size() + " item(s) have EXPIRED!");
            }
            if (!expiringSoon.isEmpty()) {
                System.out.println("[ALERT] " + expiringSoon.size() + " item(s) expiring within " + 
                                 groceryManager.getAlertThresholdDays() + " days!");
            }
        }
    }
    
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           MAIN MENU");
        System.out.println("=".repeat(40));
        System.out.println("1. Add Grocery Item");
        System.out.println("2. View All Items");
        System.out.println("3. View Expiring Soon");
        System.out.println("4. Search Items");
        System.out.println("5. Delete Item");
        System.out.println("6. Statistics");
        System.out.println("7. Settings");
        System.out.println("8. Create Backup");
        System.out.println("0. Exit");
        System.out.println("-".repeat(40));
    }
    
    private void addItem() {
        System.out.println("\n--- Add New Grocery Item ---");

        String name = getStringInput("Item Name: ");
        
        // Category selection menu
        String category = selectCategory();
        
        LocalDate purchaseDate = getDateInput("Purchase Date (YYYY-MM-DD): ");
        LocalDate expiryDate = getDateInput("Expiry Date (YYYY-MM-DD): ");

        if (expiryDate.isBefore(purchaseDate)) {
            System.out.println("Error: Expiry date cannot be before purchase date!");
            return;
        }

        int quantity = getIntInput("Quantity: ");

        GroceryItem newItem = new GroceryItem(name, category, purchaseDate, expiryDate, quantity);

        if (groceryManager.addItem(newItem)) {
            System.out.println("Item added successfully!");
        } else {
            System.out.println("Failed to add item!");
        }
    }

    private String selectCategory() {
        System.out.println("\nSelect Category:");
        System.out.println("1. Dairy ");
        System.out.println("2. Fruits ");
        System.out.println("3. Vegetables ");
        System.out.println("4. Meat & Poultry ");
        System.out.println("5. Bakery ");
        System.out.println("6. Grains & Pasta ");
        System.out.println("7. Beverages ");
        System.out.println("8. Snacks ");
        System.out.println("9. Frozen Foods ");
        System.out.println("10. Condiments & Spices ");
        System.out.println("11. Canned Goods ");
        System.out.println("12. Other ");
        
        while (true) {
            int choice = getIntInput("Choose category (1-12): ");
            
            switch (choice) {
                case 1 -> { 
                    System.out.println("Category selected: Dairy");
                    return "Dairy"; 
                }
                case 2 -> { 
                    System.out.println("Category selected: Fruits");
                    return "Fruits"; 
                }
                case 3 -> { 
                    System.out.println("Category selected: Vegetables");
                    return "Vegetables"; 
                }
                case 4 -> { 
                    System.out.println("Category selected: Meat & Poultry");
                    return "Meat & Poultry"; 
                }
                case 5 -> { 
                    System.out.println("Category selected: Bakery");
                    return "Bakery"; 
                }
                case 6 -> { 
                    System.out.println("Category selected: Grains & Pasta");
                    return "Grains & Pasta"; 
                }
                case 7 -> { 
                    System.out.println("Category selected: Beverages");
                    return "Beverages"; 
                }
                case 8 -> { 
                    System.out.println("Category selected: Snacks");
                    return "Snacks"; 
                }
                case 9 -> { 
                    System.out.println("Category selected: Frozen Foods");
                    return "Frozen Foods"; 
                }
                case 10 -> { 
                    System.out.println("Category selected: Condiments & Spices");
                    return "Condiments & Spices"; 
                }
                case 11 -> { 
                    System.out.println("Category selected: Canned Goods");
                    return "Canned Goods"; 
                }
                case 12 -> { 
                    String customCategory = getStringInput("Enter custom category: ");
                    String finalCategory = customCategory.isEmpty() ? "Other" : customCategory;
                    System.out.println("Category selected: " + finalCategory);
                    return finalCategory;
                }
                default -> System.out.println("Invalid choice! Please select 1-12.");
            }
        }
    }
    
    private void viewAllItems() {
        System.out.println("\n--- View All Items ---");
        System.out.println("1. Sort by Expiry Date");
        System.out.println("2. Sort by Category");
        System.out.println("3. Sort by Name");
        
        int choice = getIntInput("Choose sorting option: ");
        List<GroceryItem> items;
        
        switch (choice) {
            case 1 -> items = groceryManager.sortByExpiryDate();
            case 2 -> items = groceryManager.sortByCategory();
            case 3 -> items = groceryManager.sortByName();
            default -> {
                System.out.println("Invalid choice, defaulting to expiry date sort.");
                items = groceryManager.sortByExpiryDate();
            }
        }
        
        displayItems(items, "All Grocery Items");
    }
    
    private void viewExpiringSoon() {
        System.out.println("\n--- Expiry Alerts ---");
        
        List<GroceryItem> expired = groceryManager.getExpiredItems();
        List<GroceryItem> expiringSoon = groceryManager.getExpiringSoonItems();
        
        if (!expired.isEmpty()) {
            displayItems(expired, "EXPIRED ITEMS");
        }
        
        if (!expiringSoon.isEmpty()) {
            displayItems(expiringSoon, 
                "ITEMS EXPIRING WITHIN " + groceryManager.getAlertThresholdDays() + " DAYS");
        }
        
        if (expired.isEmpty() && expiringSoon.isEmpty()) {
            System.out.println("No items expiring soon!");
        }
    }
    
    private void searchItems() {
        System.out.println("\n--- Search Items ---");
        System.out.println("1. Search by Name");
        System.out.println("2. Search by Category");
    
        int choice = getIntInput("Choose search type: ");
        List<GroceryItem> results;
    
        switch (choice) {
            case 1 -> {
                String name = getStringInput("Enter item name to search: ");
                results = groceryManager.searchByName(name);
            }
            case 2 -> {
                // Show available categories
                List<String> availableCategories = groceryManager.getAvailableCategories();
                System.out.println("\nAvailable Categories:");
                for (int i = 0; i < availableCategories.size(); i++) {
                    System.out.println((i + 1) + ". " + availableCategories.get(i));
                }
            
                int categoryChoice = getIntInput("\nChoose category (1-" + availableCategories.size() + "): ");
                if (categoryChoice < 1 || categoryChoice > availableCategories.size()) {
                    System.out.println("Invalid category choice!");
                    return;
                }
            
                String category = availableCategories.get(categoryChoice - 1);
                results = groceryManager.searchByCategory(category);
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }
    
        displayItems(results, "Search Results (" + results.size() + " items found)");
    }
    private void deleteItem() {
        System.out.println("\n--- Delete Item ---");
        String name = getStringInput("Enter item name to delete: ");
        
        if (groceryManager.deleteItem(name)) {
            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Item not found!");
        }
    }
    
    private void showStatistics() {
        System.out.println("\n--- Statistics ---");
        Map<String, Object> stats = groceryManager.getStatistics();
        
        System.out.println("Total Items: " + stats.get("totalItems"));
        System.out.println("Expired Items: " + stats.get("expiredItems"));
        System.out.println("Expiring Soon: " + stats.get("expiringSoon"));
        
        @SuppressWarnings("unchecked")
        Map<String, Long> categoryDist = (Map<String, Long>) stats.get("categoryDistribution");
        if (!categoryDist.isEmpty()) {
            System.out.println("\nCategory Distribution:");
            categoryDist.forEach((category, count) -> 
                System.out.printf("  %-15s: %d items%n", category, count));
        }
    }
    
    private void manageSettings() {
        System.out.println("\n--- Settings ---");
        System.out.println("Current alert threshold: " + groceryManager.getAlertThresholdDays() + " days");
        int newThreshold = getIntInput("Enter new alert threshold (days): ");
        
        if (newThreshold > 0) {
            groceryManager.setAlertThresholdDays(newThreshold);
            System.out.println("Alert threshold updated to " + newThreshold + " days");
        } else {
            System.out.println("Threshold must be positive!");
        }
    }
    
    private void createBackup() {
        groceryManager.createBackup();
        System.out.println("Backup created successfully!");
        System.out.println("Backup saved as: data/items_backup.txt");
    }
    
    // Utility methods for input handling
    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }
    
    private LocalDate getDateInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }
    }
    
    private void displayItems(List<GroceryItem> items, String title) {
        if (items.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        
        System.out.println("\n" + title);
        System.out.println("-".repeat(85));
        System.out.printf("%-10s | %-15s | %-15s | %-12s | %-12s | %s%n", 
                         "Status", "Name", "Category", "Purchase", "Expiry", "Qty");
        System.out.println("-".repeat(85));
        
        for (GroceryItem item : items) {
            String status = "        "; // 8 spaces for alignment
            if (item.isExpired()) {
                status = "EXPIRED ";
            } else if (item.isExpiringSoon(groceryManager.getAlertThresholdDays())) {
                status = "ALERT   ";
            }
            
            System.out.printf("%-10s | %-15s | %-15s | %-12s | %-12s | %3d%n",
                status,
                item.getName().length() > 15 ? item.getName().substring(0, 12) + "..." : item.getName(),
                item.getCategory().length() > 15 ? item.getCategory().substring(0, 12) + "..." : item.getCategory(),
                item.getPurchaseDate(),
                item.getExpiryDate(),
                item.getQuantity()
            );
        }
        System.out.println("-".repeat(85));
        System.out.println("Total: " + items.size() + " items");
    }
}
