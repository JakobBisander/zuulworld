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
    
    public Player(int health, int attack) {
        this.health = health;
        this.attack = attack;
        
    }
    public String getLife(){
        return ("Your current hitpoints is: "+health);
    }
    
 /**
 * Makes changes to the players healthpool. 
 * @param  damage  The change in the players hitpoints. 
 */
    public void takeHit(int damage){
        health = health+damage;
    }
}
