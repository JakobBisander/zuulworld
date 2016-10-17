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
    private String itemDes;

    /**
     * 
     * @param name = the name of the item.
     * @param pu = a boolean defining if the item should be able to get picked up.
     * @param dmg = the damage of the wepon.
     * @param wgt = the weight of the wepon. 
     */
    public Items(String name, boolean pu, int dmg, int wgt) {
        this.name = name;
        this.pickUp = pu;
        this.damage = dmg;
        this.weight = wgt;

    }

    /**
     * 
     * @param name = the name of the item.
     * @param pu = a boolean defining if the item should be able to get picked up.
     * @param hp = the amount of health the item will give the player.
     */
    public Items(String name, boolean pu, int hp) {
        this.name = name;
        this.pickUp = pu;
        this.health = hp;
    }

    /**
     * 
     * @param des 
     */
    public void setItemDes(String des) {
        this.itemDes = des;
    }

    /**
     * 
     * @return 
     */
    public String getItemDes() {
        return itemDes;
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
}
