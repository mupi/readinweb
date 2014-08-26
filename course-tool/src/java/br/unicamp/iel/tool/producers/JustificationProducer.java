package br.unicamp.iel.tool.producers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;

/**
 *
 * @author Andre Zanchetta
 * @author Virgilio Santos
 *
 */
public class JustificationProducer implements ViewComponentProducer {

    public static final String VIEW_ID = "justificativa";

    private static Log logger = LogFactory.getLog(JustificationProducer.class);

    @Setter
    private ReadInWebCourseLogic logic;

    @Override
    public String getViewID() {
        return VIEW_ID;
    }


    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(SummaryProducer.VIEW_ID));
        UIInternalLink.make(tofill, "link_justification", viewparams);

        UIOutput.make(tofill, "user", logic.getUser().getFirstName());

        List<Justification> justifications = logic.getUserJustifications();
        if(logic.blockUser()) {
            UIBranchContainer userAlert =
                    UIBranchContainer.make(tofill, "user_alert_message:");
            UIOutput.make(userAlert, "user", logic.getUser().getDisplayName());

            UIBranchContainer container = UIBranchContainer.make(tofill,
                    "justification_container:");
            if(logic.hasSentExplanation()){
                Justification j = justifications.get(0);
                justifications.remove(0);

                UIBranchContainer addMessage =
                        UIBranchContainer.make(tofill, "add_message:");
                UIOutput.make(addMessage, "current_justification",
                        j.getExplanation());
                List<JustificationMessage> messages =
                        logic.getJustificationMessages(j);
                for(JustificationMessage jm : messages){
                    UIOutput.make(addMessage, "current_justification_message:",
                            jm.getMessage());
                }

                UIBranchContainer messageContainer = UIBranchContainer
                        .make(addMessage, "send_message_item:");
                UIForm messageForm =
                        UIForm.make(messageContainer, "message_form");

                messageForm.parameters.add(
                        new UIELBinding("#{JustificationBean.justificationId}",
                                j.getId()));

                UIInput.make(messageForm, "message",
                        "#{JustificationBean.message}");

                UICommand.make(messageForm, "send_message",
                        "#{JustificationBean.sendMessage}");

            } else {
                UIForm justificationForm =
                        UIForm.make(container, "justification_form");
                UIInput.make(justificationForm, "explanation",
                        "#{JustificationBean.explanation}");
                UICommand.make(justificationForm, "send_justification",
                        "#{JustificationBean.sendJustification}");
            }
        }

        for(Justification j : justifications){
            UIBranchContainer old_justifications =
                    UIBranchContainer.make(tofill, "old_justifications:");
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            UIOutput.make(old_justifications, "old_sent_date",
                    df.format(j.getSentDate()));
            if(j.getEvaluatedDate() != null){
                UIOutput.make(old_justifications, "old_evaluated_date",
                        df.format(j.getEvaluatedDate()));
            }
            UIOutput.make(old_justifications, "old_explanation",
                    j.getExplanation());

            List<JustificationMessage> messages =
                    logic.getJustificationMessages(j);
            for(JustificationMessage jm : messages){
                UIOutput.make(old_justifications, "old_justification_message:",
                        jm.getMessage());
            }
        }

    }
}