// =============================================
// FILE: Stock.java
// PURPOSE: Represents a single stock holding
// CONCEPTS: Inheritance, Encapsulation, Constructors
// =============================================

package stocktracker;

import java.util.Random;
import java.time.LocalDate;
import java.io.Serializable;


public class Stock extends Investment implements Serializable {

    private static final long serialVersionUID = 1L;
    private double basePrice;
    private double currentPrice;
    private static Random random = new Random();
    private static final double PRICE_VARIATION = 0.05; 


    // super() calls the parent Investment constructor
    public Stock(String symbol, int shares, double purchasePrice) {
        super(symbol, shares, purchasePrice, LocalDate.now()); 
        this.basePrice = purchasePrice;
        this.currentPrice = purchasePrice;
    }


    public Stock(String symbol, int shares, double purchasePrice, LocalDate purchaseDate) {
        super(symbol, shares, purchasePrice, purchaseDate); 
        this.basePrice = purchasePrice;
        this.currentPrice = purchasePrice;
    }

  
    // Note: getSymbol(), getShares(), getPurchasePrice(), getPurchaseDate()
    // are all INHERITED from Investment — no need to rewrite them here

    public int getShares() {
        return quantity; 
    }

    // ── DYNAMIC METHOD DISPATCH ────────────────────────────────────
    // Stock provides its OWN version of getCurrentPrice()
    // Java calls THIS version when the object is a Stock at runtime
    @Override
    public double getCurrentPrice() {
        return this.currentPrice;
    }

    public void updatePrice() {
        double variation   = (random.nextDouble() * 2 - 1) * PRICE_VARIATION;
        double newPrice = basePrice * (1 + variation);
        this.currentPrice = Math.round(newPrice * 100.0) / 100.0;
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