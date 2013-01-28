// The big fellow.

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

import java.util.ArrayList;
import EconMath.*;
/**
 *
 * @author will
 */
public class Economy {
    
    // All of the stuff
    private ArrayList<Asset> assets;
    private ArrayList<Company> companies;
    private ArrayList<Debt> debts;
    private ArrayList<Good> goods;
    private ArrayList<Household> households;
    private ArrayList<Input> inputs;
    private ArrayList<Job> jobs;
    private ArrayList<Person> people;
    private ArrayList<Recipe> recipes;
    
    private ArrayList<Variable> variables;
    
    // Set true when they're linked.
    //Set false when anything is done that could un-link them.
    private boolean variablesLinked;
    
    // Information on the running of things
    private int year;
        // 
    
    public Economy() {
        
        this.clearLists();
        this.year = 0;
        
    }
    
    public Economy(int year) {
        
        this.clearLists();
        this.year = year;
        
    }
    
    // The public method that really matters.
    // return false if something dies
    // True if nothing does
    public boolean advance() {
        
        this.year += 1;
        
        return true;
        
    }
    
    public int getYear() {
        return this.year;
    }
    
    // Private helper methods
    
    /* CATEGORY:
     *  HELPER METHODS FOR CONSTRUCTORS
     */
    // Directs all of the arraylists at the top to null pointers
    private void clearLists() {
        
        this.assets = null;
        this.companies = null;
        this.debts = null;
        this.goods = null;
        this.households = null;
        this.inputs = null;
        this.jobs = null;
        this.people = null;
        this.recipes = null;
        
        this.variablesLinked = false;
        
    }
    
    /* CATEGORY:
     *  HELPER METHODS WITH MULTIPLE POTENTIAL USES
     */
    
    // Creates an entry in variables for each good and job
    // In other words, all possible things that could serve
    // as variables.
    private void linkVariables() {
        
        // Check to see if this has already been done.
        if (this.variablesLinked == true) { return; }
        
        this.variables = new ArrayList<Variable>(this.goods.size() + this.jobs.size());
        
        // Copy over jobs
        // Best be no duplicates! Our wonderful methods earlier help with that.
        // May want to make this a hash table later for that reason.
        
        // j is current position through the new variables arraylist
        int j = 0;
        /* I'm willing to take the risk of declaring the arraylist the size
        /* listed above, because the emergency deletions in the for loop below
        * should actually never happen.
        */
        // Go through jobs
        for (int i = 0; i < this.jobs.size(); i += 1) {
            
            Job current = this.jobs.get(i);
            if (current.isVariable()) {
                
                this.jobs.remove(i);
                
            }
            else {
                
                Variable add = new Variable();
                add.setInput(current);
                this.variables.add(j, add);
                j += 1;
                
            }
            
        }
        
        // Now go through goods
        for (int i = 0; i < this.goods.size(); i += 1) {
            
            // Much the same code as above
            Good current = this.goods.get(i);
            if (current.isVariable()) {
                
                this.goods.remove(i);
                
            }
            else {
                
                Variable add = new Variable();
                add.setInput(current);
                this.variables.add(j, add);
                j += 1;
                
            }
            
        }
        
        // NOTE: MAY WANT TO MAKE AN ARRAYLIST OF INPUTS IN THE FUTURE
        // AND BASE THIS METHOD OFF OF THAT
        
        this.variablesLinked = true;
        
    }
    
}