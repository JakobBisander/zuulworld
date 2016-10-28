package zuulworld;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 

{
    private String description;
    private String location;
    private HashMap<String, Room> exits;
    private HashMap<Room, Creature> creatures;
    private HashMap<String, Items> items;
    

    public Room(String description, String location) {
        this.location = location;
        this.description = description;
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
        if(creatures.isEmpty()){
            if(!items.isEmpty()) {
                String itemDescription = "";
            
                for (Items i : items.values()) {
                    itemDescription += i.getDescription();
                }
                return "You are " + description + itemDescription + "..\n" + getExitString();
            }
            return "You are " + description + ".\n" + getExitString();
        } else {
            String creatureDescription = "";
            for (Creature i : creatures.values()) {
                creatureDescription += i.getDescription();
            }
            return "You are " + description + creatureDescription + "..\n" + getExitString();
        }
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
    public void removeCreature() {
        creatures.clear();
    }
  
}

