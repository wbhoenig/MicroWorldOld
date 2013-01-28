/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

import java.util.ArrayList;
import EconMath.*;
import com.cureos.numerics.*;

/**
 *
 * @author will
 */
public class Household {
    
    private Person adultOne;
    private Person adultTwo;
    private ArrayList<Person> children;
    private int numChildrenDesired;
    private int members;
    
    private int cash;
    // Just a number for now. Must be between 0.0 and 1.0.
    // If not, it will be rounded.
    private double savingsRate;
    
    private ArrayList<Asset> assets;
    private ArrayList<Debt> debts;
    
    public static ArrayList<Household> householdList;
    
    // The big one
    private Expression utilityFunction;
    
    public static ArrayList<Variable> purchasableGoods;
    
    public Household(Person a1, Person a2) {
        
        adultOne = a1;
        adultTwo = a2;
        
        numChildrenDesired = (int)((adultOne.getNumChildrenDesired() + adultTwo.getNumChildrenDesired()) / 2);
        
        children = new ArrayList<Person>(0);
        members = 2;
        
    }
    
    public Person haveChild() {
        
        Person child = new Person(adultOne, adultTwo);
        children.add(child);
        members += 1;
        return child;
        
    }
    
    public ArrayList<Person> prune() {
        
        ArrayList<Person> emancipated = new ArrayList<Person>(0);
        
        if (adultOne != null) {
            if (!adultOne.getLivingStatus()) {

                adultOne = null;
                members -= 1;
                
                if (adultTwo != null) {
                    
                    adultTwo.widdow();
                    
                }
                

            }
        }
        if (adultTwo != null) {
            if (!adultTwo.getLivingStatus()) {

                adultTwo = null;
                members -= 1;
                
                if (adultOne != null) {
                    
                    adultOne.widdow();
                    
                }

            }
        }
        for (int i = children.size() - 1; i >= 0; i -= 1) {
            
            if (children.get(i).getAge() > 22) {
                
                emancipated.add(children.get(i));
                children.remove(i);
                members -= 1;
                
            }
            
        }
        
        if ((members == 0) && (householdList != null)) {
            
            householdList.remove(this);
            
        }
        
        return emancipated;
        
    }
    
    public boolean childTime() {
        
        // Check to see if adults are within child-bearing age
        // < 50 for women, < 60 for men
        
        if (adultOne == null) {
            
            return false;
            
        }
        if (adultTwo == null) {
            
            return false;
            
        }

        if ((adultOne.getGender() == 0) && adultOne.getAge() > 49) {
            
            return false;
            
        }
        if ((adultOne.getGender() == 1) && adultOne.getAge() > 59) {
            
            return false;
            
        }

        if ((adultTwo.getGender() == 0) && adultTwo.getAge() > 49) {
            
            return false;
            
        }
        if ((adultTwo.getGender() == 1) && adultTwo.getAge() > 59) {
            
            return false;
            
        }
        
        if (numChildrenDesired <= children.size()) {
            
            return false;
            
        }
        
        if (children.size() > 0) {
            
            if (children.get(children.size() - 1).getAge() < 3) {
                
                return false;
                
            }
            
        }
        
        // One extra check for age
        if ((adultOne.getAge() < 22) || (adultTwo.getAge() < 22)) {
            
            return false;
            
        }
        
        return true;
        
    }
    
    public int getNumMembers() {
        
        return members;
        
    }
    
    public int getNumChildrenDesired() {
        
        return numChildrenDesired;
        
    }
    
    public boolean marriedCouple() {
        
        if (adultOne == null) {
            
            return false;
            
        }
        if (adultTwo == null) {
            
            return false;
            
        }
        
        return true;
        
    }
    
    public double totalIncome() {
        
        double output = this.adultOne.getJob().marginalCost();
        output += this.adultTwo.getJob().marginalCost();
        
        return output;
        
    }
    
    public double disposableIncome() {
        
        double savings_rate = this.savingsRate;
        if (savings_rate < 0.0) {
            savings_rate = 0.0;
        }
        if (savings_rate > 1.0) {
            savings_rate = 1.0;
        }
        
        return (1 - savings_rate) * this.totalIncome();
        
    }
    
    public double savedIncome() {
        
        double savings_rate = this.savingsRate;
        if (savings_rate < 0.0) {
            savings_rate = 0.0;
        }
        if (savings_rate > 1.0) {
            savings_rate = 1.0;
        }
        
        return savings_rate * this.totalIncome();
        
    }
    
    public Sum budgetConstraint() {
        
        Sum output = new Sum(0);
        
        // Go through each of these variables and stuff
        for (int i = 0; i < this.purchasableGoods.size(); i += 1) {
            
            Variable current_var = this.purchasableGoods.get(i);
            if (current_var.isInput()) {
                
                Input current_input = current_var.getInput();
                if (current_input.isGood()) {
                    
                    Product addition = new Product();
                    addition.components.add(current_var);
                    addition.components.add(new Num(current_input.marginalCost()));
                    output.components.add(addition);
                    
                }
                
            }
            
        }
        
        // Correct for the amount that can be spent
        output.components.add(new Num(this.disposableIncome() * -1.0));
        
        return output;
        
    }
    
    public ArrayList<GoodAmount> maxPurchases() {
        
        double[] numbers = new double[this.purchasableGoods.size()];
        double[] constraint = {0};
        
        // Minimize the negative
        Product negative = new Product(2);
        negative.components.add(0, this.utilityFunction);
        negative.components.add(1, new Num(-1.0));
        
        // Expression is utility function.
        Evaluator evaluator = new Evaluator(negative,
                this.budgetConstraint(), 0.0);
        
        CobylaExitStatus result = Cobyla.FindMinimum(evaluator, 2, 3, numbers, 0.5, 1.0e-6, 0, 9999999);
        
        ArrayList<GoodAmount> output = new ArrayList<GoodAmount>(numbers.length);
        
        int j = 0;
        for (int i = 0; i < this.purchasableGoods.size(); i += 1) {
            
            if (this.purchasableGoods.get(i).getInput().isVariable()) {
                
                Variable current = this.purchasableGoods.get(i).getInput().variable;
                if ((current.number != -1) && (this.purchasableGoods.get(i).getInput().isGood())) {
                    
                    GoodAmount addition = new GoodAmount();
                    addition.setGood((Good)(this.purchasableGoods.get(i).getInput()));
                    addition.setAmount(numbers[addition.good().variable.number]);
                    output.add(j, addition);
                    j += 1;
                    
                }
                
            }
            
        }
        
        return output;
        
    }
    
}