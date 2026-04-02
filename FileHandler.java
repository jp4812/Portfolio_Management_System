// =============================================
// FILE: FileHandler.java
// CONCEPT: File Handling
// =============================================

package stocktracker;

import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileHandler {

    // SAVE portfolio summary to a text file
    public static void saveToFile(Portfolio portfolio) {

        // filename is based on portfolio name
        String filename = portfolio.getPortfolioName().replace(" ", "_") + "_report.txt";

        try {
            FileWriter fw = new FileWriter(filename);

            fw.write("========================================\n");
            fw.write("  PORTFOLIO REPORT\n");
            fw.write("  Name: " + portfolio.getPortfolioName() + "\n");
            fw.write("========================================\n");

            // write each stock details
            for (Stock stock : portfolio.getAllStocks()) {
                fw.write("Symbol        : " + stock.getSymbol() + "\n");
                fw.write("Shares        : " + stock.getShares() + "\n");
                fw.write("Purchase Price: $" + String.format("%.2f", stock.getPurchasePrice()) + "\n");
                fw.write("Total Cost    : $" + String.format("%.2f", stock.getTotalPurchaseCost()) + "\n");
                fw.write("----------------------------------------\n");
            }

            fw.write("Total Invested : $" + String.format("%.2f", portfolio.getTotalPurchaseCost()) + "\n");
            fw.write("Current Value  : $" + String.format("%.2f", portfolio.getTotalCurrentValue()) + "\n");
            fw.write("Profit / Loss  : $" + String.format("%.2f", portfolio.getTotalProfitLoss()) + "\n");
            fw.write("========================================\n");

            fw.close(); // always close the file when done

            System.out.println("Report saved to: " + filename);

        } catch (IOException e) {
            // IOException is thrown if something goes wrong with the file
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    // READ and print the saved file
    public static void readFromFile(String filename) {
        System.out.println("\n--- Reading from file: " + filename + " ---");

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

            br.close(); // always close the file when done

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
