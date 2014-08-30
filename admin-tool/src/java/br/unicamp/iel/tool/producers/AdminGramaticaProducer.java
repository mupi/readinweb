package br.unicamp.iel.tool.producers;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
  *
 * @author Virgilio Santos
 *
 */
public class AdminGramaticaProducer implements ViewComponentProducer, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(AdminGramaticaProducer.class);

    @Setter
    private ReadInWebAdminLogic logic;

    public static final String VIEW_ID = "admin_gramatica";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        Activity activity;
        Module module;
        Course course;

        CourseViewParameters parameters = (CourseViewParameters) viewparams;
        if(parameters.newdata){ // Nothing setted
            activity = logic.getLastActivityAdded();
        } else if(parameters.dataupdated){ // Nothing setted
            activity = logic.getLastUpdatedActivity();
        } else if(parameters.datadeleted){
            activity = logic.getCourseFirstActivity(logic.getCourseId());
        } else if(parameters.activity != null){
            activity = logic.getActivity(parameters.activity);
        } else { // Nothing setted =(
            return;
        }

        module = logic.getModule(activity.getModule().getId());
        course = logic.getCourse(module.getCourse().getId());

        CourseComponents.checkParameters(course, module, activity);
        CourseComponents.loadMenu(activity, course, null, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(),
                logic);
        CourseComponents.createBreadCrumb(tofill, activity, module,
                this.getViewID());

        if(parameters.errortoken != null){
            System.out.println(parameters.errortoken);
        }

        UIForm updateGrammaryForm = UIForm.make(tofill, "grammar_form");

        updateGrammaryForm.parameters.add(
                new UIELBinding("#{AdminActivityBean.grammarModule}",
                        Long.toString(module.getId())));

        UIInput.make(updateGrammaryForm, "grammar",
                "#{AdminActivityBean.grammarData}",
                module.getGrammar());

        UICommand.make(updateGrammaryForm, "grammar_save",
                "#{AdminActivityBean.updateGrammar}");
    }

    @Override
    public ViewParameters getViewParameters() {
        return new CourseViewParameters(this.getViewID());
    }
}