/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EconMath;

import java.util.ArrayList;
import microworld.*;

/**
 *
 * @author will
 */
public class Variable implements Expression {
    
    private static int lowestUnusedId = 0;
    
    private int id;
    public double value;
    
    // What number was it evaluated as?
    public int number = -1;
    
    // Link this variable to an input?
    private Input input;
    
    public Variable() {
        
        setId();
        value = 0;
        
    }
    
    public Variable(double value) {
        
        setId();
        this.value = value;
        
    }
    
    // Called by all constructors
    private void setId() {
        
        id = lowestUnusedId;
        lowestUnusedId += 1;
        
    }
    
    public boolean sameVariable(Variable other) {
        
        return (this.id == other.id);
        
    }
    
    public boolean equals(Variable other) {
        
        return (this.id == other.id);
        
    }
    
    public double value() {
        
        return this.value;
        
    }
    
    public Expression derivative(Variable wrt) {
        
        if (this.sameVariable(wrt)) {
            
            return new Num(1);
            
        }
        return new Num(0);
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        return new ArrayList<Expression>(0);
        
    }
    
    public Variable copy() {
        
        return new Variable(this.value);
        
    }
    
    public Variable shallowCopy() {
        
        return this;
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        output.add(this);
        return output;
        
    }
    
    public boolean isInput() {
        
        if (this.input == null) {
            return false;
        }
        
        return true;
        
    }
    
    public Input getInput() {
        
        return this.input;
        
    }
    
    // Could set off a nasty chain reaction.
    // Includes some protection to stop this from going in
    // an infinite circle.
    public void clearInput() {
        
        if (this.isInput() == false) {
            return;
        }
        
        Input temp = this.input;
        this.input = null;
        temp.clearVariable();
        
    }
    
    public void setInput(Input input) {
        
        if (this.input == input) { return; }
        this.clearInput();
        
        this.input = input;
        input.setVariable(this);
        
    }
    
}
