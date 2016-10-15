/*
 * This class holds information about creatures generated for the game. 
 * Creatures are assigned with a certain amount of hitpoints and attack-rating, and are linked to a specific room.
 * An object of a creature keeps track on wether or not the creature has been killed already, and how many hitpoints the creature has left. 
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
     *
     * @return Returns a string with the name of the creature.
     */
    public String getName() {
        return name;
    }

    /**
     * Changes the alive status of a creature to false (dead) if its hitpoints
     * is below 1.
     */
    public void changeStatus() {
        if (health < 1) {
            isAlive = false;
        }

    }

    /**
     * Returns the status of the creature, where true is alive, and false is
     * dead.
     *
     * @return Returns a boolean where true is alive, and false is dead.
     */
    public boolean getStatus() {

        return isAlive;
    }

    /**
     * A method for knowing the hitpoints of a creature. This returns a string,
     * and not an integer with the actual number.
     *
     * @return Returns a string containing information about the creatures
     * current hitpoints.
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
     * @return Returns an integer between 80% and 120% of the creatures attack,
     * rounded down. rating.
     */
    public int damageRoll() {
        Double dmgRoll;
        int roll = 0;
        // Rolls a random number between 0 and 40, then adds 80 to get a number between 80 and 120. 
        dmgRoll = (Double) (Math.random() * 41) + 80;
        // Divides by 100 to get a factor between 0.80 and 1.20
        dmgRoll = (dmgRoll / 100) * attack;

        // Converts the double dmgRoll into an integer for easier display and calculations.
        // Always rounds down. Also makes it negative for the purpose of the takeHit method 
        roll = roll - dmgRoll.intValue();

        return (roll);
    }
}
