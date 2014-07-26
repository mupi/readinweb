package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.AccessTypes;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Strategy;
import br.unicamp.iel.model.StrategyType;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

public class EstrategiaProducer implements ViewComponentProducer,
ViewParamsReporter {

    private static Log logger = LogFactory.getLog(EstrategiaProducer.class);
    @Setter
    private ReadInWebCourseLogic logic;

    // The VIEW_ID must match the html template (without the .html)
    public static final String VIEW_ID = "estrategias";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        CourseViewParameters parameters = (CourseViewParameters) viewparams;
        Course course = logic.getCourse(parameters.course);
        Activity activity;
        Module module;

        if(course == null){
            System.out.println("Course is null");
            return;
        } else {
            activity = logic.getActivity(parameters.activity);
            module = logic.getModule(parameters.module);
        }

        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(),
                logic);
        CourseComponents.createBreadCrumb(tofill, module, activity);

        logic.registerAccess(AccessTypes.STRATEGY.getTitle(),
                this.getViewID(), activity);

        List<Strategy> strategies =
                new ArrayList<Strategy>(logic.getStrategies(activity));

        // Show Strategies
        for(Strategy strategy : strategies){
            if (strategy.getType()
                    .equals(StrategyType.LANGUAGE.getValue())) { //aquisicao
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLi:");
                UIVerbatim.make(rowMod,
                        "divli", strategy.getBody());
            } else if (strategy.getType()
                    .equals(StrategyType.READING.getValue())) { //leitura
                UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                        "li-rowsEstLe:");
                UIVerbatim.make(rowMod,
                        "divle", strategy.getBody());
            }
        }
    }

    @Override
    public ViewParameters getViewParameters() {
        return new CourseViewParameters(this.getViewID());
    }
}
