package microworld;

import java.util.ArrayList;
import java.util.Collections;
import EconMath.*;
import com.cureos.numerics.*;

public class MicroWorld {

    public static void main(String[] args) {
        
        Num one = new Num(4.0);
        Variable x = new Variable();
        Variable y = new Variable();
        
        Power exx = new Power();
        exx.base = x;
        exx.exponent = new Num(0.4);
        Power why = new Power(y, new Num(0.6));
        
        Product expression = new Product();
        expression.components.add(one);
        expression.components.add(exx);
        expression.components.add(why);
        
        Sum constraint = new Sum();
        Product two = new Product();
        two.components.add(x);
        two.components.add(new Num(2.0));
        Product three = new Product();
        three.components.add(y);
        three.components.add(new Num(4.0));
        
        constraint.components.add(two);
        constraint.components.add(three);
        
        double[] out = new double[2];
        double[] con = {16, 0, 0};
        Evaluator evaluator = new Evaluator(expression, constraint, 16.0);
        
        CobylaExitStatus result = Cobyla.FindMinimum(evaluator, 2, 3, out, 0.5, 1.0e-6, 0, 3500);
        
        System.out.println(out[0] + " " + out[1]);
        
    }
    
    // General theory:
    // People buy Goods which give People Jobs using Goods to make other Goods.
    public static void mainDeux(String[] args) {
        
        // Right now, we just care about calculus.
        /*Variable x = new Variable(4);
        Variable y = new Variable(3.2);
        Variable z = new Variable(11);
        Power one = new Power(x, y);
        Product two = new Product(y, x, z);
        Sum three = new Sum(one, two);
        Expression four = three.derivative(x);
        double yeah = four.value();
        System.out.println(yeah + "\n");*/
        
        ArrayList<Person> people = new ArrayList<Person>(10000000);
        ArrayList<Household> households = new ArrayList<Household>(10000000);
        
        double[] populations = new double[450];
        int dead_people = 0;
        
        // Feed the class itself
        //Person.personList = people;
        //Household.householdList = households;
        
        // Populate with 2 random people.
        for (int i = 0; i < 10; i += 1) {
            
            people.add(new Person());
            
        }
        
        int total_people = 10;
       // int dead_people = 0;
        
        // Let's go up to 450 years
        int i;
        for (i = 0; i < 315; i += 1) {
            
            System.out.print("Year: " + i + "\n");
            populations[i] = people.size();
            System.out.print("People: " + populations[i] + "\n");
            System.out.print("Households: " + households.size() + "\n");
            //System.out.print("Total people to ever die: " + dead_people + "\n");
            System.out.println("Total people to ever exist: " + total_people);
            System.out.println("Total people to have died: " + dead_people + "\n");
            // Remove dead people from the array, and age everyone
            for (int j = people.size() - 1; j >= 0; j -= 1) {
                
                people.get(j).process();
                if (!people.get(j).getLivingStatus()) {
                    
                    people.remove(j);
                    dead_people += 1;
                    
                }
                
            }
            people.trimToSize();
            
            // Prune
            for (int j = households.size() - 1; j >= 0; j -= 1) {
                
                households.get(j).prune();
                if (households.get(j).getNumMembers() < 1) {
                    
                    households.remove(j);
                    
                }
                
            }
            households.trimToSize();
            
            // Marry people off. For equity, this should be done randomly
            ArrayList<Person> listOne = (ArrayList<Person>)people.clone();
            ArrayList<Person> listTwo = (ArrayList<Person>)people.clone();
            Collections.shuffle(listOne);
            Collections.shuffle(listTwo);
            
            for (int j = 0; j < listOne.size(); j += 1) {
                
                if (listOne.get(j).marry(listTwo)) {
                    
                    households.add(new Household(listOne.get(j), listOne.get(j).getSpouse()));
                    
                }
                
            }
            
            // Now let's kill off dead households
            for (int j = households.size() - 1; j >= 0; j -= 1) {
                
                if (households.get(j).getNumMembers() < 1) {
                    
                    households.remove(j);
                    
                }
                
            }
            
            households.trimToSize();
            
            // Now let's make babies.
            for (int j = 0; j < households.size(); j += 1) {
                
                if (households.get(j).childTime()) {
                    
                    people.add(households.get(j).haveChild());
                    total_people++;
                    
                }
                
            }
            
        }
        
        System.out.println("\nAt the start of year 450... Demographics:");
        System.out.print("Year: " + i + "\n");
        System.out.print("People: " + people.size() + "\n");
        System.out.print("Households: " + households.size() + "\n");
        System.out.print("Total people to ever die: " + dead_people + "\n");
        System.out.println("Total people to ever exist: " + total_people);
        int[] ages = new int[81];
        int married = 0;
        int marriages = 0;
        for (int a = 0; a < 81; a += 1) {
            
            ages[a] = 0;
            
        }
        for (int a = 0; a < people.size(); a += 1) {
            
            int age = people.get(a).getAge();
            if (age < 81) {
                
                ages[age] += 1;
                
            }
            if (people.get(a).isMarried()) {
                
                married += 1;
                
            }
            
        }
        for (int a = 0; a < households.size(); a += 1) {
            
            if (households.get(a).marriedCouple()) {
                
                marriages += 1;
                
            }
            
        }
        System.out.println("People of each age:");
        for (int b = 0; b < 81; b += 1) {
            
            System.out.println(b + ": " + ages[b]);
            
        }
        System.out.println("Married People: " + married);
        System.out.println("Marriages: " + marriages);
        System.out.println("Population History:");
        double[] rates = new double[215];
        for (int g = 0; g < 315; g += 1) {
            
            System.out.println(g + ": " + populations[g]);
            
            if (g > 99) {
                
                rates[g - 100] = populations[g] / populations[g - 1];
                
            }
            
        }
        double avg = 0;
        for (int h = 0; h < 215; h += 1) {
            
            avg += rates[h] / 215;
            
        }
        System.out.println("Average population growth rate since year 100: " + avg);
        
        int highest = 0;
        int lowest = people.get(0).getGeneration();
        
        for (int q = 0; q < people.size(); q += 1) {
            
            if (people.get(q).getGeneration() > highest) {
                
                highest = people.get(q).getGeneration();
                
            }
            if (people.get(q).getGeneration() < lowest) {
                
                lowest = people.get(q).getGeneration();
                
            }
            
        }
        System.out.println("Highest generation: " + highest);
        System.out.println("Lowest generation: " + lowest);
        
    }
}