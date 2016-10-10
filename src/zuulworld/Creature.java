/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;

/**
 *
 * @author Jakob
 */
public class Creature {

    private String name;
    private int health;
    private int attack;    
    public boolean isAlive;

    public Creature(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.isAlive = true;

    }
    
    /**
     * Method for getting the name of a creature
     * @return Returns a string with the name of the creature. 
     */
    public String getName(){
        return name;
    }
    /**
     * Changes the alive status of a creature to false (dead).
     */
    public void changeStatus(){
        isAlive = false;
    }
    
    /**
     * Returns the status of the creature, where true is alive, and false is dead. 
     * @return Returns a boolean where true is alive, and false is dead. 
     */
    public boolean getStatus(){
        
        return isAlive;
    }
    /**
     * getLife Method
     *
     * @return Returns an integer containing the creatures current hitpoints.
     */
    public String getLife() {
        return ("The creatures hitpoints is: " + health);
    }

    /**
     * Takes an integer variable and changes the creatures attack-rating by that
     * much.
     *
     * @param wep The amount to change the attack-rating with.
     */
    public void changeAttack(int wep) {
        attack = attack + wep;
    }

    /**
     * Makes changes to the creatures healthpool.
     *
     * @param damage The integer value with which to change the creatures
     * hitpoints.
     */
    public void takeHit(int damage) {
        health = health + damage;
    }

    /**
     * Rolls an attack roll for the creature, taking into account it's attack
     * rating.
     *
     * @return Returns an integer between 80% and 120% of the creatures attack
     * rating.
     */
    public int damageRoll() {
        Double dmgRoll;
        int roll;
        // Rolls a random number between 0 and 40, then adds 80 to get a number between 80 and 120. 
        dmgRoll = (Double) (Math.random() * 41) + 80;
        // Divides by 100 to get a factor between 0.80 and 1.20
        dmgRoll = (dmgRoll / 100) * attack;

        // Converts the double dmgRoll into an integer for easier display and calculations. Always rounds down. 
        roll = dmgRoll.intValue();

        return (roll);
    }
}
