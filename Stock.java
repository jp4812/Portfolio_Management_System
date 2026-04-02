// =============================================
// FILE: Stock.java
// PURPOSE: Represents a single stock holding
// CONCEPTS: Inheritance, Encapsulation, Constructors
// =============================================

package stocktracker;

import java.util.Random;
import java.time.LocalDate;

// 'extends Investment' = Stock INHERITS from Investment
// Stock gets symbol, quantity, purchasePrice, getProfitLoss() etc. for FREE
public class Stock extends Investment {

    // Stock's OWN extra field (not in parent)
    private double basePrice;

    // Random object for price fluctuations
    private static Random random = new Random();

    // Constant for price variation
    private static final double PRICE_VARIATION = 0.05; // ±5%

    // ── CONSTRUCTOR 1 ──────────────────────────────────────────────
    // super() calls the parent Investment constructor
    public Stock(String symbol, int shares, double purchasePrice) {
        super(symbol, shares, purchasePrice, LocalDate.now()); // calls Investment constructor
        this.basePrice = purchasePrice;
    }

    // ── CONSTRUCTOR 2 — with custom date ───────────────────────────
    public Stock(String symbol, int shares, double purchasePrice, LocalDate purchaseDate) {
        super(symbol, shares, purchasePrice, purchaseDate); // calls Investment constructor
        this.basePrice = purchasePrice;
    }

    // ── GETTER ─────────────────────────────────────────────────────
    // Note: getSymbol(), getShares(), getPurchasePrice(), getPurchaseDate()
    // are all INHERITED from Investment — no need to rewrite them here

    public int getShares() {
        return quantity; // quantity is inherited from Investment (protected)
    }

    // ── DYNAMIC METHOD DISPATCH ────────────────────────────────────
    // Stock provides its OWN version of getCurrentPrice()
    // Java calls THIS version when the object is a Stock at runtime
    @Override
    public double getCurrentPrice() {
        double variation   = (random.nextDouble() * 2 - 1) * PRICE_VARIATION;
        double currentPrice = basePrice * (1 + variation);
        return Math.round(currentPrice * 100.0) / 100.0;
    }

    // Stock provides its OWN version of getCurrentValue()
    @Override
    public double getCurrentValue() {
        return quantity * getCurrentPrice();
    }

    // ── Stock specific method ──────────────────────────────────────
    public double getPriceChangePerShare() {
        return getCurrentPrice() - purchasePrice;
    }

    // ── toString — overrides parent's toString ─────────────────────
    @Override
    public String toString() {
        return String.format(
            "Stock{symbol='%s', shares=%d, purchasePrice=%.2f, purchaseDate=%s}",
            symbol, quantity, purchasePrice, purchaseDate
        );
    }
}