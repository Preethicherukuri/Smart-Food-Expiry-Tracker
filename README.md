# 🛒 Smart Food Expiry Tracker (S.E.T.)

A Java-based console application that helps users track groceries, monitor expiry dates, get alerts, organize items by category, and maintain backups — all stored locally with file handling.

This project demonstrates:
- Clean OOP design
- Modular package structure
- File handling, data persistence
- Sorting, searching, statistics, and user menus
- Real-world Java skills suitable for resumes & internships

## 📂 Project Structure

```
src/
├── model/
│    └── GroceryItem.java
├── service/
│    └── GroceryManager.java
├── ui/
│    └── MenuUI.java
└── util/
     └── FileHandler.java

data/
├── items.txt
└── items_backup.txt

Main.java
README.md
```
## ✨ Features

### 🧺 Grocery Management
- Add items with name, category, purchase date, expiry date, quantity
- Delete items by name
- Browse or search groceries anytime

### ⏰ Expiry Tracking
- Auto-alerts on startup for:
  - Expired items
  - Items expiring soon (within configurable threshold)
- Highlighted status in tables: **EXPIRED**, **ALERT**, normal

### 📊 Statistics Dashboard
- Total items
- Expired count
- Expiring soon count
- Category-wise item distribution

### 🔍 Search & Sorting
- Search by:
  - Item name
  - Category
- Sort items by:
  - Expiry date
  - Category
  - Name

### ⚙️ Settings
- Change expiry alert threshold (default: 3 days)

### 💾 Backup & Persistence
- Auto-saving to `items.txt`
- One-click backup to `items_backup.txt`

## 🛠️ Technologies Used

```
Java 17+
OOP (Classes, Encapsulation, Packages)
File I/O (BufferedReader, PrintWriter)
Collections API
Streams & Lambdas
LocalDate + ChronoUnit
```

## ▶️ How to Run

### Option 1 — Using an IDE (IntelliJ/Eclipse/VSCode)

```
Clone this repository

Import as a Java project

Run Main.java


```
### Option 2 — Compile & Run via Terminal

```
javac Main.java
java Main
```

## 📁 Data Storage

| File            | Purpose                    |
|-----------------|----------------------------|
| `items.txt`     | Active storage for all groceries |
| `items_backup.txt` | Manual backup created via menu |

## 🧩 Object-Oriented Design

1. **`model/GroceryItem.java`**
   - Represents a single grocery entry
   - Name & Category, Purchase & Expiry dates, Quantity
   - Methods to check: Expiring soon, Already expired

2. **`service/GroceryManager.java`**
   - Handles core logic: Add/delete items
   - Sorting & searching, Expiry detection
   - Statistics generation, File interactions via util

3. **`ui/MenuUI.java`**
   - Interactive menus & input handling
   - Alerts & pretty table-style outputs

4. **`util/FileHandler.java`**
   - Reading/writing data
   - Creating backups
   - Ensuring data folder exists

## 🎮 Demo (Console UI Preview)

```
============================================
SMART FOOD EXPIRY TRACKER (S.E.T)
Today's Date: 2025-11-27

*** ALERTS ***
[ALERT] 2 item(s) expiring within 3 days!

--------------- MAIN MENU -----------------

Add Grocery Item

View All Items

View Expiring Soon

Search Items

Delete Item

Statistics

Settings

Create Backup

Exit
```


