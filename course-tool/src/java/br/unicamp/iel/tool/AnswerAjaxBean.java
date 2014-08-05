package br.unicamp.iel.tool;

import lombok.Data;
import uk.org.ponder.rsf.state.scope.BeanDestroyer;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.ReadInWebAnswer;

@Data
public class AnswerAjaxBean {
    private Long question;
    private String answer;

    private ReadInWebCourseLogic logic;
    private BeanDestroyer destroyer;

    private String[] results;
    public String[] getResults(){
        saveUserAnswer();
        destroyer.destroy();
        return results;
    }

    public void saveUserAnswer(){
        try {
            ReadInWebAnswer uAnswer;
            uAnswer = logic.getAnswerByQuestionAndUser(question);

            if(uAnswer == null) { // id will be created at update
                uAnswer = new ReadInWebAnswer();
                uAnswer.setQuestion(logic.getQuestion(question));
                uAnswer.setUser(logic.getUserId());
            }
            if(answer != null){
                uAnswer.setAnswer(answer);
            }

            if(uAnswer.getId() != null){ // has id
                logic.updateAnswer(uAnswer);
            } else {
                logic.saveAnswer(uAnswer);
            }

            Long activity = logic.getQuestion(question).getActivity().getId();
            if(logic.hasUserAnsweredQuestions(activity)){
                logic.registerQuestionsDone(activity);
            }

            results = new String[] {"success"};
        } catch (Exception e){
            e.printStackTrace();
            results = new String[] {"error"};
        }
    }
}
