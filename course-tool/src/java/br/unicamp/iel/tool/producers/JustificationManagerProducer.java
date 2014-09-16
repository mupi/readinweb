package br.unicamp.iel.tool.producers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.api.User;

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
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.tool.viewparameters.JustificationViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class JustificationManagerProducer implements ViewComponentProducer, ViewParamsReporter {

    public static final String VIEW_ID = "justificativa-single";

    private static Log logger = LogFactory.getLog(JustificationManagerProducer.class);

    @Setter
    private ReadInWebCourseLogic logic;

    @Override
    public String getViewID() {
        return VIEW_ID;
    }


    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        if(!logic.isUserTeacher()){
            return;
        }

        JustificationViewParameters parameters =
                (JustificationViewParameters) viewparams;

        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(SummaryProducer.VIEW_ID));
        UIInternalLink.make(tofill, "link_justification", viewparams);

        UIBranchContainer container = UIBranchContainer.make(tofill,
                "current_justification_container:");

        Justification justification =
                logic.getJustification(parameters.justification);

        User student = logic.getSudent(justification.getUser());

        UIOutput.make(container, "username", student.getDisplayName());

        // TODO refuse acept form
        //        current_evaluate_form
        UIForm evaluateForm = UIForm.make(container, "current_evaluate_form");
        evaluateForm.parameters.add(
                new UIELBinding("#{JustificationBean.justificationId}",
                        justification.getId()));
        //        current_refuse
        UICommand.make(evaluateForm, "current_refuse",
                "#{JustificationBean.refuseJustification}");
        //        current_accept
        UICommand.make(evaluateForm, "current_accept",
                "#{JustificationBean.acceptJustification}");

        UIOutput.make(container, "current_justification_explanation",
                justification.getExplanation());

        UIOutput.make(container, "sent_date",
                justification.getSentDate().toString());

        UIOutput evaluatedDate = UIOutput.make(container, "evaluated_date");

        evaluatedDate.setValue(justification.getEvaluatedDate() == null ?
                "Ainda não avaliado" :
                    justification.getEvaluatedDate().toString());

        List<JustificationMessage> messages =
                logic.getJustificationMessages(justification);

        for(JustificationMessage jm : messages){
            UIBranchContainer currentMessage = UIBranchContainer.make(tofill,
                    "current_justification_message:");
            UIOutput messageUser = UIOutput.make(currentMessage, "message_user",
                    "Professor");
            if(student.getId().equals(jm.getUser())){
                messageUser.setValue(student.getDisplayName());
            }
            UIOutput.make(currentMessage, "message_message", jm.getMessage());
            UIOutput.make(currentMessage, "message_sent_date",
                    jm.getDateSent().toString());
        }

        // Show justification send message form

    }

    @Override
    public ViewParameters getViewParameters() {
        return new JustificationViewParameters();
    }
}