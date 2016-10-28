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
    int chosenAnswer;
    HashMap<Integer, String> answer = new HashMap<>();
    HashMap<Integer, String> question = new HashMap<>();
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
        question.put(1, set1);
        question.put(2, set2);
        question.put(3, set3);
    }
    
    public void setAnswers (String set1, String set2, String set3) {
        answer.put(1, set1);
        answer.put(2, set2);
        answer.put(3, set3);
        answer.put(4, "Goodbye");
    }
    
    public void returnQuestions() {
        System.out.println("Please choose one of the following three answers:");
        System.out.println("1: " + question.get(1));
        System.out.println("2: " + question.get(2));
        System.out.println("3: " + question.get(3));
        System.out.println();
    }
    
    public int chosenAnswer() {
        return chosenAnswer;
    }
    
    public String switchAnswers() {
        int key;
        String value;    
        
        System.out.print(">");
        key = sc.nextInt();
        value = answer.get(key);
        chosenAnswer = key;
        if(!answer.containsKey(key)){
            return "Not a possible answer";
        }
        return value;
//        }
        
//        while(key!=4);
        
//        }
        
        

//        switch(answer){
//            case 1: chosenAnswer = answers[0];
//            break;
//            case 2: chosenAnswer = answers[1];
//            break;
//            case 3: chosenAnswer = answers[2];
//            break;
//            default: chosenAnswer = "No answer found, please input a value between 1-3";
//           
//                        do {
//                             numberInput = sc.nextInt();
//            switch (numberInput) {
//                case 1:
//                    return answer.get(2);
//                case 2:
//                    return answer[1];
//                case 3:
//                    return answer[2];
//                case 4:
//                    break;
//                    
//            }
//                        }
//            while(numberInput!=4);  
//        
//                        return "Goodbye";
//                        
//                        
//                        }
//        return chosenAnswer;
}
}


