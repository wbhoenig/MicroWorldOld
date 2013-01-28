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
public interface Expression {
    
    ArrayList<Expression> childExpressions();
    
    double value();
    
    Expression derivative(Variable wrt);
    
    Expression copy();
    
    // Keeps the variables as-is.
    Expression shallowCopy();
    
    boolean number();
    
    ArrayList<Variable> childVariables();
    
}