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
        Course course;
        Activity activity;
        Module module;

        if(logic == null){
            System.out.println("Logic bean is null");
            return;
        } else {
            activity = logic.getActivity(parameters.activity);
            module = logic.getModule(parameters.module);
            course = logic.getCourse(parameters.course);
        }

        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(), logic);

        // Fills the forms
        UIForm.make(tofill, "input_form_tst_m");

        UIOutput.make(tofill, "current_mod", Long.toString(module.getPosition()));
        UIOutput.make(tofill, "current_act", Long.toString(activity.getPosition()));
        UIVerbatim.make(tofill, "current_title", activity.getTitle());

        logic.registerAccess("Acesso a gramatica.", this.getViewID(), activity);

        String header = "<div id='title_gramatica'>GRAM&Aacute;TICA</div>";
        UIVerbatim.make(tofill, "conteudo_gramatica_extendido", header + module.getGrammar());
    }
    
    @Override
    public ViewParameters getViewParameters() {
        return (new CourseViewParameters(this.getViewID()));
    }
}
