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
public class Sum implements Expression {
    
    public ArrayList<Expression> components;
    
    public Sum() {
        
        components = new ArrayList<Expression>(0);
        
    }
    
    public Sum(int num) {
        
        components = new ArrayList<Expression>(num);
        
    }
    
    public Sum(ArrayList<Expression> components) {
        
        this.components = components;
        
    }
    
    public Sum(Expression... inputs) {
        
        this.components = new ArrayList<Expression>(0);
        for (int i = 0; i < inputs.length; i += 1) {
            this.components.add(inputs[i]);
        }
        
    }
    
    public double value() {
        
        double ret = 0;
        
        for (int i = 0; i < components.size(); i += 1) {
            
            ret += components.get(i).value();
            
        }
        
        return ret;
        
    }
    
    public Sum derivative(Variable wrt) {
        
        Sum ret = new Sum();
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            ret.components.add(this.components.get(i).derivative(wrt));
            
        }
        
        return ret;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        return this.components;
        
    }
    
    public Sum copy() {
        
        Sum ret = new Sum(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            ret.components.add(i, this.components.get(i).copy());
            
        }
        
        return ret;
        
    }
    
    public Sum shallowCopy() {
        
        Sum ret = new Sum(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            ret.components.add(i, this.components.get(i).shallowCopy());
            
        }
        
        return ret;
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            output.addAll(this.components.get(i).childVariables());
            
        }
        
        for (int i = output.size(); i >= 0; i -= 1) {
            
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