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
public class Cosine implements Expression {
    
    public Expression inside;
    private boolean radians;
    
    public Cosine() {
        
        this.inside = null;
        this.radians = true;
        
    }
    
    public Cosine(Expression inside) {
        
        this.inside = inside;
        this.radians = true;
        
    }
    
    public Cosine(boolean radians) {
        
        this.inside = null;
        this.radians = radians;
        
    }
    
    public Cosine(Expression inside, boolean radians) {
        
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
        Product output = new Product(3);
        
        // derivative of the outside
        output.components.add(0, new Sine(this.inside, this.radians));
        
        // times derivative of the inside!
        output.components.add(1, this.inside.derivative(wrt));
        
        // Times -1, since this is cosine
        output.components.add(new Num(-1.0));
        
        return output;
        
    }
    
    public Cosine copy() {
        
        return new Cosine(this.inside.copy(), this.radians);
        
    }
    
    public Cosine shallowCopy() {
        
        return new Cosine(this.inside.shallowCopy(), this.radians);
        
    }
    
    public boolean number() {
        return false;
    }
    
    public ArrayList<Variable> childVariables() {
        
        return this.inside.childVariables();
        
    }
    
}