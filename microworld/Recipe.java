package microworld;

import EconMath.*;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class Recipe {
    
    // The big one. The production function.
    private Expression function;
    private Good good;
    
    private ArrayList<JobVariable> job_vars;
    private ArrayList<GoodVariable> good_vars;
    
    public Recipe() {
        
        this.function = null;
        this.good = null;
        this.job_vars = new ArrayList<JobVariable>(0);
        this.good_vars = new ArrayList<GoodVariable>(0);
        
    }
    
    public Recipe(Expression function) {
        
        this.function = function;
        this.job_vars = new ArrayList<JobVariable>(0);
        this.good_vars = new ArrayList<GoodVariable>(0);
        
    }
    
    public Recipe(Good good) {
        
        this.function = null;
        this.good = good;
        this.job_vars = new ArrayList<JobVariable>(0);
        this.good_vars = new ArrayList<GoodVariable>(0);
        
    }
    
    public Recipe(Expression function, Good good) {
        
        this.function = function;
        this.good = good;
        this.job_vars = new ArrayList<JobVariable>(0);
        this.good_vars = new ArrayList<GoodVariable>(0);
        
    }
    
    public void setFunction(Expression function) {
        
        this.function = function;
        
    }
    
    public Expression getFunction() {
        
        return this.function;
        
    }
    
    public void setVariable(Variable variable, Job job) {
        
        this.flushVariable(variable);
        
        JobVariable addition = new JobVariable();
        addition.variable = variable;
        addition.job = job;
        this.job_vars.add(addition);
        
    }
    
    public void setVariable(Variable variable, Good good) {
        
        this.flushVariable(variable);
        
        GoodVariable addition = new GoodVariable();
        addition.variable = variable;
        addition.good = good;
        this.good_vars.add(addition);
        
    }
    
    // Get rid of a variable's entry if it already exists
    private void flushVariable(Variable variable) {
        
        int delete = searchJobVariable(variable);
        if (delete != -1) {
            this.job_vars.remove(delete);
        }
        
        delete = searchGoodVariable(variable);
        if (delete != -1) {
            this.good_vars.remove(delete);
        }
        
    }
    
    // Check to see if there's already an entry for a job
    private int searchJobVariable(Variable key) {
        
        for (int i = 0; i < this.job_vars.size(); i += 1) {
            
            if (this.job_vars.get(i).variable.equals(key)) {
                return i;
            }
            
        }
        
        return -1;
        
    }
    
    // And for a good
    private int searchGoodVariable(Variable key) {
        
        for (int i = 0; i < this.job_vars.size(); i += 1) {
            
            if (this.good_vars.get(i).variable.equals(key)) {
                return i;
            }
            
        }
        
        return -1;
        
    }
    
    private class JobVariable {
        
        public Job job;
        public Variable variable;
        
    }
    
    private class GoodVariable {
        
        public Good good;
        public Variable variable;
        
    }
    
    public Expression costFunction() {
        
        Sum output = new Sum();
        
        for (int i = 0; i < this.good_vars.size(); i += 1) {
            
            Product addition = new Product();
            addition.components.add(this.good_vars.get(i).variable);
            // Works for now
            addition.components.add(new Num(this.good_vars.get(i).good.marginalCost()));
            output.components.add(addition);
            
            
        }
        
        for (int i = 0; i < this.job_vars.size(); i += 1) {
            
            Product addition = new Product();
            addition.components.add(this.job_vars.get(i).variable);
            addition.components.add(new Num(this.job_vars.get(i).job.marginalCost()));
            output.components.add(addition);
            
        }
        
        return output;
        
    }
    
    public Expression revenueFunction() {
        
        Product output = new Product();
        
        // Price
        output.components.add(new Num(this.good.marginalCost()));
        // Times quantity
        output.components.add(this.function);
        
        return output;
        
    }
    
    public Expression profitFunction() {
        
        Sum output = new Sum();
        
        output.components.add(this.revenueFunction());
        
        Product cost = new Product();
        // Make the cost negative and add it
        cost.components.add(new Num(-1.0));
        cost.components.add(this.costFunction());
        output.components.add(cost);
        
        return output;
        
    }
    
}
