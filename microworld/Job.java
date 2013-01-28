package microworld;
import EconMath.*;

/**
 *
 * @author will
 */
public class Job extends Input {
    
    private String name;
    private Company company;
    
    private int salary;
    
    private Expression qualificationFormula;
    private Variable education;
    private Variable qualSkill;
    private Variable quantSkill;
    private double minQualification;
    
    public double marginalCost(int unit) {
        
        return this.salary;
        
    }
    
    public double marginalCost() {
        
        return this.salary;
    
    }
    
    public double qualifiedAmount(Person person) {
        
        double oldEducation = this.education.value;
        double oldQualSkill = this.qualSkill.value;
        double oldQuantSkill = this.quantSkill.value;
        
        this.education.value = person.getEducation();
        this.qualSkill.value = person.getQualSkill();
        this.quantSkill.value = person.getQuantSkill();
        
        double output = this.qualificationFormula.value() - this.minQualification;
        
        this.education.value = oldEducation;
        this.qualSkill.value = oldQualSkill;
        this.quantSkill.value = oldQuantSkill;
        
        return output;
        
    }
    
    public boolean qualified(Person person) {
        
        return (this.qualifiedAmount(person) >= 0);
        
    }
    
    // Easy function!
    public boolean isJob() { return true; }
    
}
