package br.unicamp.iel.tool.producers;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UIVerbatim;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebCourseLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.types.AccessTypes;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

public class GramaticaProducer implements ViewComponentProducer, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(GramaticaProducer.class);
    @Setter
    private ReadInWebCourseLogic logic;

    // The VIEW_ID must match the html template (without the .html)
    public static final String VIEW_ID = "gramatica";

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
        CourseComponents.createModulesMenu(tofill, course, getViewID(), logic);
        CourseComponents.createBreadCrumb(tofill, module, activity);

        logic.registerAccess(AccessTypes.GRAMMAR.getTitle(),
                this.getViewID(), activity);

        String header = "<div id='title_gramatica'>GRAM&Aacute;TICA</div>";
        UIVerbatim.make(tofill, "conteudo_gramatica_extendido",
                header + module.getGrammar());
    }

    @Override
    public ViewParameters getViewParameters() {
        return (new CourseViewParameters(this.getViewID()));
    }
}
