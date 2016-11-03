package zuulworld;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;


public class Room 

{
    private String description;
    private String location;
    private String exitDesciption;
    private HashMap<String, Room> exits;
    private HashMap<Room, Creature> creatures;
    private HashMap<String, Items> items;
    private HashMap<String, NPC> npc;
    

    public Room(String description, String exitDesciption, String location) {
        this.location = location;
        this.description = description;
        this.exitDesciption = exitDesciption;
        exits = new HashMap<String, Room>();
        creatures = new HashMap<Room, Creature>();
        items = new HashMap<String, Items>();
        npc = new HashMap<String, NPC>();
        
    }
    
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }
    
    public void setCreature(Room room, Creature creature){
        creatures.put(room, creature);
    }
    public void setNPC(String name, NPC person) {
        npc.put(name, person);
    }
    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        if(npc.isEmpty()) {
            if(creatures.isEmpty()){
                if(!items.isEmpty()) {
                    String itemDescription = "";

                    for (Items i : items.values()) {
                        itemDescription += i.getDescription();
                    }
                    return "You are " + description + itemDescription + "..\n" + exitDesciption;
                }
                return "You are " + description + ".\n" + exitDesciption;
            } else {
                String creatureDescription = "";
                for (Creature i : creatures.values()) {
                    creatureDescription += i.getDescription();
                }
                return "You are " + description + creatureDescription + "..\n" + exitDesciption;
            }
        } else {
            String npcDescription = "";
            for (NPC i : npc.values()) {
                npcDescription += i.getNpcDescription();
            }
            return "You are " + description + npcDescription + "..\n" + exitDesciption;
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
    public boolean hasNPC(String a) {
        return npc.containsKey(a);
    }
    public NPC getNPC(String key) {
        return npc.get(key);
    }
    public void removeNPC() {
        npc.clear();
    }
    

    
  
}

