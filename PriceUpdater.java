// =============================================
// FILE: PriceUpdater.java
// CONCEPT: Multithreading (implements Runnable)
// =============================================

package stocktracker;

import java.util.List;

// By implementing Runnable, this class can run in a separate thread
public class PriceUpdater implements Runnable {

    private List<Stock> stocks;   // list of stocks to update
    private int iterations;       // how many times to update prices

    // Constructor: receives the stock list and number of updates
    public PriceUpdater(List<Stock> stocks, int iterations) {
        this.stocks     = stocks;
        this.iterations = iterations;
    }

    // run() is called automatically when the thread starts
    @Override
    public void run() {
        System.out.println("\n[Thread] Price updater started in background...");
        System.out.println("[Thread] Will update prices " + iterations + " times\n");

        for (int i = 1; i <= iterations; i++) {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("[Thread] Interrupted.");
                return;
            }

            // Print updated price for each stock
            System.out.println("--- Update #" + i + " ---");
            for (Stock stock : stocks) {
                stock.updatePrice();
                double currentPrice = stock.getCurrentPrice();
                double profitLoss   = stock.getProfitLoss();
                String direction    = profitLoss >= 0 ? "UP" : "DOWN";

                System.out.printf("  %-6s | Price: Rs. %-8.2f | P/L: Rs. %.2f (%s)%n",
                        stock.getSymbol(), currentPrice, profitLoss, direction);
            }
            System.out.println();
        }

        System.out.println("[Thread] Price updater finished.\n");
    }
}
