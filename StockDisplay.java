// =============================================
// FILE: StockDisplay.java
// PURPOSE: Handle all display formatting
// CONCEPTS: String formatting, Table display
// =============================================

package stocktracker;

import java.util.List;

public class StockDisplay {

    // Display header for stock portfolio
    public static void displayPortfolioHeader(String portfolioName) {
        System.out.println("\n" + "=".repeat(120));
        System.out.println("📊 STOCK PORTFOLIO: " + portfolioName);
        System.out.println("=".repeat(120));
    }

    // Display table header for stocks
    public static void displayTableHeader() {
        System.out.println(String.format(
            "%-8s %-8s %-15s %-15s %-15s %-15s %-15s %-12s",
            "Symbol", "Shares", "Buy Price", "Total Cost", "Current Price", "Current Value", "Profit/Loss", "% Change"
        ));
        System.out.println("-".repeat(120));
    }

    // Display a single stock row
    public static void displayStockRow(Stock stock) {
        String symbol = stock.getSymbol();
        int shares = stock.getShares();
        double purchasePrice = stock.getPurchasePrice();
        double totalCost = stock.getTotalPurchaseCost();
        double currentPrice = stock.getCurrentPrice();
        double currentValue = stock.getCurrentValue();
        double profitLoss = stock.getProfitLoss();
        double percentageChange = stock.getPercentageChange();

        // Format profit/loss with color indicator
        String profitLossStr = String.format("%.2f", profitLoss);
        String profitLossDisplay = (profitLoss >= 0) ? "+" + profitLossStr : profitLossStr;
        
        // Format percentage with indicator
        String percentageStr = String.format("%.2f%%", percentageChange);
        String percentageDisplay = (percentageChange >= 0) ? "+" + percentageStr : percentageStr;
        
        // Visual indicator
        String indicator = (profitLoss >= 0) ? "✅" : "❌";

        System.out.println(String.format(
            "%-8s %-8d %-15.2f %-15.2f %-15.2f %-15.2f %s%-14s %-12s",
            symbol,
            shares,
            purchasePrice,
            totalCost,
            currentPrice,
            currentValue,
            indicator,
            profitLossDisplay,
            percentageDisplay
        ));
    }

    // Display full portfolio with all stocks
    public static void displayPortfolio(Portfolio portfolio) {
        if (portfolio.isEmpty()) {
            System.out.println("❌ Portfolio is empty! Add stocks first.\n");
            return;
        }

        displayPortfolioHeader(portfolio.getPortfolioName());
        displayTableHeader();

        // Display each stock
        List<Stock> stocks = portfolio.getAllStocks();
        for (Stock stock : stocks) {
            displayStockRow(stock);
        }

        // Display summary
        displayPortfolioSummary(portfolio);
    }

    // Display portfolio summary
    public static void displayPortfolioSummary(Portfolio portfolio) {
        System.out.println("-".repeat(120));
        
        double totalCost = portfolio.getTotalPurchaseCost();
        double totalValue = portfolio.getTotalCurrentValue();
        double totalProfitLoss = portfolio.getTotalProfitLoss();
        double percentageChange = portfolio.getTotalPercentageChange();

        // Format values
        String profitLossStr = String.format("%.2f", totalProfitLoss);
        String profitLossDisplay = (totalProfitLoss >= 0) ? "+" + profitLossStr : profitLossStr;
        String percentageStr = String.format("%.2f%%", percentageChange);
        String percentageDisplay = (percentageChange >= 0) ? "+" + percentageStr : percentageStr;
        String indicator = (totalProfitLoss >= 0) ? "✅ PROFIT" : "❌ LOSS";

        System.out.println(String.format(
            "%-8s %-8s %-15s %-15.2f %-15s %-15.2f %s%-14s %-12s",
            "TOTAL",
            "",
            "",
            totalCost,
            "",
            totalValue,
            indicator + ": ",
            profitLossDisplay,
            percentageDisplay
        ));
        
        System.out.println("=".repeat(120) + "\n");
    }

    // Display individual stock details
    public static void displayStockDetails(Stock stock) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📈 STOCK DETAILS: " + stock.getSymbol());
        System.out.println("=".repeat(60));
        
        System.out.println(String.format("Symbol:              %s", stock.getSymbol()));
        System.out.println(String.format("Shares:              %d", stock.getShares()));
        System.out.println(String.format("Purchase Price:      $%.2f", stock.getPurchasePrice()));
        System.out.println(String.format("Purchase Date:       %s", stock.getPurchaseDate()));
        System.out.println(String.format("Total Purchase Cost: $%.2f", stock.getTotalPurchaseCost()));
        
        System.out.println("\n--- Current Prices (Generated Fresh) ---");
        System.out.println(String.format("Current Price:       $%.2f", stock.getCurrentPrice()));
        System.out.println(String.format("Current Value:       $%.2f", stock.getCurrentValue()));
        System.out.println(String.format("Price Change/Share:  $%.2f", stock.getPriceChangePerShare()));
        
        System.out.println("\n--- Profit/Loss Analysis ---");
        double profitLoss = stock.getProfitLoss();
        double percentage = stock.getPercentageChange();
        String indicator = (profitLoss >= 0) ? "✅ PROFIT" : "❌ LOSS";
        String sign = (profitLoss >= 0) ? "+" : "";
        
        System.out.println(String.format("%s: $%s%.2f (%.2f%%)", 
            indicator, sign, profitLoss, percentage));
        
        System.out.println("=".repeat(60) + "\n");
    }

    // Display error message
    public static void displayError(String message) {
        System.out.println("\n❌ ERROR: " + message + "\n");
    }

    // Display success message
    public static void displaySuccess(String message) {
        System.out.println("\n✅ " + message + "\n");
    }

    // Display info message
    public static void displayInfo(String message) {
        System.out.println("\nℹ️  " + message + "\n");
    }

    // Display separator
    public static void displaySeparator() {
        System.out.println("\n" + "-".repeat(60) + "\n");
    }

    // Display dynamic price update simulation
    public static void displayPriceUpdateSimulation(Stock stock, int iterations) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🔄 PRICE UPDATE SIMULATION: " + stock.getSymbol());
        System.out.println("=".repeat(60));

        System.out.println("Generating " + iterations + " price updates...\n");

        for (int i = 1; i <= iterations; i++) {
            stock.updatePrice();
            double price = stock.getCurrentPrice();
            double value = stock.getCurrentValue();
            double profitLoss = stock.getProfitLoss();
            
            String arrow = "→";
            String indicator = (profitLoss >= 0) ? "✅" : "❌";
            
            System.out.println(String.format(
                "Update #%d: Price $%.2f %s Value: $%.2f, P/L: %s$%.2f",
                i, price, arrow, value, (profitLoss >= 0 ? "+" : ""), profitLoss
            ));

            try {
                Thread.sleep(500);  // Small delay to see updates
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        System.out.println("\n" + "=".repeat(60) + "\n");
    }
}
