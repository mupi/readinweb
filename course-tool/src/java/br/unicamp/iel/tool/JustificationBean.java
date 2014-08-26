package br.unicamp.iel.tool;

import java.util.Date;

import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.types.JustificationStateTypes;
import br.unicamp.iel.tool.commons.CourseComponents;

@Data
public class JustificationBean {
    private String explanation;
    private String message;

    @Setter
    private ReadInWebCourseLogic logic;

    public String sendJustification(){
        if(explanation != null) {
            Justification justification = new Justification();
            justification.setSentDate(new Date());
            justification.setState(new Byte((byte)0));
            justification.setUser(logic.getUserId());
            justification.setSite(logic.getCurrentSiteId());
            justification.setExplanation(explanation);
            try{
                logic.sendJustification(justification);
                return CourseComponents.DATA_SENT;
            } catch (Exception e) {
                e.printStackTrace();
                if(justification.getId() != null){
                    logic.deleteEntity(justification);
                }
                return CourseComponents.DATA_SEND_FAILED;
            }

        } else {
            return CourseComponents.DATA_EMPTY;
        }
    }

    private Long justificationId;
    public String sendMessage(){
        if(message != null){
            JustificationMessage justificationMessage =
                    new JustificationMessage();
            justificationMessage.setJustification(
                    logic.getJustification(justificationId));
            justificationMessage.setMessage(message);
            justificationMessage.setUser(logic.getUserId());
            justificationMessage.setDateSent(new Date());
            try{
                logic.sendJustificationMessage(justificationMessage);
                return CourseComponents.DATA_SENT;
            }catch (Exception e){
                e.printStackTrace();
                return CourseComponents.DATA_SEND_FAILED;
            }
        } else {
            return CourseComponents.DATA_EMPTY;
        }
    }
}
