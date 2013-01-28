/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cureos.numerics;
import EconMath.*;
import java.util.ArrayList;
import com.cureos.numerics.*;

/**
 *
 * @author will
 */
public class Evaluator implements Calcfc {
    
    public Expression expression;
    public Expression constraint;
    public double constraintEquals;
    
    private boolean activated;
    private Variable[] vars;
    
    public Evaluator() {
        
        this.expression = null;
        this.constraint = null;
        this.constraintEquals = 0;
        activated = false;
        
    }
    
    public Evaluator(Expression expression) {
        
        this.expression = expression;
        this.constraint = null;
        this.constraintEquals = 0;
        activated = false;
        
    }
    
    public Evaluator(Expression expression, Expression constraint) {
        
        this.expression = expression;
        this.constraint = constraint;
        this.constraintEquals = 0;
        activated = false;
        
    }
    
    public Evaluator(Expression expression, Expression constraint, double constraintEquals) {
        
        this.expression = expression;
        this.constraint = constraint;
        this.constraintEquals = constraintEquals;
        activated = false;
        
    }
    
    public double Compute(int n, int m, double[] x, double[] con) {
        
        if (this.expression == null) {
            
            for (int i = 0; i < m; i += 1) {
                con[i] = 0.0;
            }
            return 0.0;
            
        }
        
        if ((this.activated != true) || (this.vars.length != n)) {
            
            activated = true;
            ArrayList<Variable> actual_vars = this.expression.childVariables();
            
            this.vars = new Variable[n];
            for (int i = 0; i < n; i += 1) {
                // We're just assuming that there won't be errors here.
                // There could be, but I intend to implement this correctly
                // And time is of the essence.
                this.vars[i] = actual_vars.get(i);
                actual_vars.get(i).number = i;
                
            }
            
        }
        
        // Loop through all of the variables
        // Best be using the same ones in both equations
        // Or thou art screwed
        for (int i = 0; i < n; i += 1) {
            
            this.vars[i].value = x[i];
            
        }
        double constraint_out = this.constraint.value() - this.constraintEquals;
        // Only allow one constraint. If more then one, spit out the same value to 'em all.
        // But that shouldn't happen. This should cause a sufficient mess to stop me
        // From attempting multiple constraints at the moment. :)
        // Edit: one constraint plus one per variable. Maybe? Deal with later.
        for (int i = 0; i < m; i += 1) {
            
            con[i] = constraint_out;
            
        }
        
        double output = this.expression.value();
        
        if (Double.isNaN(output)) {
            return 0.0;
        }
        
        return output;
        
    }
    
}