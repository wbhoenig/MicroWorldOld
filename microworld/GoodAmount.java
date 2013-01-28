/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

/**
 *
 * @author will
 */
public class GoodAmount {
    
    private Good good;
    private double amount;
    // Round to the nearest integer when actually used, though.
    private int roundedAmount;

    public void setGood(Good good) {
        this.good = good;
    }

    public void setAmount(double amount) {
        this.amount = amount;
        this.roundedAmount = 0;
        double i;
        for (i = amount; i >= 1; i -= 1) {
            this.roundedAmount += 1;
        }
        if (i >= 0.5) {
            this.roundedAmount += 1;
        }
    }

    public Good good() {
        return this.good;
    }

    public double amount() {
        return this.amount;
    }

    public int roundedAmount() {
        return this.roundedAmount;
    }

}