package br.unicamp.iel.tool;

import java.util.Date;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.request.EarlyRequestParser;
import lombok.Data;
import lombok.Setter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.ReadInWebUserControl;
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
            Justification justification =
                    logic.getJustification(justificationId);
            justificationMessage.setJustification(justification);
            justificationMessage.setMessage(message);
            justificationMessage.setUser(logic.getUserId());
            justificationMessage.setDateSent(new Date());


            justification.setState(JustificationStateTypes
                    .flagUnread(justification.getState()));
            try{
                logic.sendJustificationMessage(justificationMessage);
                logic.updateJustification(justification);
                return CourseComponents.DATA_SENT;
            }catch (Exception e){
                e.printStackTrace();
                return CourseComponents.DATA_SEND_FAILED;
            }
        } else {
            return CourseComponents.DATA_EMPTY;
        }
    }

    public String acceptJustification(){
        handleUserStatus(true);
        return CourseComponents.DATA_SENT;
    }
    public String refuseJustification(){
        handleUserStatus(false);
        return CourseComponents.DATA_SENT;
    }

    private void handleUserStatus(boolean approved){
        Justification justification = logic.getJustification(justificationId);

        Byte state = justification.getState();
        state = JustificationStateTypes.markEvaluated(state);
        state = JustificationStateTypes.markAsRead(state);

        Date evalDate = new Date();

        justification.setState(state);
        justification.setEvaluatedDate(evalDate);
        logic.updateJustification(justification);

        ReadInWebUserControl userControl = logic.getUserControl(
                justification.getUser(), justification.getSite());

        logic.updateBlockInfo(userControl, evalDate);
        if(approved){
            logic.unblockUser(userControl);
        }
    }

}
