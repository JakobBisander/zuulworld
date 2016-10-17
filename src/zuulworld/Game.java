package zuulworld;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 */
public class Game {

    private final Parser parser;
    private Room currentRoom;
    private Player currentPlayer;
    private Creature currentCreature;
    private boolean firstCombat;

    public Game() {

        createRooms();
        createItem();
        newPlayer();
        parser = new Parser();
        firstCombat = true;
    }

    private void newPlayer() {
        Player newPlayer;
        newPlayer = new Player(20, 10);
        currentPlayer = newPlayer;

    }
    private Room beach, jungle, river, crash, desert, village, mountain, volcano, tunnel;
    private void createRooms() {
        
        beach = new Room("at the beach where you first washed up.", "", "beach");
        jungle = new Room("in a dense part of the jungle.", " You see a sword laying on the ground.","jungle");
        river = new Room("by a river in the midst of the jungle. A tiger is sleeping near the bank of the river.", " You see a bow laying neer the tiger next to a blody corpse.", "river");
        crash = new Room("at the site where the plane crashed.", " By the crash you see a stick", "crash");
        desert = new Room("in a desolate desert.", "", "desert");
        village = new Room("at the village of a local tribe.", "","village");
        mountain = new Room("on the slope of a mountain.", "", "mountain");
        volcano = new Room("inside an volcano LotR-style.", "", "volcano");
        tunnel = new Room("inside a cave.", " You see a old gun laying in the shadows.", "tunnel");

        beach.setExit("south", jungle);
        beach.setExit("west", crash);

        crash.setExit("east", beach);
        crash.setExit("flee", beach);

        jungle.setExit("north", beach);
        jungle.setExit("west", desert);
        jungle.setExit("east", river);
        jungle.setExit("south", mountain);
        jungle.setExit("flee", beach);

        desert.setExit("east", jungle);
        desert.setExit("flee", beach);

        mountain.setExit("north", jungle);
        mountain.setExit("south", volcano);
        mountain.setExit("flee", beach);

        volcano.setExit("north", mountain);
        volcano.setExit("flee", beach);

        river.setExit("west", jungle);
        river.setExit("south", tunnel);
        river.setExit("east", village);
        river.setExit("flee", beach);

        tunnel.setExit("north", river);
        tunnel.setExit("flee", beach);

        village.setExit("west", river);
        village.setExit("flee", beach);

        currentRoom = beach;

        Creature tiger, boss, giant, crab, dummy;
        tiger = new Creature("tiger", 100, 5);
        boss = new Creature("boss", 100, 15);
        giant = new Creature("giant", 70, 10);
        crab = new Creature("crab", 15, 3);
        dummy = new Creature("dummy", 5, 0);

        river.setCreature(river, tiger);
        crash.setCreature(crash, crab);

    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            if (currentPlayer.getDead() == true) {  // Checks if the player is dead
                finished = true;
            } else {
                finished = processCommand(command);
            }

        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {

        System.out.println("Welcome to the Isle of Zuul!");
        System.out.println("<<<<<<<<<<<<<< Game Explanation >>>>>>>>>>>>");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.ATTACK) {
            if (currentPlayer.getStatus() == false) {
                startAttack(command);
            } else {
                cAttack();
            }
        } else if (commandWord == CommandWord.GO) {
            goRoom(command);
        } else if (commandWord == CommandWord.FLEE) {
            fleeCombat();
        } else if (commandWord == CommandWord.TAKE) {
            takeItem(command);
        } else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        } else if (commandWord == CommandWord.STATS) {
            showStats();
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    /**
     * Prints some helpful information for the player. Is issued with the
     * 'help'-command.
     */
    private void printHelp() {

        if (currentPlayer.getStatus() == true) {   // Checks if the player is in combat.
            System.out.println("You are currently in combat with a " + currentCreature.getName() + " .");
            System.out.println("You can either hit the " + currentCreature.getName() + " again with the 'attack'-command, or flee using the 'flee'-command.");
        } else {
            System.out.println("You are lost and need to find a way off the island as fast as possible.");
            System.out.println();

            System.out.println(currentPlayer.getLife());
            System.out.println("You are currently not in combat.");

            System.out.println("You are " + currentRoom.getShortDescription());
            System.out.println();
            System.out.println("Your command words are: 'help', 'go', 'attack' and 'quit'. ");

            //parser.showCommands();
        }

    }

    private void goRoom(Command command) {
        if (currentPlayer.getStatus() == false) {   // Checks if the player is in combat. 
            if (!command.hasSecondWord()) {
                System.out.println("Go where?");
                return;
            }

            if ("flee".equals(command.getSecondWord())) {
                System.out.println("You can't flee when not in combat.");
                return;

            }
            String direction = command.getSecondWord();

            Room nextRoom = currentRoom.getExit(direction);

            if (nextRoom == null) {
                System.out.println("You wander for a while in the direction, but suddenly find yourself back where you started.");
            } else {

                currentRoom = nextRoom;
                System.out.println(currentRoom.getLongDescription());

            }
        } else {
            System.out.println("You can't do that right now!");     // If the player is in combat, the 'go' command cannot be used. 
        }
    }
    
    /**
     * 
     */
    private void createItem() {
        Items sword = new Items("sword", true, 10, 1);
        sword.setItemDes("A sword in good condition");
        jungle.placeItem("sword", sword);
        
        Items bow = new Items("bow", true, 7, 1);
        bow.setItemDes("A old bow");
        river.placeItem("bow", bow);
        
        Items gun = new Items("gun", true, 100, 1);
        gun.setItemDes("An old gun with a few bullets");
        tunnel.placeItem("gun", gun);
        
        Items stick = new Items("stick", true, 2, 1);
        stick.setItemDes("A wooden stick");
        crash.placeItem("stick", stick);
    }
    /**
     * 
     * @param command 
     */
    private void takeItem(Command command) {
        if (currentPlayer.getStatus() == false) {
            if(!command.hasSecondWord()) {
                System.out.println("Take what?");
                return;
            }
            String item = command.getSecondWord();
            
            if(!currentRoom.hasItem(item)) {
                System.out.println("That item doesn't exist here.");
            } else {
                currentPlayer.addItem(item, currentRoom.getItem(item));
                currentRoom.removeItem(item);
                showStats();
            }
        }
        
    }
    /**
     * 
     * @param command 
     */
    private void dropItem(Command command) {
        if (currentPlayer.getStatus() == false) {
            if(!command.hasSecondWord()) {
                System.out.println("Drop what?");
                return;
            }
            String key = command.getSecondWord();
            
            if(!currentPlayer.hasItem(key)) {
                System.out.println("You don't have that item!");
            } else {
                currentPlayer.dropItem(key);
            }
        }
    }
    /**
     * Starts combat with a creature. Is called from the processCommand method,
     * whenever the player uses the 'attack' command while not in combat
     * already.
     *
     * @param command The command contains the attack command and a second word
     * that should be the name of whatever creature is available to attack.
     */
    private void startAttack(Command command) {
        Creature nowCreature = currentRoom.getCreature(currentRoom);

        currentCreature = nowCreature;
        if (currentCreature != null) {
            if (!command.hasSecondWord()) {
                System.out.println("Attack what?");
                return;
            }
            String target = command.getSecondWord();
            if (target.equals(currentCreature.getName())) {

                if (currentCreature.getStatus() == false) {     // Checks if the creature is already dead. 
                    // Code regarding loot 
                    System.out.println("The creature here is already dead, maybe you should try to search it instead");
                } else if (firstCombat == true) {
                    firstCombat = false;
                    currentPlayer.changeStatus();
                    System.out.println("You are now in combat with a " + currentCreature.getName() + ".");
                    System.out.println("While in combat you cannot move to another nearby room, but you can use the flee command to escape combat. ");
                    System.out.println("You only need to type in 'attack' while in combat, to hit your opponent.");
                    System.out.println(currentCreature.getLife());
                } else {

                    currentPlayer.changeStatus();
                    System.out.println("You are now in combat with a " + currentCreature.getName() + ".");
                    System.out.println(currentCreature.getLife());
                }

            }

        } else {
            System.out.println("There is nothing to attack here.");
        }
    }

    /**
     * Is called from the processCommand method when a player enters the
     * 'attack'-command while IN combat with a creature. Furthers combat by 1
     * hit from both participants - the player hits first, and then if the
     * creature is not dead, it hits back.
     */
    private void cAttack() {
        int playerAttack;
        int creatureAttack;

        playerAttack = currentPlayer.damageRoll();
        creatureAttack = currentCreature.damageRoll();

        currentCreature.takeHit(playerAttack);
        System.out.println("You hit the " + currentCreature.getName() + " for " + playerAttack + " damage.");
        System.out.println(currentCreature.getLife());
        currentCreature.changeStatus();

        if (currentCreature.getStatus() == true) {      // Checks if the creature died from the attack.
            currentPlayer.takeHit(creatureAttack);
            System.out.println("The " + currentCreature.getName() + " hits you back for " + creatureAttack + " damage.");
            System.out.println(currentPlayer.getLife());
            currentPlayer.checkPlayer();         // Checks if the players hitpoints is under 0, and marks him/her as dead if so. 
            if (currentPlayer.getDead() == true) {   // Checks if the player died from the attack. 
                System.out.println("Oh no! You've died! The game is now over, and will exit the next time you enter any command.");

            }
        } else {
            System.out.println("The " + currentCreature.getName() + " has died!");
            currentPlayer.changeStatus();
        }

    }

    /**
     * Removes the player from combat and puts him in the beach location.
     */
    private void fleeCombat() {
        if (currentPlayer.getStatus() == true) {    // Checks if the player is in combat.
            fleeRoom();
            currentPlayer.changeStatus();

        } else {
            System.out.println("There is nothing to flee from.... Coward!");
        }
    }

    /**
     * Works with the fleeCombat method to allow the player to exit combat
     * without defeating the target creature.
     */
    private void fleeRoom() {
        String flee = "flee";
        Room nextRoom = currentRoom.getExit(flee);
        currentRoom = nextRoom;
        System.out.println("You run away from the creature, and dont stop until you reach the beach again.");
        System.out.println(currentRoom.getLongDescription());
    }
    /**
     * 
     */
    private void showStats() {
           
        System.out.println("\n");
        System.out.println("---- STATS ----");
        System.out.println("Health - " + currentPlayer.getHP());
        System.out.println("Attack DMG - " + currentPlayer.getDMG());
        System.out.println("");
        System.out.println(" --Inventory-- ");
        for(String s: currentPlayer.itemStats()) {
            System.out.println(s);
        }
        System.out.println("\n");
    }
    /**
     * Exits the game.
     *
     * @param command The second word in the entered command. There should not
     * be one for the quit command.
     * @return Returns the true boolean for wanting to quit the game.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;
        }
    }

}
