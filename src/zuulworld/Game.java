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

    public Game() {

        createRooms();
        createItem();
        newPlayer();
        parser = new Parser();
    }

    private void newPlayer() {
        Player newPlayer;
        newPlayer = new Player(20, 10);
        currentPlayer = newPlayer;

    }

//    private void spawnCreatures() {
//        Creature tiger, boss;
//
//        tiger = new Creature("tiger", 20, 5);
//        boss = new Creature("boss", 50, 15);
//
//    }
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
    }

    public void play() {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Good bye.");
    }

    private void printWelcome() {
//        System.out.println();
//        String Title = "Welcome to the Isle of Zuul!";
//        for(int i=0; i<Title.length(); i++){
//            try {
//                System.out.print(Title.charAt(i));
//                //Thread.sleep(5030)
//                TimeUnit.MILLISECONDS.sleep(250);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }

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
        }else if (commandWord == CommandWord.ATTACK) {
            startAttack(command);
        }else if (commandWord == CommandWord.GO) {
            goRoom(command);
        }else if (commandWord == CommandWord.TAKE) {
            takeItem(command);
        }else if (commandWord == CommandWord.FLEE) {
            fleeCombat();
        }else if (commandWord == CommandWord.DROP) {
            dropItem(command);
        }else if (commandWord == CommandWord.STATS) {
            showStats();
        }        
        else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        }
        return wantToQuit;
    }

    private void printHelp() {
        try {
            System.out.println("You are lost and need to find a way off the island, and preferably soon.");
            Thread.sleep(500);

            System.out.println("You are " + currentRoom.getShortDescription());
            System.out.println();
            Thread.sleep(500);
            System.out.println(currentPlayer.getLife());

            Thread.sleep(500);
            currentPlayer.changeStatus();
            System.out.println(currentPlayer.getStatus());
            System.out.println("Your command words are:");

            parser.showCommands();
        } catch (InterruptedException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void goRoom(Command command) {
        if (currentPlayer.getStatus() == false) {
            if (!command.hasSecondWord()) {
                System.out.println("Go where?");
                return;
            }

            if ("flee".equals(command.getSecondWord())){
                System.out.println("You can't flee right now.");
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
            System.out.println("You cant do that right now!");
        }
    }
    
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
        
        Items banana = new Items("banana", false, 5);
        banana.setItemDes("A fresh banana");
        //***
    }
    
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
    
    private void startAttack(Command command) {
        if (currentRoom.getLocation() == "river") {
            if (!command.hasSecondWord()) {
                System.out.println("Attack what?");
                return;
            }
            String target = command.getSecondWord();
            System.out.println(target);

            if (target.equals("tiger")) {
                Creature tiger;
                tiger = new Creature("tiger", 25, 5);
                currentCreature = tiger;

                System.out.println(currentCreature.getLife());
                currentPlayer.changeStatus();

            }

        } else {
            System.out.println("There is nothing to attack here.");
        }
    }

    /**
     * Removes the player from combat and puts him in the beach location.
     */
    private void fleeCombat() {
        if (currentPlayer.getStatus() == true) {
            fleeRoom();
            currentPlayer.changeStatus();

        } else {
            System.out.println("There is nothing to flee from. Coward..!");
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

    private void showStats() {
           
        System.out.println("\n");
        System.out.println("---- STATS ----");
        System.out.println("Total Health - " + currentPlayer.getHP());
        System.out.println("Total DMG - " + currentPlayer.getDMG());
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
