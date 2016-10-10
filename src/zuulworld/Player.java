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
public class Player {

    private int health;
    private int attack;
    private boolean inCombat;

    /**
     * Initiates the player with a given health and attack-rating. Also sets the
     * combat-status to false.
     *
     * @param health The starting hitpoints of the player.
     * @param attack The starting attack-rating of the player.
     */
    public Player(int health, int attack) {
        this.health = health;
        this.attack = attack;
        this.inCombat = false;

    }

    /**
     * getLife Method
     *
     * @return Returns the players current hitpoints.
     */
    public String getLife() {
        return ("Your current hitpoints is: " + health);
    }

    /**
     * Checks the combat-status of the player.
     *
     * @return Returns true if the player is in combat, and false if out of
     * combat.
     */
    public Boolean getStatus() {

        return inCombat;
    }

    /**
     * Changes the combat-status of the player.
     */
    public void changeStatus() {
        if (inCombat == true) {
            inCombat = false;
        } else if (inCombat == false) {
            inCombat = true;
        }
    }

    /**
     * Takes an integer variable and changes the players attack-rating by that
     * much.
     *
     * @param wep The amount to change the attack-rating with.
     */
    public void changeAttack(int wep) {
        attack = attack + wep;
    }

    /**
     * Makes changes to the players healthpool.
     *
     * @param damage The change in the players hitpoints.
     */
    public void takeHit(int damage) {
        health = health + damage;
    }

    /**
     * Rolls an attack roll for the player, taking into account his attack
     * rating.
     *
     * @return Returns an integer between 80% and 120% of the players attack
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
