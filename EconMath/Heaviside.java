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
public class Heaviside implements Expression {
    
    public Variable variable;
    public Expression point;
    
    public Heaviside() {
        
        point = new Num(0.0);
        variable = null;
        
    }
    
    public Heaviside(Expression point) {
        
        this.point = point;
        variable = null;
        
    }
    
    public Heaviside(double number) {
        
        this.point = new Num(number);
        variable = null;
        
    }
    
    public Heaviside(Variable variable) {
        
        this.variable = variable;
        this.point = new Num(0.0);
        
    }
    
    public Heaviside(Variable variable, Expression point) {
        
        this.variable = variable;
        this.point = point;
        
    }
    
    public Heaviside(Variable variable, double number) {
        
        this.variable = variable;
        this.point = new Num(number);
        
    }
    
    public double value() {
        
        if (this.variable.value() >= this.point.value()) {
            return 1.0;
        }
        return 0.0;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        ArrayList<Expression> output = new ArrayList<Expression>(0);
        output.add(this.point);
        output.add(this.variable);
        return output;
        
    }
    
    public Expression derivative(Variable wrt) {
        
        if (!wrt.equals(this.variable)) {
            return new Num(0.0);
        }
        
        if (this.variable.value() == this.point.value()) {
            
            return new Num(Double.POSITIVE_INFINITY);
            
        }
        
        return new Num(0.0);
        
    }
    
    // Bad idea. Somewhat disastrous.
    public Heaviside copy() {
        
        return new Heaviside(this.variable.copy(), this.point.copy());
        
    }
    
    public Heaviside shallowCopy() {
        
        return new Heaviside(this.variable.shallowCopy(), this.point.shallowCopy());
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        output.add(this.variable);
        output.addAll(this.point.childVariables());
        return output;
        
    }
    
}