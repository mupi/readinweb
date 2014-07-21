package br.unicamp.iel.tool.producers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;

/**
 * ViewParamsReporter - tem que herdar esse infeliz para receber parametros
 *
 * @author andre
 *
 */
public class AdminSendContentProducer implements ViewComponentProducer {

    private static Log logger = LogFactory.getLog(AdminSendContentProducer.class);
    public static final String VIEW_ID = "send_content";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(AdminCreateCourseProducer.VIEW_ID));
    }

}