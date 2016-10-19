/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zuulworld;

/**
 *
 * @author mizen
 */
public class NPC {
    
    String welcomeMessage;
    String answers[][];
    
    
    
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
    
    
    public String switchAnswers(int answer) {
        switch(answer){
            case 1: return answers[0][0];
            break;
            case 2: return answers[0][1];
            break;
            case 3: return answers[0][2];
            break;
            default: return "No answer found, please input a value between 1-3";
                   
        }
    }

    public void setAnswers() {
        
    }
    
    
}
