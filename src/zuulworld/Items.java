/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;

import java.util.HashMap;

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
     * @param name
     * @param pu
     * @param dmg
     * @param wgt 
     */
    public Items(String name, boolean pu, int dmg, int wgt) {
        this.name = name;
        this.pickUp = pu;
        this.damage = dmg;
        this.weight = wgt;
        
    }
    /**
     * 
     * @param name
     * @param pu
     * @param hp 
     */
    public Items(String name, boolean pu, int hp){
        this.name = name;
        this.pickUp = pu;
        this.health = hp;
    }
    
    public void setItemDes(String des) {
        this.itemDes = des;
    }
    
    public String getItemDes() {
        return itemDes;
    }
    public int getDMG() {
        return damage;
    }
    public String getName() {
        return name;
    }
}
