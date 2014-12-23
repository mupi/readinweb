package br.unicamp.iel.tool.producers;

import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Justification;
import br.unicamp.iel.model.types.JustificationStateTypes;
import br.unicamp.iel.tool.components.GatewayMenuComponent;
import br.unicamp.iel.tool.viewparameters.JustificationViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class JustificationsProducer implements ViewComponentProducer {

    public static final String VIEW_ID = "justificativas";

    private static Log logger = LogFactory.getLog(JustificationsProducer.class);

    @Setter
    private ReadInWebCourseLogic logic;

    @Override
    public String getViewID() {
        return VIEW_ID;
    }


    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
    	boolean isTeacher = logic.isUserTeacher();
        if(!isTeacher){
            return;
        }

        GatewayMenuComponent menu = new GatewayMenuComponent(viewparams, 
        		isTeacher);
        menu.make(UIBranchContainer.make(tofill, "gateway_menu_replace:"));

        Site site = logic.getCurrentSite();

        UIOutput.make(tofill, "riw_class_name", site.getTitle());

        // Get all Justifications from this siteId
        List<Justification> justifications = logic.getClassJustifications();
        for(Justification j : justifications){
            User student = logic.getSudent(j.getUser());
            UIBranchContainer just =
                    UIBranchContainer.make(tofill, "justificativa:");
            UIBranchContainer oldJust =
                    UIBranchContainer.make(tofill, "old_justificativa:");
            if(JustificationStateTypes.toShow(j.getState())){
                UIOutput.make(just, "username", student.getDisplayName());
                UIOutput.make(just, "date_sent", j.getSentDate().toString());

                UIOutput.make(just, "num_messages",
                        Long.toString(logic.countMessages(j)));
                UIInternalLink.make(
                        (UIBranchContainer.make(just, "evaluate_col:")),
                        "evaluate_link",
                        new JustificationViewParameters(
                                JustificationManagerProducer.VIEW_ID,
                                j.getId()));
            } else {
                UIOutput.make(oldJust, "old_username",
                        student.getDisplayName());
                UIOutput.make(oldJust, "old_date_sent",
                        j.getSentDate().toString());

                UIOutput.make(oldJust, "old_num_messages",
                        Long.toString(logic.countMessages(j)));

                UIInternalLink.make(
                        (UIBranchContainer.make(just, "old_evaluate_col:")),
                        "old_evaluate_link",
                        new JustificationViewParameters(
                                JustificationManagerProducer.VIEW_ID,
                                j.getId()));
            }

        }

    }
}