/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

import java.util.ArrayList;

/**
 *
 * @author will
 */
public class Good extends Input {
    
    private String name;
    
    private boolean capital;
    private boolean consumer;
    
    private double capitalAge;
    private double consumerAge;
    
    private ArrayList<Household> consumerOwners;
    private ArrayList<Company> capitalOwners;
    
    private int stock;
    
    // Easy functions!
    public boolean isGood() { return true; }
    
    public int getStock() {
        return this.stock;
    }
    
    private void zeroStock() {
        
        this.stock = 0;
        
    }
    
    private void addToStock(int amount) {
        
        if (amount > 0) {
            this.stock += amount;
        }
        
    }
    
    private void removeFromStock(int amount) {
        
        if (amount > 0) {
            this.stock -= amount;
        }
        
    }
    
}