/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;
import java.util.*;

/**
 *
 * @author mizen
 */
public class NPC {
    
    // Rewrite to an interface!!
    // Maybe hashmap?
    // https://docs.oracle.com/javase/tutorial/java/concepts/interface.html

    
    String welcomeMessage;
    String answer[] = new String[3];
    String question[] = new String[3];
    Scanner sc = new Scanner(System.in);
    
    
    
    public NPC(String name, String welcomeMessage) {
        
    }
    
    public void setWelcomeMessage(String set){
        welcomeMessage = set;
    }
    
    public String getWelcomeMessage() {
        return welcomeMessage;
    }
    
//    public void setFirstAnswerOne(String set) {
//        set = firstAnswerOne;
//    }
//    
//    public String firstResponseOne(int i) {
//        if(i==1) {
//            return firstAnswerOne; 
//        }
//        else if(i==2) {
//            return firstAnswerTwo;
//        }
//        else if(i==3) {
//            return firstAnswerTwo;
//        }
//        else {
//            return noRealAnswer;
//        }
    /**
     * 
     * @param set1
     * @param set2
     * @param set3 
     */
    public void setQuestions(String set1, String set2, String set3) {
        question[0] = set1;
        question[1] = set2; 
        question[2] = set3;
    }
    
    public void setAnswers (String set1, String set2, String set3) {
        answer[0]= set1;
        answer[1]= set2;
        answer[2] = set3;
    }
    
    public String switchAnswers(int numberInput) {
        
//        String chosenAnswer;
        System.out.println("Please choose one of the following three answers:");
        System.out.println("1: " + question[0]);
        System.out.println("2: " + question[1]);
        System.out.println("3: " + question[2]);
        System.out.println();

//        switch(answer){
//            case 1: chosenAnswer = answers[0];
//            break;
//            case 2: chosenAnswer = answers[1];
//            break;
//            case 3: chosenAnswer = answers[2];
//            break;
//            default: chosenAnswer = "No answer found, please input a value between 1-3";
           
                        do {
            switch (numberInput) {
                case 1:
                    return answer[0];
                case 2:
                    return answer[1];
                case 3:
                    return answer[2];
                case 4:
                    break;
            }
                        }
            while(numberInput!=4);
        
                        return "Goodbye";
                        
                        
                        }
//        return chosenAnswer;
    }
    
    
}
