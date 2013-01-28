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
public class Power implements Expression {
    
    
    public Expression base;
    public Expression exponent;
    
    public Power() {
        
        this.base = new Num(0);
        this.exponent = new Num(0);
        
    }
    
    public Power(double base, double exponent) {
        
        this.base = new Num(base);
        this.exponent = new Num(exponent);
        
    }
    
    public Power(Expression base, Expression exponent) {
        
        this.base = base;
        this.exponent = exponent;
        
    }
    
    public double value() {
        
        // Somewhat recursive, this is!
        return Math.pow(base.value(), exponent.value());
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        ArrayList<Expression> ret = new ArrayList<Expression>(2);
        ret.set(0, this.base);
        ret.set(1, this.exponent);
        return ret;
        
    }
    
    public Power copy() {
        
        return new Power(this.base, this.exponent);
        
    }
    
    public Power shallowCopy() {
        
        Power output = new Power();
        
        output.base = this.base.shallowCopy();
        output.exponent = this.exponent.shallowCopy();
        
        return output;
        
    }
    
    public Product derivative(Variable wrt) {
        
        Product ret = new Product(0);
        
        /* Three components, to satisfy the chain rule:
         * 1) Old Exponent
         * 2) Base to new exponent
         * 3) Derivative of base
         */
        
        // Oy vey
        Power newPower = new Power(this.base, new Sum(this.exponent, new Num(-1)));
        ret = new Product(this.exponent, newPower, this.base.derivative(wrt));
        
        return ret;
        /*ret.components.add(0, exponent);
        // We have to make a Sum that is the old exponent minus one
        ArrayList<Expression> sumComponents = new ArrayList<Expression>(2);
        sumComponents.add(0, this.exponent);
        sumComponents.add(1, new Num(-1));
        ret.components.add(1, new Power(base, new Sum(sumComponents)));
        ret.components.add(2, this.base.derivative(wrt));
        System.out.println(ret.value() + "\n");
        return ret;*/
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        
        output.addAll(this.base.childVariables());
        output.addAll(this.exponent.childVariables());
        
        for (int i = output.size() - 1; i >= 0; i -= 1) {
            
            for (int j = i - 1; j >= 0; j -= 1) {
                
                if (output.get(i).equals(output.get(j))) {
                    
                    output.remove(i);
                    break;
                    
                }
                
            }
            
        }
        
        return output;
        
    }
    
}