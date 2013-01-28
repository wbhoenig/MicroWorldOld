/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package microworld;

import java.util.ArrayList;
/**
 *
 * @author will
 */
public class Company {
    
    private Good product;
    
    private ArrayList<Asset> assets;
    private ArrayList<Debt> debts;
    
    // Keep these indexed together, or else
    private ArrayList<Person> employees;
    private ArrayList<Job> jobs;
    
}
