package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.SendContentViewParameters;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;

/**
 *
 * @author Virgilio N Santos
 *
 */
public class AdminSendContentProducer implements ViewComponentProducer,
    NavigationCaseReporter, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(AdminSendContentProducer.class);
    public static final String VIEW_ID = "send_content";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        SendContentViewParameters viewParameters =
                (SendContentViewParameters) viewparams;

        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(AdminCreateCourseProducer.VIEW_ID));

        if(viewParameters.dataPosted){
            UIOutput.make(tofill, "senderror", viewParameters.errorMessage);
        }

        UIForm uploadform = UIForm.make(tofill, "uploadform" );
        UICommand.make(uploadform, "send_content",
                "#{AdminBean.handleContentSent}");

    }

    public List<NavigationCase> reportNavigationCases() {
        List<NavigationCase> cases = new ArrayList<NavigationCase>();
        cases.add(new NavigationCase(CourseComponents.DATA_SAVED,
                new SendContentViewParameters(true,
                        "Dados enviados com sucesso")));

        cases.add(new NavigationCase(CourseComponents.DATA_SAVING_FAIL,
                new SendContentViewParameters(true,
                        "Falha no envio de dados")));

        cases.add(new NavigationCase(CourseComponents.DATA_EMPTY,
                new SendContentViewParameters(true,
                        "Nenhum arquivo enviado")));
        return cases;
    }

    @Override
    public ViewParameters getViewParameters() {
        return new SendContentViewParameters();
    }
}