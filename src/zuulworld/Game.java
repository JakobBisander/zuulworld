package zuulworld;

import java.util.Scanner;
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
    private NPC currentNPC;

    public Game() {

        createRooms();
        createItem();
        createNPC();
        newPlayer();
        newGame();
        parser = new Parser();
        firstCombat = true;
    }

    private void newPlayer() {
        Player newPlayer;
        newPlayer = new Player(20, 10);
        currentPlayer = newPlayer;
        currentPlayer.setName();

    }
    private void newGame() {
        System.out.println("Please choose your disired difficulty:");
        System.out.println("1: Easy Mode.");
        System.out.println("2: Normal Mode.");
        System.out.println("3: Hard Mode.");
        
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        
        if(choice == 1) {
            createCreaturesEasy();
        } else if( choice == 2) {
            createCreaturesNormal();
        } else {
            createCreaturesHard();
        }
    }
    private Room beach, jungle, river, crash, desert, village, mountain, volcano, tunnel;
    private void createRooms() {
        
        beach = new Room("at the beach where you first washed up.", "To the west, down the beach, you see flames and smoke rising to the sky from your plane wreck. \nTo the south, a deep jungle starts.", "beach");
        jungle = new Room("in a dense part of the jungle.", "To the north is the beach where you washed up. \nTo the west the jungle seems to thin out into a desert.\nTo the south a mountain slope appears to start.\nTo the east you hear the faint sound of running water.", "jungle");
        river = new Room("by a river in the midst of the jungle.", "To the south you see a cave opening. \nTo the east you can make out a few huts.\nTo the west the jungle seems to get denser.", "river");
        crash = new Room("at the site where the plane crashed.", "Back east is where you first came ashore. \nTo the south the jungle starts.", "crash");
        desert = new Room("in a desolate desert.", "Back east the jungle blooms.", "desert");
        village = new Room("at the village of a local tribe.", "Back west is the river at the edge of the jungle.", "village");
        mountain = new Room("on the slope of a mountain.", "To the south the mountain climbs even higher, and ends in a smoke-spewing volcano. \nTo the north is the jungle.", "mountain");
        volcano = new Room("inside a volcano where lava is sputtering against the old stone walls.", "To the north is back out on the mountainside.", "volcano");
        tunnel = new Room("inside a cave. A pillar in the cave is marked by a rune in the shape of a triangle.", "Back north is the exit of the cave, to the river.", "tunnel");

        beach.setExit("south", jungle);
        beach.setExit("west", crash);

        crash.setExit("east", beach);
        crash.setExit("flee", beach);
        crash.setExit("south", jungle);

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
        System.out.println("");
        System.out.println("You are making an overflight to discover a new group of islands.");
        System.out.println("To the east you see a small village, with smoke coming from the huts.");
        System.out.println("Suddenly your instruments malfunctions, and your plane plunges to the ground.");
        System.out.println("You luckily manage to activate your emergency parachute and land in the ocean...");
        System.out.println("You pass out...");
        System.out.println("Later you wake up to the sound of the waves hitting the beach and see a big boat in the horizon.");
        System.out.println("You try to make contact to the boat, but they don’t see you.");
        System.out.println("");
        System.out.println("Your command words are: 'help', 'go', 'attack', 'take', 'drop', 'talk', 'stats' and 'quit'. ");
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
        } else if (commandWord == CommandWord.TALK) {
            startConversation(command);
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
            System.out.println("Your command words are: 'help', 'go', 'attack', 'take', 'drop', 'talk', 'stats' and 'quit'. ");

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
    private Creature tiger, dragon, golem, crab, yeti;
    private void createCreaturesEasy() {
        
        tiger = new Creature("tiger", " A tiger is sleeping near the bank of the river, next to a bloody corpse.", 75, 8);
        river.setCreature(river, tiger);
        
        dragon = new Creature("dragon", " A massive dragon stands before you, with enormous wings, and smoke flaring from its nostrils. "
                                        + "Behind it, an unconscious girl is lying on the cavern floor.", 100, 20);
        volcano.setCreature(volcano, dragon);
        
        golem = new Creature("golem", " A sand golem is standing watch near an ancient ruin. An intricate runemark is branded "
                                + "on its chest, in the shape of a triangle.", 120, 5);
        desert.setCreature(desert, golem);
        
        crab = new Creature("crab", " A giant angry crab storms out of the wreckage. ", 15, 3);
        crash.setCreature(crash, crab);
        
        yeti = new Creature("yeti", " A ferocious yeti is standing on the mountain side.", 40, 5);
        mountain.setCreature(mountain, yeti);
        
             
    }
        private void createCreaturesNormal() {
        
        tiger = new Creature("tiger", " A tiger is sleeping near the bank of the river, next to a bloody corpse.", 75, 10);
        river.setCreature(river, tiger);
        
        dragon = new Creature("dragon", " A massive dragon stands before you, with enormous wings, and smoke flaring from its nostrils. "
                                        + "Behind it, an unconscious girl is lying on the cavern floor.", 100, 25);
        volcano.setCreature(volcano, dragon);
        
        golem = new Creature("golem", " A sand golem is standing watch near an ancient ruin. An intricate runemark is branded "
                                + "on its chest, in the shape of a triangle.", 120, 7);
        desert.setCreature(desert, golem);
        
        crab = new Creature("crab", " A giant angry crab storms out of the wreckage. ", 15, 5);
        crash.setCreature(crash, crab);
        
        yeti = new Creature("yeti", " A ferocious yeti is standing on the mountain side.", 40, 8);
        mountain.setCreature(mountain, yeti);
        
             
    }
            private void createCreaturesHard() {
        
        tiger = new Creature("tiger", " A tiger is sleeping near the bank of the river, next to a bloody corpse.", 75, 13);
        river.setCreature(river, tiger);
        
        dragon = new Creature("dragon", " A massive dragon stands before you, with enormous wings, and smoke flaring from its nostrils. "
                                        + "Behind it, an unconscious girl is lying on the cavern floor.", 100, 30);
        volcano.setCreature(volcano, dragon);
        
        golem = new Creature("golem", " A sand golem is standing watch near an ancient ruin. An intricate runemark is branded "
                                + "on its chest, in the shape of a triangle.", 120, 10);
        desert.setCreature(desert, golem);
        
        crab = new Creature("crab", " A giant angry crab storms out of the wreckage. ", 15, 8);
        crash.setCreature(crash, crab);
        
        yeti = new Creature("yeti", " A ferocious yeti is standing on the mountain side.", 40, 10);
        mountain.setCreature(mountain, yeti);
        
             
    }
    /**
     * 
     */
    private void createItem() {
        //Weapons
        Items sword = new Items("sword", " There is a sword laying in the shadows.", true, 10, 1);
        river.placeItem("sword", sword);
        
        Items bow = new Items("bow", " You see an old bow hanging from a tree.", true, 7, 1);
        jungle.placeItem("bow", bow);
        
        Items mjolnir = new Items("mjolnir", " You see an shiny object crackling with thunder, could that be the legendary mjolnir?", true, 25, 1);
        tunnel.placeItem("mjolnir", mjolnir);
        
        Items stick = new Items("stick", " You see a stick laying on the ground, maybe you can use it as a weapon.", true, 2, 1);
        crash.placeItem("stick", stick);
        
        Items spear = new Items("spear", " There is a spear stuck in the ground.", true, 15, 1);
        mountain.placeItem("spear", spear);
        
        Items knife = new Items("knife", " There is a small rusty knife laying in the sand", true, 5, 1);
        desert.placeItem("knife", knife);
        
        //health items
        Items banana = new Items("banana", " There is a banana laying on the ground.", false, 6);
        river.placeItem("banana", banana);
        
        Items coconut = new Items("coconut", " There is a coconut laying next to an old tree.", false, 5);
        jungle.placeItem("coconut", coconut);
        
        Items bread = new Items("bread", " There is a loaf of bread laying next to the plane.", false, 10);
        crash.placeItem("bread", bread);
        
        Items pineapple = new Items("pineapple", " There is a pineapple by the foot of a giant tree nearby", false, 7);
        river.placeItem("pineapple", pineapple);
        
        Items cactus = new Items("cactus", " There is a cactus blooming in at the edge of the desert", false, 3);
        desert.placeItem("cactus", cactus);
        
        Items potion = new Items("potion", " There is a golden potion sitting on a stone shelf.", false, 20);
        tunnel.placeItem("potion", potion);
        
        Items egg = new Items("egg", " There is a egg, laying in a old nest at the to of the mountain.", false, 10);
        mountain.placeItem("egg", egg);
             
    }
    
    private NPC man, voice, villager;
    private void createNPC() {
        man = new NPC("man", "Hello", " A mysterious man appers out of no where.");
        man.setQuestions("Who are you?", "Where am I?", "Can you please help me get off this island?");
        man.setAnswers("I am god!", "You are on The Island of Zuul!", "You must help yourself get off this island. The village in the east will be able to help you!"); 
        beach.setNPC("man", man);
                
        voice = new NPC("voice", "I don't have eyes, but once i did see. Once i had thoughts, but now i'm empty. What am i?", " You can hear a voice through out the tunnel, maybe you are able to talk to it?");
        voice.setQuestions("A skull", "A ghost", "An old man");
        voice.setAnswers("You may enter!" + "\n" + "A giant boulder rolls to the side to reveal a mighty weapon!", "You are not worthy", "You are not worthy");
        tunnel.setNPC("voice", voice);
        
        villager = new NPC("villager", "What are you doing here?", " An villager stands outside the village, he looks upset.");
        villager.setQuestions("Is something wrong?", "What is this place?", "Can you help me get to the boat on the north coast?");
        villager.setAnswers("My village is in shock.. My only daughter has been taken by the mighty dragon!", "This is the only village on Zuul island", "If you can defeat the dragon at the top of the mountain, and safely get my daughter back, we'll help you get off the island!");
        village.setNPC("villager", villager);
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
                if(currentPlayer.getCarryCurrent() < 2 || currentRoom.getItem(item).getPickUp() == false) {
                    currentPlayer.addItem(item, currentRoom.getItem(item));
                    currentRoom.removeItem(item);
                    showStats();
                    System.out.println(currentRoom.getLongDescription());
                } else {
                    System.out.println("Your inventory is full!");
                    System.out.println("If you want to pick up the item, you must first drop one of your current items!");
                    System.out.println("The " + currentRoom.getItem(item).getName() + " has " + currentRoom.getItem(item).getDMG() + " DMG.");
                    System.out.println("You can look at your current weapons under stats.");
                }
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
                showStats();
                System.out.println(currentRoom.getLongDescription());
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
        System.out.println("You hit the " + currentCreature.getName() + " for " + (playerAttack * -1) + " damage.");
        System.out.println(currentCreature.getLife());
        currentCreature.changeStatus();

        if (currentCreature.getStatus() == true) {      // Checks if the creature died from the attack.
            currentPlayer.takeHit(creatureAttack);
            System.out.println("The " + currentCreature.getName() + " hits you back for " + (creatureAttack * -1) + " damage.");
            System.out.println(currentPlayer.getLife());
            currentPlayer.checkPlayer();         // Checks if the players hitpoints is under 0, and marks him/her as dead if so. 
            if (currentPlayer.getDead() == true) {   // Checks if the player died from the attack. 
                System.out.println("Oh no! You've died! The game is now over, and will exit the next time you enter any command.");

            }
        } else {
            if(currentCreature.getName() == "dragon") {
                System.out.println("The " + currentCreature.getName() + " has died!");
                currentPlayer.changeStatus();
                currentRoom.removeCreature();
                endGameDescription();
                System.exit(0);
                
            } else if(currentCreature.getName() == "golem") {
                System.out.println("With it's final breath, the golem whispers:");
                System.out.println("To find the hidden chamber.. use your head..");
                currentPlayer.changeStatus();
                currentRoom.removeCreature();
                System.out.println("\n" + currentRoom.getLongDescription());
            } else {
                System.out.println("The " + currentCreature.getName() + " has died!");
                currentPlayer.changeStatus();
                currentRoom.removeCreature();
                System.out.println("\n" + currentRoom.getLongDescription());
            }
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
    private void startConversation(Command command) {
        if (currentPlayer.getStatus() == false) {
            if(!command.hasSecondWord()) {
                System.out.println("Talk to what?");
                return;
            }
            String npc = command.getSecondWord();
            
            if(!currentRoom.hasNPC(npc)) {
                System.out.println("You can't talk with that!");
            } else {
                currentNPC = currentRoom.getNPC(npc);
                if(!currentNPC.getName().equals(voice.getName())) {
                    System.out.println(currentNPC.getWelcomeMessage());
                    currentNPC.returnQuestions();
                    while(currentNPC.chosenAnswer!=4){
                        System.out.println(currentNPC.switchAnswers());
                        //currentNPC.returnQuestions();
                    }
                    currentRoom.getLongDescription();
                } else {
                    System.out.println(currentNPC.getWelcomeMessage());
                    currentNPC.returnQuestions();
                    while(currentNPC.chosenAnswer!=4){
                        System.out.println(currentNPC.switchAnswers());
                        if(currentNPC.chosenAnswer==2 || currentNPC.chosenAnswer==3) {
                            System.out.println("You take a hit of 10 health points");
                            currentPlayer.takeHit(-10);
                            //currentNPC.returnQuestions();
                        }
                        if(currentNPC.chosenAnswer == 1) {
                            currentRoom.removeNPC();
                            break;
                        }
                    }
                    
                }
                System.out.println(currentRoom.getLongDescription());
            }
        }
    }
    private void endGameDescription() {
        if(tiger.isAlive == false) {
            System.out.println("");
            System.out.println("Suddenly the volcano chamber starts to collapse.");
            System.out.println("You quickly grap the unconscious girl lying on the floor and run out of the chamber.");
            System.out.println("On your way down of the mountain you hear the chamber exploding behind you.");
            System.out.println("You quickly take cover from the explosion..");
            System.out.println("After a while the girl wakes up, amazed not to be in the dragon’s lair no more.");
            System.out.println("You take her back to her village.");
            System.out.println("The village is ecstatic to get the chief’s daughter back from the evil dragon.");
            System.out.println("To thank you for your heroism the village helps you escape the island and get to the boat you saw in the horizon.");
            System.out.println("");
            System.out.println("You have escaped the Isle of Zuul!");
            System.out.println("Thanks for playing, and congratulations!");
        } else {
            System.out.println("");
            System.out.println("Suddenly the volcano chamber starts to collapse.");
            System.out.println("You quickly grap the unconscious girl lying on the floor and run out of the chamber.");
            System.out.println("On your way down of the mountain you hear the chamber exploding behind you.");
            System.out.println("You quickly take cover from the explosion..");
            System.out.println("After a while the girl wakes up, amazed not to be in the dragon’s lair no more.");
            System.out.println("On the way back you get attacked by a tiger laying in the shadows.");
            System.out.println("You stall the tiger while the girl makes her escape to the village.");
            System.out.println("In the fight with the tiger you get wounded badly, but still manages to get back to the village before you collapse.");
            System.out.println("You wake up after a few weeks to the shocking realization that the boat already is gone.");
            System.out.println("For your heroism you are welcomed as a new citizen in the village.");
            System.out.println("");
            System.out.println("You have survived the Isle of Zuul!");
            System.out.println("Thanks for playing, and congratulations!");
        }
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
