// =============================================
// FILE: StockTrackerApp.java
// PURPOSE: Main application with menu system
// CONCEPTS: User input, Loops, Conditionals, Error handling, Multithreading
// =============================================

package stocktracker;

import java.util.Scanner;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class StockTrackerApp {

    private Portfolio portfolio;
    private Scanner scanner;
    private Map<String, Portfolio> portfolios;

    // ── CONSTRUCTOR ────────────────────────────────────────────────
    public StockTrackerApp() {
        scanner = new Scanner(System.in);
        portfolio = null;
        portfolios = loadPortfolios();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Portfolio> loadPortfolios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("portfolios.dat"))) {
            return (Map<String, Portfolio>) ois.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }

    private void savePortfolios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("portfolios.dat"))) {
            oos.writeObject(portfolios);
        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // ── MAIN METHOD ────────────────────────────────────────────────
    public static void main(String[] args) {
        StockTrackerApp app = new StockTrackerApp();
        app.run();
    }

    // ── MAIN APPLICATION LOOP ─────────────────────────────────────
    public void run() {
        displayWelcome();

        while (true) {
            if (portfolio == null) {
                showMainMenu();
            } else {
                showPortfolioMenu();
            }

            int choice = getUserChoice();
            handleMenuChoice(choice);
        }
    }

    // ── DISPLAY WELCOME SCREEN ────────────────────────────────────
    private void displayWelcome() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("STOCK TRACKER APPLICATION");
        System.out.println("Simple Stock Portfolio Management");
        System.out.println("=".repeat(60) + "\n");
        System.out.println("Welcome! Let's track your stock investments.\n");
    }

    // ── MAIN MENU (No portfolio created yet) ──────────────────────
    private void showMainMenu() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("MAIN MENU");
        System.out.println("-".repeat(40));
        System.out.println("1. Create New Portfolio");
        System.out.println("2. Access Existing Portfolio");
        System.out.println("0. Exit");
        System.out.print("\nEnter choice: ");
    }

    // ── PORTFOLIO MENU (Portfolio exists) ─────────────────────────
    private void showPortfolioMenu() {
        System.out.println("\n" + "-".repeat(40));
        System.out.println("PORTFOLIO MENU: " + portfolio.getPortfolioName());
        System.out.println("-".repeat(40));
        System.out.println("1. Add New Stock");
        System.out.println("2. View All Stocks");
        System.out.println("3. View Stock Details");
        System.out.println("4. Refresh Prices");
        System.out.println("5. Simulate Live Price Updates (Thread)");  // updated label
        System.out.println("6. Remove Stock");
        System.out.println("7. Return to Main Menu");
        System.out.println("8. Save Report to File");
        System.out.println("0. Exit");
        System.out.print("\nEnter choice: ");
    }

    // ── GET USER CHOICE ───────────────────────────────────────────
    private int getUserChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            return choice;
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            StockDisplay.displayError("Invalid input! Please enter a number.");
            return -1;
        }
    }

    // ── HANDLE MENU CHOICES ───────────────────────────────────────
    private void handleMenuChoice(int choice) {
        if (portfolio == null) {
            handleMainMenuChoice(choice);
        } else {
            handlePortfolioMenuChoice(choice);
        }
    }

    // ── HANDLE MAIN MENU CHOICES ──────────────────────────────────
    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1: createNewPortfolio(); break;
            case 2: accessExistingPortfolio(); break;
            case 0: exitApplication();    break;
            default: StockDisplay.displayError("Invalid choice! Please enter 0, 1, or 2.");
        }
    }

    // ── HANDLE PORTFOLIO MENU CHOICES ─────────────────────────────
    private void handlePortfolioMenuChoice(int choice) {
        switch (choice) {
            case 1: addNewStock();      break;
            case 2: viewAllStocks();    break;
            case 3: viewStockDetails(); break;
            case 4: refreshPrices();    break;
            case 5: simulatePriceUpdates(); break;  // now uses real thread
            case 6: removeStock();      break;
            case 7:
                portfolio = null;
                StockDisplay.displaySuccess("Returned to Main Menu!");
                break;
            case 8: saveReport();       break;
            case 0: exitApplication();  break;
            default: StockDisplay.displayError("Invalid choice! Please enter 0-7.");
        }
    }

    // ── OPTION 1: CREATE NEW PORTFOLIO ────────────────────────────
    private void createNewPortfolio() {
        System.out.print("\nEnter portfolio name: ");
        String name = scanner.nextLine();
        if (portfolios.containsKey(name)) {
            StockDisplay.displayError("Portfolio '" + name + "' already exists!");
            return;
        }
        portfolio = new Portfolio(name);
        portfolios.put(name, portfolio);
        savePortfolios();
        StockDisplay.displaySuccess("Portfolio '" + name + "' created successfully!");
    }

    // ── OPTION 2: ACCESS EXISTING PORTFOLIO ───────────────────────
    private void accessExistingPortfolio() {
        System.out.print("\nEnter portfolio name to access: ");
        String name = scanner.nextLine();
        if (portfolios.containsKey(name)) {
            portfolio = portfolios.get(name);
            StockDisplay.displaySuccess("Accessed portfolio '" + name + "' successfully!");
        } else {
            StockDisplay.displayError("Portfolio '" + name + "' does not exist!");
        }
    }

    // ── OPTION 1/2: ADD NEW STOCK ─────────────────────────────────
    private void addNewStock() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ADD NEW STOCK");
        System.out.println("=".repeat(50));

        System.out.print("Enter stock symbol (e.g., AAPL): ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        if (symbol.isEmpty()) {
            StockDisplay.displayError("Symbol cannot be empty!");
            return;
        }

        System.out.print("Enter number of shares: ");
        int shares = getIntInput();
        if (shares <= 0) {
            StockDisplay.displayError("Number of shares must be positive!");
            return;
        }

        System.out.print("Enter purchase price per share ($): ");
        double purchasePrice = getDoubleInput();
        if (purchasePrice <= 0) {
            StockDisplay.displayError("Purchase price must be positive!");
            return;
        }

        System.out.print("Enter purchase date (YYYY-MM-DD) or press Enter for today: ");
        String dateStr = scanner.nextLine().trim();

        Stock stock;
        if (dateStr.isEmpty()) {
            stock = new Stock(symbol, shares, purchasePrice);
        } else {
            try {
                LocalDate date = LocalDate.parse(dateStr);
                stock = new Stock(symbol, shares, purchasePrice, date);
            } catch (Exception e) {
                StockDisplay.displayError("Invalid date format! Using today's date.");
                stock = new Stock(symbol, shares, purchasePrice);
            }
        }

        portfolio.addStock(stock);
        savePortfolios();
        StockDisplay.displaySuccess("Stock " + symbol + " added! Total cost: $"
                + String.format("%.2f", stock.getTotalPurchaseCost()));
    }

    // ── OPTION 2/4: VIEW ALL STOCKS ───────────────────────────────
    private void viewAllStocks() {
        StockDisplay.displayPortfolio(portfolio);
    }

    // ── OPTION 3: VIEW STOCK DETAILS ──────────────────────────────
    private void viewStockDetails() {
        if (portfolio.isEmpty()) {
            StockDisplay.displayError("Portfolio is empty!");
            return;
        }

        System.out.print("\nEnter stock symbol to view details: ");
        String symbol = scanner.nextLine().trim().toUpperCase();

        Stock stock = portfolio.getStock(symbol);
        if (stock == null) {
            StockDisplay.displayError("Stock " + symbol + " not found in portfolio!");
            return;
        }

        for (int i = 0; i < 3; i++) {
            stock.updatePrice();
            StockDisplay.displayStockDetails(stock);
            if (i < 2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
        }
    }

    // ── OPTION 4: REFRESH PRICES ──────────────────────────────────
    private void refreshPrices() {
        System.out.println("\nRefreshing prices...");
        for (Stock stock : portfolio.getAllStocks()) {
            stock.updatePrice();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // Ignore
        }
        viewAllStocks();
    }

    // ── OPTION 5: SIMULATE LIVE PRICE UPDATES USING A REAL THREAD ──
    private void simulatePriceUpdates() {
        if (portfolio.isEmpty()) {
            StockDisplay.displayError("Portfolio is empty!");
            return;
        }

        System.out.print("\nHow many price updates do you want to see? (3-10): ");
        int iterations = getIntInput();

        if (iterations < 3 || iterations > 10) {
            iterations = 5; // default
        }

        // ── MULTITHREADING: Create PriceUpdater and run it in a thread ──

        // Step 1: Create the PriceUpdater object (implements Runnable)
        PriceUpdater updater = new PriceUpdater(portfolio.getAllStocks(), iterations);

        // Step 2: Wrap it in a Thread
        Thread priceThread = new Thread(updater);
        priceThread.setName("PriceUpdaterThread"); // give the thread a name

        System.out.println("\nMain thread: starting background price updater...");
        System.out.println("Main thread: thread name is → " + priceThread.getName());

        // Step 3: Start the thread — runs PriceUpdater.run() in background
        priceThread.start();

        // Step 4: Main thread waits here until background thread finishes
        try {
            priceThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting.");
        }

        // Step 5: Thread is done — show final portfolio
        System.out.println("Main thread: background thread finished!");
        System.out.println("\nFinal Portfolio State After Live Updates:");
        viewAllStocks();
    }

    // ── OPTION 6: REMOVE STOCK ────────────────────────────────────
    private void removeStock() {
        if (portfolio.isEmpty()) {
            StockDisplay.displayError("Portfolio is empty!");
            return;
        }

        System.out.print("\nEnter stock symbol to remove: ");
        String symbol = scanner.nextLine().trim().toUpperCase();
        if (portfolio.removeStock(symbol)) {
            savePortfolios();
        }
    }

    // ── OPTION 8: SAVE REPORT TO FILE ──────────────────────────
    private void saveReport() {
        FileHandler.saveToFile(portfolio);
        String filename = portfolio.getPortfolioName().replace(" ", "_") + "_report.txt";
        FileHandler.readFromFile(filename);
    }

    // ── OPTION 0: EXIT APPLICATION ────────────────────────────────
    private void exitApplication() {
        savePortfolios();
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Thank you for using Stock Tracker!");
        System.out.println("Happy investing!");
        System.out.println("=".repeat(60) + "\n");
        scanner.close();
        System.exit(0);
    }

    // ── HELPER: GET INTEGER INPUT ─────────────────────────────────
    private int getIntInput() {
        try {
            int value = scanner.nextInt();
            scanner.nextLine();
            return value;
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            StockDisplay.displayError("Please enter a valid number!");
            return 0;
        }
    }

    // ── HELPER: GET DOUBLE INPUT ──────────────────────────────────
    private double getDoubleInput() {
        try {
            double value = scanner.nextDouble();
            scanner.nextLine();
            return value;
        } catch (java.util.InputMismatchException e) {
            scanner.nextLine();
            StockDisplay.displayError("Please enter a valid number!");
            return 0;
        }
    }
}
