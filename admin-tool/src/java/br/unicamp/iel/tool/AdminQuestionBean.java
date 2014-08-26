package br.unicamp.iel.tool;

import lombok.Getter;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Question;

public class AdminQuestionBean {
    @Setter
    @Getter
    private Integer position;

    @Setter
    @Getter
    private String question;

    @Setter
    @Getter
    private String suggestedAnswer;

    @Setter
    private ReadInWebAdminLogic logic;

    public boolean updateQuestion(Long id){
        Question q = logic.getQuestion(id);

        if(position != null){
            q.setPosition(position);
        }
        if(question != null){
            q.setQuestion(question);
        }
        if(suggestedAnswer != null){
            q.setSuggestedAnswer(suggestedAnswer);
        }

        return logic.updateQuestion(q);
    }
}
