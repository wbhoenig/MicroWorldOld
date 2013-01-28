package microworld;

import EconMath.*;
/**
 *
 * @author will
 */
public class Input {
    
    protected double price;
    
    // Quantized goods, because jobs are quantized
    public int old_demand;
    public int new_demand;
    
    protected Variable variable;
    
    public double marginalCost() {
        
        return this.price;
        
    }
    
    public double marginalCost(int unit) {
        
        return this.price;
        
    }
    
    public int oldDemand() {
        
        return this.old_demand;
        
    }
    
    public int newDemand() {
        
        return this.new_demand;
        
    }
    
        
    public void changeDemand(int amount) {
        
        this.new_demand += amount;
        
    }
    
    public void updateDemand() {
        
        this.new_demand = this.new_demand;
        
    }
    
    public boolean isGood() { return false; }
    
    public boolean isJob() { return false; }
    
    public boolean isVariable() {
        
        return (this.variable != null);
        
    }
    
    public void clearVariable() {
        
        if (!isVariable()) { return; }
        
        Variable temp = this.variable;
        this.variable = null;
        temp.clearInput();
        
    }
    
    public Variable getVariable() {
        return this.variable;
    }
    
    public void setVariable(Variable variable) {
        
        if (this.variable == variable) { return; }
        
        Variable temp = this.variable;
        this.variable = variable;
        this.variable.setInput(this);
        temp.clearInput();
        
    }
    
}