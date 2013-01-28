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

// Perhaps useful for Forier series?
public class Sine implements Expression {
    
    public Expression inside;
    private boolean radians;
    
    public Sine() {
        
        this.inside = null;
        this.radians = true;
        
    }
    
    public Sine(Expression inside) {
        
        this.inside = inside;
        this.radians = true;
        
    }
    
    public Sine(boolean radians) {
        
        this.inside = null;
        this.radians = radians;
        
    }
    
    public Sine(Expression inside, boolean radians) {
        
        this.inside = inside;
        this.radians = radians;
        
    }
    
    public boolean inRadians() {
        
        return this.radians;
        
    }
    
    public boolean isDegrees() {
        
        return !(this.radians);
        
    }
    
    public void setRadians() {
        
        this.radians = true;
        
    }
    
    public void setDegrees() {
        
        this.radians = false;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        ArrayList<Expression> output = new ArrayList<Expression>(1);
        output.add(0, this.inside);
        return output;
        
    }
    
    public double value() {
        
        double inside = this.inside.value();
        
        if (this.radians == false) {
            
            inside = Math.toDegrees(inside);
            
        }
        
        return Math.sin(inside);
        
    }
    
    public Product derivative(Variable wrt) {
        
        // Ye olde chain rule
        Product output = new Product(2);
        
        // derivative of the outside
        output.components.add(0, new Cosine(this.inside, this.radians));
        
        // times derivative of the inside!
        output.components.add(1, this.inside.derivative(wrt));
        
        return output;
        
    }
    
    public Sine copy() {
        
        return new Sine(this.inside.copy(), this.radians);
        
    }
    
    public Sine shallowCopy() {
        
        return new Sine(this.inside.shallowCopy(), this.radians);
        
    }
    
    public boolean number() {
        return false;
    }
    
    public ArrayList<Variable> childVariables() {
        
        return this.inside.childVariables();
        
    }
    
}