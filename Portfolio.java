// =============================================
// FILE: Portfolio.java (New Version)
// PURPOSE: Manage multiple stock holdings
// CONCEPTS: Collections (ArrayList), Loops, Aggregation
// =============================================

package stocktracker;

import java.util.ArrayList;
import java.util.List;

public class Portfolio {

    // Private variables
    private String portfolioName;
    private List<Stock> stocks;

    // ── CONSTRUCTOR ────────────────────────────────────────────────
    public Portfolio(String portfolioName) {
        this.portfolioName = portfolioName;
        // Create an empty list to store stocks
        this.stocks = new ArrayList<Stock>();
    }

    // ── STOCK MANAGEMENT METHODS ───────────────────────────────────
    
    // Add a new stock to portfolio
    public void addStock(Stock stock) {
        stocks.add(stock);
        System.out.println("✅ Stock " + stock.getSymbol() + " added to portfolio!");
    }

    // Remove a stock from portfolio
    public boolean removeStock(String symbol) {
        for (int i = 0; i < stocks.size(); i++) {
            if (stocks.get(i).getSymbol().equals(symbol)) {
                stocks.remove(i);
                System.out.println("✅ Stock " + symbol + " removed from portfolio!");
                return true;
            }
        }
        System.out.println("❌ Stock " + symbol + " not found in portfolio!");
        return false;
    }

    // Get a specific stock by symbol
    public Stock getStock(String symbol) {
        for (Stock stock : stocks) {
            if (stock.getSymbol().equals(symbol)) {
                return stock;
            }
        }
        return null;
    }

    // Get all stocks in portfolio
    public List<Stock> getAllStocks() {
        return stocks;
    }

    // ── PORTFOLIO CALCULATION METHODS ──────────────────────────────
    
    // ✓ Calculate total amount invested across all stocks
    public double getTotalPurchaseCost() {
        double total = 0;
        
        // Loop through all stocks and sum their purchase costs
        for (Stock stock : stocks) {
            total += stock.getTotalPurchaseCost();
        }
        
        return total;
    }

    // ✓ Calculate current total portfolio value
    public double getTotalCurrentValue() {
        double total = 0;
        
        // Loop through all stocks and sum their current values
        for (Stock stock : stocks) {
            total += stock.getCurrentValue();
        }
        
        return total;
    }

    // ✓ Calculate overall profit/loss
    // Formula: Total Current Value - Total Purchase Cost
    public double getTotalProfitLoss() {
        return getTotalCurrentValue() - getTotalPurchaseCost();
    }

    // ✓ Calculate overall percentage change
    // Formula: (Total Profit/Loss ÷ Total Purchase Cost) × 100
    public double getTotalPercentageChange() {
        double purchaseCost = getTotalPurchaseCost();
        
        if (purchaseCost == 0) {
            return 0;
        }
        
        double profitLoss = getTotalProfitLoss();
        return (profitLoss / purchaseCost) * 100;
    }

    // ── DISPLAY METHODS ────────────────────────────────────────────
    
    // Get portfolio name
    public String getPortfolioName() {
        return portfolioName;
    }

    // Get number of stocks in portfolio
    public int getStockCount() {
        return stocks.size();
    }

    // Check if portfolio is empty
    public boolean isEmpty() {
        return stocks.isEmpty();
    }

    // String representation of portfolio
    @Override
    public String toString() {
        return String.format("Portfolio{name='%s', stocks=%d}", 
            portfolioName, stocks.size());
    }
}
