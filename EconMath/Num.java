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
public class Num implements Expression {
    
    public double value;
    
    public Num() {
        
        value = 0;
        
    }
    
    public Num(double value) {
        
        this.value = value;
        
    }
    
    public Expression derivative(Variable wrt) {
        
        return new Num(0);
        
    }
    
    @Override
    public double value() {
        
        return value;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        return new ArrayList<Expression>(0);
        
    }
    
    public Num copy() {
        
        return new Num(value);
        
    }
    
    public Num shallowCopy() {
        
        return new Num(value);
        
    }
    
    public boolean number() {
        
        return true;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        return new ArrayList<Variable>(0);
        
    }
    
}
