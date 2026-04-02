// =============================================
// FILE: Investment.java
// PURPOSE: Parent/Base class for all investments
// CONCEPTS: Inheritance, Abstract Class
// =============================================

package stocktracker;

import java.time.LocalDate;

// abstract class = cannot create object directly
// it is only used as a parent for other classes
public abstract class Investment {

    // Common fields every investment has
    // protected = accessible by this class AND subclasses
    protected String symbol;
    protected int quantity;
    protected double purchasePrice;
    protected LocalDate purchaseDate;

    // ── CONSTRUCTOR ────────────────────────────────────────────────
    // Called by child class using super()
    public Investment(String symbol, int quantity, double purchasePrice, LocalDate purchaseDate) {
        this.symbol        = symbol.toUpperCase();
        this.quantity      = quantity;
        this.purchasePrice = purchasePrice;
        this.purchaseDate  = purchaseDate;
    }

    // ── GETTERS ────────────────────────────────────────────────────
    public String    getSymbol()        { return symbol; }
    public int       getQuantity()      { return quantity; }
    public double    getPurchasePrice() { return purchasePrice; }
    public LocalDate getPurchaseDate()  { return purchaseDate; }

    // ── ABSTRACT METHOD ────────────────────────────────────────────
    // No body here — every subclass MUST write their own version
    // This is what enables Dynamic Method Dispatch
    public abstract double getCurrentPrice();
    public abstract double getCurrentValue();

    // ── COMMON METHOD shared by ALL subclasses ─────────────────────
    // Subclasses get this for free through inheritance
    public double getTotalPurchaseCost() {
        return quantity * purchasePrice;
    }

    public double getProfitLoss() {
        return getCurrentValue() - getTotalPurchaseCost();
    }

    public double getPercentageChange() {
        if (getTotalPurchaseCost() == 0) return 0;
        return (getProfitLoss() / getTotalPurchaseCost()) * 100;
    }

    // ── toString ───────────────────────────────────────────────────
    @Override
    public String toString() {
        return String.format(
            "Investment{symbol='%s', quantity=%d, purchasePrice=%.2f}",
            symbol, quantity, purchasePrice
        );
    }
}