package util;

import model.GroceryItem;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String DATA_DIR = "data";
    private static final String DATA_FILE = DATA_DIR + "/items.txt";
    private static final String BACKUP_FILE = DATA_DIR + "/items_backup.txt";
    
    // Ensure data directory exists
    static {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    // Save items to file
    public static void saveItems(List<GroceryItem> items) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (GroceryItem item : items) {
                writer.println(item.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }
    
    // Load items from file
    public static List<GroceryItem> loadItems() {
        List<GroceryItem> items = new ArrayList<>();
        File file = new File(DATA_FILE);
        
        if (!file.exists()) {
            return items;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        items.add(GroceryItem.fromFileString(line));
                    } catch (Exception e) {
                        System.err.println("Error parsing line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading items: " + e.getMessage());
        }
        
        return items;
    }
    
    // Create backup
    public static void createBackup(List<GroceryItem> items) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BACKUP_FILE))) {
            for (GroceryItem item : items) {
                writer.println(item.toFileString());
            }
        } catch (IOException e) {
            System.err.println("Error creating backup: " + e.getMessage());
        }
    }
}
