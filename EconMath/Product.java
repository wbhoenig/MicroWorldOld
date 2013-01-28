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
public class Product implements Expression {
    
    public ArrayList<Expression> components;
    
    public Product() {
        
        this.components = new ArrayList<Expression>(0);
        
    }
    
    public Product(int num) {
        
        this.components = new ArrayList<Expression>(num);

        
    }
    
    public Product(ArrayList<Expression> components) {
        
        this.components = components;
        
    }
    
    public Product(Expression... input) {
        
        this.components = new ArrayList<Expression>(0);
        //System.out.println(this.components.size() + " " + input.length + "\n");
        for (int i = 0; i < input.length; i += 1) {
            this.components.add(input[i]);
        }
        
    }
    
    public double value() {
        
        if (components.size() > 0) {
            
            double ret = 1;
            
            for (int i = 0; i < components.size(); i += 1) {
                
                // We can just exit now if it's zero!
                double current = components.get(i).value();
                if (current == 0) {
                    
                    return 0;
                    
                }
                
                ret *= current;
                
            }
            return ret;
            
        }
        return 0;
        
    }
    
    public Sum derivative(Variable wrt) {
        
        Sum ret = new Sum(this.components.size());
        
        // Product Rule, here we come
        for (int i = 0; i < this.components.size(); i += 1) {
            
            Product temp = new Product(this.components.size());
            for (int j = 0; j < this.components.size(); j += 1) {
                
                if (j == i) {
                    
                    temp.components.add(j, this.components.get(j).derivative(wrt));
                    
                }
                else {
                    
                    temp.components.add(j, this.components.get(j));
                    
                }
                
            }
            // Oy vey! That's one term of the product rule!
            ret.components.add(i, temp.copy());
            
        }
        
        return ret;
        
    }
    
    public Expression copy() {
        
        Product ret = new Product(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            // Kill this whole enterprise if multiplying by zero
            if (this.components.get(i).number()) {
                
                if (this.components.get(i).value() == 0) {
                    
                    return new Num(0);
                    
                }
 
            }
            
            ret.components.add(i, this.components.get(i).copy());
            
        }
        
        return ret;
        
    }
    
    public Expression shallowCopy() {
        
        Product ret = new Product(this.components.size());
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            // Kill this whole enterprise if multiplying by zero
            if (this.components.get(i).number()) {
                
                if (this.components.get(i).value() == 0) {
                    
                    return new Num(0);
                    
                }
 
            }
            
            ret.components.add(i, this.components.get(i).shallowCopy());
            
        }
        
        return ret;
        
        
    }
    
    public boolean number() {
        
        return false;
        
    }
    
    public ArrayList<Expression> childExpressions() {
        
        return components;
        
    }
    
    public ArrayList<Variable> childVariables() {
        
        ArrayList<Variable> output = new ArrayList<Variable>(0);
        
        for (int i = 0; i < this.components.size(); i += 1) {
            
            output.addAll(this.components.get(i).childVariables());
            
        }
        
        for (int i = output.size() - 1; i >= 0; i -= 1) {
            
            for (int j = i - 1; j >= 0; j -= 1) {
                
                if (output.get(i).equals((Variable)output.get(j))) {
                    
                    output.remove(i);
                    break;
                    
                }
                
            }
            
        }
        
        return output;
        
    }
    
}