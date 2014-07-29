package br.unicamp.iel.tool.producers;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebCourseLogic;

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

        Long course = logic.getCourseId();

        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(SummaryProducer.VIEW_ID));
        UIInternalLink.make(tofill, "link_justification", viewparams);

        UIOutput.make(tofill, "user", logic.getUser().getFirstName());

        if(logic.blockUser() && !logic.hasSentExplanation()){

        }

    }
}