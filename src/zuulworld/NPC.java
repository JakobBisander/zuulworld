/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Vedsted
 */
public class NPC {
    
    // Rewrite to an interface!!
    // Maybe hashmap?
    // https://docs.oracle.com/javase/tutorial/java/concepts/interface.html

    
    private String welcomeMessage; // A welcome message that the object will return upon contact
    int chosenAnswer; // the key value for the answer chosen in a conversation. Used for checking when you want to leave the converstation
    HashMap<Integer, String> answer = new HashMap<>(); // HashMap for answers
    HashMap<Integer, String> question = new HashMap<>(); // HashMap for answers
    Scanner sc = new Scanner(System.in); // Scanner for getting input for the questions&answers
    private String name;
    private String npcDescription;
    
    
    
    public NPC(String name, String welcomeMessage, String npcDescription) {  // Constructor lets you assign a nae and welcome message.
        this.name = name;
        this.welcomeMessage = welcomeMessage;
        this.npcDescription = npcDescription;
    }
    
    public void setWelcomeMessage(String set){ // setting method for welcomeMessage
        welcomeMessage = set;
    }
    
    public String getWelcomeMessage() { // getter for welcomeMessage
        return welcomeMessage;
    }
    
    public String getNpcDescription() {
        return npcDescription;
    }
    
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param set1
     * @param set2
     * @param set3 
     */
    public void setQuestions(String set1, String set2, String set3) { // setting method for questions. Sets 3 questions for the player to choose from.
        question.put(1, set1);
        question.put(2, set2);
        question.put(3, set3);
        question.put(4, "Goodbye");
    }
    
    public void setAnswers (String set1, String set2, String set3) { // setting method for answers. Sets 3 answers that the player can get in return from his question
        answer.put(1, set1);
        answer.put(2, set2);
        answer.put(3, set3);
        answer.put(4, "Farewell!");
    }
    
    public void returnQuestions() { // Return the possible questions for the player to ask.
        chosenAnswer = -1;
        System.out.println("Please choose one of the following three answers:");
        System.out.println("1: " + question.get(1));
        System.out.println("2: " + question.get(2));
        System.out.println("3: " + question.get(3));
        System.out.println("4: " + question.get(4));
        
    }
    
    public int chosenAnswer() { // An integer set by switchAnswers. It is the key of the answers hashmap.
        return chosenAnswer;
    }
    
    public String switchAnswers() { // This method carries out the conversation itself.
        int key = -1; // key of the hashmap
        String response; // value of the hashmap
        
        System.out.print(">");
        try {
        key = sc.nextInt(); // Next scanner input will set the key value
        } catch (InputMismatchException e){
            String i = sc.nextLine(); //karrykode... i'm sorry man :(
        }
        response = answer.get(key); // Gets the string from the hashmap value at the key of "key"
        chosenAnswer = key; 
        if(!answer.containsKey(key)){ // This if-statement checks if the answer chosen is contained by the hashmap of answers.
            return "That's not a possible answer!";
        }
        return response;
}
}
