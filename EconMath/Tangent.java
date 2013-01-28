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
public class Tangent implements Expression {
    
    public Expression inside;
    private boolean radians;
    
    public Tangent() {
        
        this.inside = null;
        this.radians = true;
        
    }
    
    public Tangent(Expression inside) {
        
        this.inside = inside;
        this.radians = true;
        
    }
    
    public Tangent(boolean radians) {
        
        this.inside = null;
        this.radians = radians;
        
    }
    
    public Tangent(Expression inside, boolean radians) {
        
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
        
        return Math.tan(inside);
        
    }
    
    public Product derivative(Variable wrt) {
        
        // Ye olde chain rule
        Product output = new Product(2);
        
        output.components.add(0, this.inside.derivative(wrt));
        
        // Now to create sec^2(x)
        Power secant = new Power();
        secant.base = new Cosine(this.inside, this.radians);
        secant.exponent = new Num(-2.0);
        output.components.add(1, secant);
        
        return output;
        
    }
    
    public Tangent copy() {
        
        return new Tangent(this.inside.copy(), this.radians);
        
    }
    
    public Tangent shallowCopy() {
        
        return new Tangent(this.inside.shallowCopy(), this.radians);
        
    }
    
    public boolean number() {
        return false;
    }
    
    public ArrayList<Variable> childVariables() {
        
        return this.inside.childVariables();
        
    }
    
}