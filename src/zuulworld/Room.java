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

    public Room(String description, String location) 
    {
        this.location = location;
        this.description = description;
        exits = new HashMap<String, Room>();
        creatures = new HashMap<Room, Creature>();
    }

    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public void setCreature(Room room, Creature creature){
        creatures.put(room, creature);
    }
    
    public String getShortDescription()
    {
        return description;
    }

    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString()
    {
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

    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    public Creature getCreature(Room room){
        return creatures.get(room);
    }
    
//    public Creature getCreature(String name){
//        return creatures.get(name);
//    }
  
}

