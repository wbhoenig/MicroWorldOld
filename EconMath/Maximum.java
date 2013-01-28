/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package EconMath;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class Maximum implements Expression {
    
    public ArrayList<Expression> components;
    
    public Maximum() {
        
        this.components = new ArrayList<Expression>(0);
        
    }
    
    public Maximum(int size) {
        
        this.components = new ArrayList<Expression>(size);
        
    }
    
    public Maximum(ArrayList<Expression> components) {
        
        this.components = new ArrayList<Expression>(components.size());
        for (int i = 0; i < components.size(); i += 1) {
            this.components.add(i, components.get(i));
        }
        
    }
    
    public Maximum(Expression zero, Expression one) {
        
        this.components = new ArrayList<Expression>(2);
        this.components.add(0, zero);
        this.components.add(1, one);
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        ArrayList<Expression> output = new ArrayList<Expression>(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            output.add(i, this.components.get(i));
        }
        
        return output;
        
    }
    
    public double value() {
        
        double output = this.components.get(0).value();
        
        for (int i = 1; i < this.components.size(); i += 1) {
            
            double current = this.components.get(i).value();
            if (current > output) {
                output = current;
            }
            
        }
        
        return output;
        
    }
    
    public Num derivative(Variable wrt) {
        
        return new Num(0.0);
        
    }
    
    public Maximum copy() {
        
        Maximum copy = new Maximum(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            copy.components.add(i, this.components.get(i).copy());
            
        }
        
        return copy;
        
    }
    
    public Maximum shallowCopy() {
        
        Maximum copy = new Maximum(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            copy.components.add(i, this.components.get(i).shallowCopy());
            
        }
        
        return copy;
        
    }
    
    public boolean number() {
        return false;
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            output.addAll(this.components.get(i).childVariables());
            
        }
        
        return output;
        
    }
    
}