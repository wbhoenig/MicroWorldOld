/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

import java.util.ArrayList;
import java.util.Random;

public class Person {
    
    private boolean alive;
    private int age;
    
    // 0 is female, 1 is male
    private int gender;
    private int marriagePropensity;
    private boolean married;
    private double numChildrenDesired;
    
    private Person spouse;
    private Person mother;
    private Person father;
    private ArrayList<Person> children;
    private ArrayList<Person> formerSpouses;
    
    // Controversial variables
    private int education; // 0-99
    private int quantSkill; // 0-99
    private int qualSkill; // 0-99
    
    private int experience;
    private Job job;
    
    private int generation;
    
    // Common to all
    public static final int generalMarriagePropensity = 65;
    
    public static final int stdMarriaveDev = 30;
    public static final int marriageAgeDiff = 8;
    
    public static ArrayList<Person> personList;
    
    private static int numDeaths = 0;
    
    // Constructors
    public Person() {
        
       alive = true; 
       age = 0;
       
       gender = randomGender();
       married = false;
       marriagePropensity = randomPropensityToMarry();
       numChildrenDesired = randomNumChildrenDesired();
       
       education = 0;
       quantSkill = randomSkill();
       qualSkill = randomSkill();
       
       generation = 0;
        
    }
    
    public Person(int gender) {

       alive = true; 
       age = 0;
       
       this.gender = gender;
       marriagePropensity = randomPropensityToMarry();
       numChildrenDesired = randomNumChildrenDesired();
       married = false;
       
       education = 0;
       quantSkill = randomSkill();
       qualSkill = randomSkill();
       
       generation = 0;
        
    }
    
    public Person(Person p1, Person p2) {
        
        alive = true;
        age = 0;
        
        gender = randomGender();
        marriagePropensity = randomPropensityToMarry();
        numChildrenDesired = randomNumChildrenDesired();
        married = false;
        
        education = 0;
        experience = 0;
        
        if (p1.getGender() == 0) {
            
            mother = p1;
            father = p2;
            
        }
        else {
            
            father = p1;
            mother = p2;
            
        }
        
        // For qualSkill and quantSkill, 60 possible points are random, and 50 are inherited
        // But it maxes out at 99
        quantSkill = randomSkill(p1.quantSkill, p2.quantSkill);
        qualSkill = randomSkill(p1.qualSkill, p2.qualSkill);
        
        if (father.generation > mother.generation) {
            
            generation = father.generation + 1;
            
        }
        else {
            
            generation = mother.generation + 1;
            
        }
        
    }
    
    public boolean getLivingStatus() {
        
        return alive;
        
    }
    
    public int getAge() {
        
        return age;
        
    }
    
    public int getGender() {
        
        return gender;
        
    }
    
    public int getMarriagePropensity() {
        
        return marriagePropensity;
        
    }
    
    public double getNumChildrenDesired() {
        
        if (children != null) {
            return numChildrenDesired - children.size();
        }
        return numChildrenDesired;
        
    }
    
    public boolean isMarried() {
        
        return married;
        
    }
    
    public Person getSpouse() {
        
        return spouse;
        
    }
    
    public Person getMother() {
        
        return mother;
        
    }
    
    public Person getFather() {
        
        return father;
        
    }
    
    public ArrayList<Person> getFormerSpouses() {
        
        return formerSpouses;
        
    }
    
    public ArrayList<Person> getChildren() {
        
        return children;
        
    }
    
    public int getEducation() {
        
        return education;
        
    }
    
    public int getQuantSkill() {
        
        return quantSkill;
        
    }
    
    public int getQualSkill() {
        
        return qualSkill;
        
    }
    
    public int getExperience() {
        
        return experience;
        
    }
    
    public Job getJob() {
        
        return job;
        
    }
    
    public int getGeneration() {
        
        return generation;
        
    }
    
    public void marryTo(Person beloved) {
        
        married = true;
        spouse = beloved;
        
    }
    
    public void process() {
        
        age += 1;
        if (age > 85) {
            
            alive = false;
            if (personList != null) {
                
                personList.remove(this);
                numDeaths += 1;
                
            }
            
        }
        if (age > 75) {
            
            int rand = (int)(Math.random() * 100);
            int chance = 85 - age;
            chance = 10 - chance;
            chance *= 10;
            if (rand < chance) {
                
            alive = false;
            if (personList != null) {
                
                personList.remove(this);
                numDeaths += 1;
                
            }
                
            }
            
        }
        
    }
    
