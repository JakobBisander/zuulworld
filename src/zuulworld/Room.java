package zuulworld;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 

{
    private String description;
    private String location;
    private String weaponDes;
    private HashMap<String, Room> exits;
    private HashMap<Room, Creature> creatures;
    private HashMap<String, Items> items;

    public Room(String description, String wepDes, String location) {
        this.location = location;
        this.description = description;
        this.weaponDes = wepDes;
        exits = new HashMap<String, Room>();
        creatures = new HashMap<Room, Creature>();
        items = new HashMap<String, Items>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    
    public void setCreature(Room room, Creature creature){
        creatures.put(room, creature);
    }
    
    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        String des = "You are " + description + ".\n" + getExitString();
        if(!items.isEmpty()) {
            des = "You are " + description + weaponDes + ".\n" + getExitString();
        }
        return des;
    }

    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }
    
    /**
    Returns the current location of the player for comparison in commands.    
    **/
    public String getLocation(){
        return location;
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }
    
    public Creature getCreature(Room room){
        return creatures.get(room);
    }
    public void placeItem(String name, Items item) {
        items.put(name, item);
    }
    public boolean hasItem(String a) {
        
        return items.containsKey(a);
    }
    public Items getItem(String key) {
        return items.get(key);
    }
    public void removeItem(String key) {
        items.remove(key);
    }
//    public Creature getCreature(String name){
//        return creatures.get(name);
//    }
  
}

