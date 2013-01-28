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
public class NatLog implements Expression {
    
    public Expression inside;
    
    public NatLog() {
        
        inside = new Num(1);
        
    }
    
    public NatLog(Expression inside) {
        
        inside = inside.copy();
        
    }
    
    public double value() {
        
        return Math.log(inside.value());
        
    }
    
    public NatLog copy() {
        
        return new NatLog(this.inside);
        
    }
    
    public NatLog shallowCopy() {
        
        NatLog output = new NatLog();
        output.inside = this.inside.shallowCopy();
        return output;
        
    }
    
    public Product derivative(Variable wrt) {
        
        // Derivative of the outside
        Power one = new Power();
        one.base = this.inside;
        one.exponent = new Num(-1);
        
        // Combined with that of the inside.
        Product ret = new Product(2);
        ret.components.set(0, one);
        ret.components.set(1, inside.derivative(wrt));
        return ret;
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        ArrayList<Expression> ret = new ArrayList<Expression>(1);
        ret.set(1, this.inside);
        return ret;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        return this.inside.childVariables();
        
    }
    
}
