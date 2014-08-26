package br.unicamp.iel.tool.producers;

import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBoundString;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;
import br.unicamp.iel.tool.viewparameters.ExerciseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminExerciciosProducer implements ViewComponentProducer, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(AdminExerciciosProducer.class);

    @Setter
    private ReadInWebAdminLogic logic;

    public static final String VIEW_ID = "admin_exercicios";

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
        Exercise exercise = null;

        ExerciseViewParameters parameters = (ExerciseViewParameters) viewparams;

        if(parameters.module == null) parameters.module = 1L;
        if(parameters.activity == null) parameters.activity = 1L;

        course = logic.getCourse(parameters.course);
        activity = logic.getActivity(parameters.activity);
        module = logic.getModule(parameters.module);
        if(parameters.exercise != null){
            exercise = logic.getExercise(parameters.exercise);
        }

        CourseComponents.loadMenu(parameters, tofill);
        CourseComponents.createModulesMenu(tofill, course, this.getViewID(),
                logic);
        CourseComponents.createBreadCrumb(tofill, activity, module,
                this.getViewID());

        if(parameters.errortoken != null){
            System.out.println(parameters.errortoken);
        }

        List<Exercise> exercises = logic.getExercises(activity);
        if(exercise == null && !exercises.isEmpty()) {
            exercise = exercises.get(0);
        }

        //UIBranchContainer ui_bc = UIBranchContainer.make(tofill, "ul_exercicios");
        for(Exercise e : exercises){
            System.out.println("Oeaa!");
            UIBranchContainer rowMod = UIBranchContainer.make(tofill,
                    "exercise_link_container:",
                    Long.toString(e.getId()));

            ExerciseViewParameters evp =
                    new ExerciseViewParameters(parameters.course,
                            parameters.module, parameters.activity, e.getId());
            evp.viewID = this.getViewID();

            UIInternalLink link = UIInternalLink.make(rowMod, "exercise_link",
                    e.getTitle(), evp);

            link.updateFullID("input_link_e_" + e.getId());
        }

        /** Update form **/
        UIForm exerciseForm = UIForm.make(tofill, "exercise_form");
        exerciseForm.parameters.add(
                new UIELBinding("#{AdminActivityBean.exerciseId}",
                        Long.toString(exercise.getId())));

        UIInput.make(exerciseForm, "exercise_position",
                "#{AdminActivityBean.exercisePosition}",
                Integer.toString(exercise.getPosition()));

        UIInput.make(exerciseForm, "exercise_title",
                "#{AdminActivityBean.exerciseTitle}",
                exercise.getTitle());

        UIInput.make(exerciseForm, "exercise_description",
                "#{AdminActivityBean.exerciseDescription}",
                exercise.getDescription());

        UIInput.make(exerciseForm, "exercise_answer",
                "#{AdminActivityBean.exerciseAnswer}",
                exercise.getAnswer());

        UICommand.make(exerciseForm, "exercise_save",
                "#{AdminActivityBean.updateExercise}");

        UICommand.make(exerciseForm, "exercise_delete",
                "#{AdminActivityBean.deleteExercise}");


        /** Add Form **/

        UIForm addExerciseForm = UIForm.make(tofill, "add_exercise_form");
        addExerciseForm.parameters.add(
                new UIELBinding("#{AdminActivityBean.exerciseActivity}",
                        Long.toString(activity.getId())));

        UIInput.make(addExerciseForm, "add_exercise_position",
                "#{AdminActivityBean.exercisePosition}");

        UIInput.make(addExerciseForm, "add_exercise_title",
                "#{AdminActivityBean.exerciseTitle}");

        UIInput.make(addExerciseForm, "add_exercise_description",
                "#{AdminActivityBean.exerciseDescription}");

        UIInput.make(addExerciseForm, "add_exercise_answer",
                "#{AdminActivityBean.exerciseAnswer}");

        UICommand.make(addExerciseForm, "add_exercise_save",
                "#{AdminActivityBean.saveExercise}");


    }

    @Override
    public ViewParameters getViewParameters() {
        return new ExerciseViewParameters(this.getViewID());
    }
}