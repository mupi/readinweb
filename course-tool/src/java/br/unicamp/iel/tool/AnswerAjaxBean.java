package br.unicamp.iel.tool;

import br.unicamp.iel.logic.ReadInWebCourseLogic;
import lombok.Data;

@Data
public class AnswerAjaxBean {
    private Long answer_id;
    private Long question;
    private String answer;
    
    private String[] results;
    public String[] getResults(){
        saveUserAnswer();
        return results;
    }
    
    private ReadInWebCourseLogic logic;
    
    public boolean saveUserAnswer(){
        String user = logic.getUserId();
        System.out.println("oeeaaa:" + question + " - " + answer);
        results = new String[] {"aaron", "is", "totally", "cool"};
        return false;
    }
}
