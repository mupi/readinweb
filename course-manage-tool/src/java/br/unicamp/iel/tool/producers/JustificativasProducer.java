package br.unicamp.iel.tool.producers;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;

/**
  *
 * @author Virgilio Santos
 *
 */
public class JustificativasProducer implements ViewComponentProducer {

    private static Log logger = LogFactory.getLog(JustificativasProducer.class);

    @Setter
    private ReadInWebClassManagementLogic logic;

    public static final String VIEW_ID = "turmas";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {


    }
}