    public boolean marry(ArrayList<Person> options) {
        
        ArrayList<Person> viables = new ArrayList<Person>(0);
        
        // Sweep one... find the viables.
        int j = 0;
        for (int i = 0; i < options.size(); i += 1) {
            
            if (viability(options.get(i))) {
                
                viables.add(options.get(i));
                j += 1;
                
            }
            
        }
        
        // Pretty (absurdly) simple algorithm. Self-explanatory.
        for (int i = 0; i < viables.size(); i += 1) {
            
            int chance = marriagePropensity + viables.get(i).getMarriagePropensity();
            chance += (100 - Math.abs(education - viables.get(i).getEducation()));
            chance += (100 - Math.abs(quantSkill - viables.get(i).getQuantSkill()));
            chance += (100 - Math.abs(qualSkill - viables.get(i).getQualSkill()));
            // Adjust for income later.
            chance /= 5;
            
            int proposal = (int)(Math.random() * 100);
            
            if (proposal > chance) {
                
                // Yay! <3
                marryTo(viables.get(i));
                viables.get(i).marryTo(this);
                return true;
                
            }
            
        }
            
        return false;
        
    }
    
    private boolean viability(Person candidate) {
        
        // Just in case...
        if (!candidate.getLivingStatus()) {
            
            
            return false;
            
        }
        
        if (candidate.getAge() < 22) {
            
            return false;
            
        }
        
        // Sorry... no gay marriage
        // Maybe at a different point, but for now, convenience is king.
        if (candidate.getGender() == gender) {
            
            return false;
            
        }
        
        // No adultary
        if (candidate.isMarried()) {
            
            return false;
            
        }
        
        if (Math.abs(candidate.getAge() - age) > marriageAgeDiff) {
            
            return false;
            
        }
        
        // Deal with incest
        if ((generation > 6) && (candidate.getGeneration() > 4)) {
            
            // No to any grandparents, or parents
            ArrayList<Person> possibilities = new ArrayList<Person>(0);
            
            // My side
            possibilities.add(mother);
            possibilities.add(father);
            possibilities.add(mother.getMother());
            possibilities.add(mother.getFather());
            possibilities.add(father.getMother());
            possibilities.add(father.getFather());
            
            // The other side
            possibilities.add(candidate.getMother());
            possibilities.add(candidate.getFather());
            possibilities.add(candidate.getMother().getMother());
            possibilities.add(candidate.getMother().getFather());
            possibilities.add(candidate.getFather().getMother());
            possibilities.add(candidate.getFather().getFather());
            
            for (int i = 0; i < 12; i += 1) {
                
                for (int j = i + 1; j < 12; j += 1) {
                    
                    if (possibilities.get(j) == possibilities.get(i)) {
                        
                        return false;
                        
                    }
                    
                }
                
            }
            
        }
        
        // Within a certain number of years in either direction
        
        return true;
        
    }
    
    public static int randomGender() {
        
        return (int)(Math.random() * 2);
        
    }
    
    public static int randomSkill() {
        
        return (int)(Math.random() * 99);
        
    }
    
    public static int randomPropensityToMarry() {
        
        Random random = new Random();
        int attempt = generalMarriagePropensity + ((int)(random.nextGaussian()) * stdMarriaveDev);
        
        if (attempt > 99) {
            
            return 99;
            
        }
        if (attempt < 0) {
            
            return 0;
            
        }
        return attempt;
        
    }
    
    private static int randomSkill(int p1, int p2) {
        
        int attempt = p1 + p2;
        attempt = attempt / 4;
        attempt += (int)(Math.random() * 60);
        if (attempt > 99) {
            
            return 99;
            
        }
        return attempt;
        
    }
    
    private static double randomNumChildrenDesired() {
        
        //return (int)(Math.random() * 5);
        Random random = new Random();
        double attempt = 2.85 + (random.nextGaussian() * 1.2);
        if (attempt < 0.0) {
            
            attempt = 0.0;
            
        }
        return attempt;
        
    }
    
    public static int getNumDeaths() {
        
        return numDeaths;
        
    }
    
    public void widdow() {
        
        married = false;
        spouse = null;
        
    }
    
}
