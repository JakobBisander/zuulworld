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
    
    String welcomeMessage;
    String answer[] = new String[3];
    String question[] = new String[3];
    
    
    
    public NPC(String name, String welcomeMessage) {
        
    }
    
    public void setWelcomeMessage(String set){
        set = welcomeMessage;
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
    
    public void setQuestions(String set1, String set2, String set3) {
        set1 = question[0];
        set2 = question[1];
        set3 = question[2];
    }
    
    public void setAnswers (String set1, String set2, String set3) {
        set1 = answer[0];
        set2 = answer[1];
        set3 = answer[2];
    }
    
    public String switchAnswers(int numberInput) {
        
//        String chosenAnswer;
        System.out.println("Please choose one of the following three answers:");
        System.out.println("1: " + question[0]);
        System.out.println("2: " + question[1]);
        System.out.println("3: " + question[2]);

//        switch(answer){
//            case 1: chosenAnswer = answers[0];
//            break;
//            case 2: chosenAnswer = answers[1];
//            break;
//            case 3: chosenAnswer = answers[2];
//            break;
//            default: chosenAnswer = "No answer found, please input a value between 1-3";
            
                        if (numberInput!=4) {
            switch (numberInput) {
                case 1:
                    return answer[0];
                case 2:
                    return answer[2];
                case 3:
                    return answer[3];
                default:
                    break;
            }
        }
                         else {
                    return "Goodbye";
                        }
                        return "That is not an answer!";
//        return chosenAnswer;
    }
    
    
}
