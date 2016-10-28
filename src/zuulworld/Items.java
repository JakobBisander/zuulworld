/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;

/**
 *
 * @author Vedsted
 */
public class Items {

    private String name;
    private int damage;
    private int weight;
    private boolean pickUp;
    private int health;
    private String description;

    /**
     * 
     * @param name
     * @param description
     * @param pu
     * @param dmg
     * @param wgt 
     */
    public Items(String name, String description, boolean pu, int dmg, int wgt) {
        this.name = name;
        this.description = description;
        this.pickUp = pu;
        this.damage = dmg;
        this.weight = wgt;

    }

    /**
     * 
     * @param name
     * @param description
     * @param pu
     * @param hp 
     */
    public Items(String name, String description, boolean pu, int hp) {
        this.name = name;
        this.description = description;
        this.pickUp = pu;
        this.health = hp;
    }

    public boolean getPickUp() {
        return pickUp;
    }

    /**
     * 
     * @return 
     */
    public int getDMG() {
        return damage;
    }

    /**
     * 
     * @return 
     */
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getHealth() {
        return health;
    }
}
