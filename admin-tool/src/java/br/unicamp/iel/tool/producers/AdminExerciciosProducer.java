package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.ActivityMenuComponent;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminExerciciosProducer implements ViewComponentProducer,
		ViewParamsReporter, NavigationCaseReporter {

	private static Log logger = LogFactory
			.getLog(AdminExerciciosProducer.class);

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
		Activity activity = null;
		Module module;
		Course course;
		Exercise exercise;

		CourseViewParameters parameters = (CourseViewParameters) viewparams;

		if (parameters.newdata) { // Nothing setted
			exercise = logic.getLastExerciseAdded();
		} else if (parameters.dataupdated) { // Nothing setted
			exercise = logic.getLastUpdatedExercise();
		} else if (parameters.datadeleted) {
			activity = logic.getLastUpdatedActivity();
			exercise = logic.getActivityFirstExercise(activity);
		} else if (parameters.exercise != null) {
			exercise = logic.getExercise(parameters.exercise);
		} else if (parameters.activity != null) {
			activity = logic.getActivity(parameters.activity);
			exercise = logic.getActivityFirstExercise(activity);
		} else { // Nothing setted =(
			return;
		}

		if (activity == null) {
			activity = logic.getActivity(exercise.getActivity().getId());
		}
		module = logic.getModule(activity.getModule().getId());
		course = logic.getCourse(module.getCourse().getId());

		// FIXME get the last modified exercise and load it

		ActivityMenuComponent menu = new ActivityMenuComponent(logic,
				viewparams, activity, course, exercise, getViewID());
		menu.fillComponents(tofill, "activity_menu_replace:");

		CourseComponents.createBreadCrumb(tofill, activity, module,
				this.getViewID());

		List<Exercise> exercises = logic.getExercises(activity);
		if (exercise == null && !exercises.isEmpty()) {
			exercise = exercises.get(0);
		}

		// UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
		// "ul_exercicios");
		for (Exercise e : exercises) {
			UIBranchContainer rowMod = UIBranchContainer.make(tofill,
					"exercise_link_container:", Long.toString(e.getId()));

			CourseViewParameters params = new CourseViewParameters(
					parameters.course, e.getId(), parameters.activity);
			params.viewID = this.getViewID();

			UIInternalLink link = UIInternalLink.make(rowMod, "exercise_link",
					e.getTitle(), params);

			link.updateFullID("input_link_e_" + e.getId());
		}

		/** Update form **/
		if (exercise != null) {
			UIForm exerciseForm = UIForm.make(tofill, "exercise_form");
			exerciseForm.parameters.add(new UIELBinding(
					"#{AdminActivityBean.exerciseId}", Long.toString(exercise
							.getId())));

			UIInput.make(exerciseForm, "exercise_position",
					"#{AdminActivityBean.exercisePosition}",
					Integer.toString(exercise.getPosition()));

			UIInput.make(exerciseForm, "exercise_title",
					"#{AdminActivityBean.exerciseTitle}", exercise.getTitle());

			UIInput.make(exerciseForm, "exercise_description",
					"#{AdminActivityBean.exerciseDescription}",
					exercise.getDescription());

			UIInput.make(exerciseForm, "exercise_path",
					"#{AdminActivityBean.exercisePath}",
					exercise.getExercise_path());

			UIInput.make(exerciseForm, "exercise_answer",
					"#{AdminActivityBean.exerciseAnswer}", exercise.getAnswer());

			UICommand.make(exerciseForm, "exercise_save",
					"#{AdminActivityBean.updateExercise}");

			UICommand.make(exerciseForm, "exercise_delete",
					"#{AdminActivityBean.deleteExercise}");
		}
		/** Add Form **/

		UIForm addExerciseForm = UIForm.make(tofill, "add_exercise_form");
		addExerciseForm.parameters.add(new UIELBinding(
				"#{AdminActivityBean.exerciseActivity}", Long.toString(activity
						.getId())));

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

		/** Send Data Form **/
		UIForm form = UIForm.make(tofill, "uploadexercise");
		form.parameters.add(new UIELBinding("#{UploaderBean.course}", course
				.getId()));

		form.parameters.add(new UIELBinding("#{UploaderBean.activity}",
				activity.getId()));

		form.parameters.add(new UIELBinding("#{UploaderBean.exercise}",
				exercise.getId()));

		UICommand.make(tofill, "send_exercise",
				"#{UploaderBean.exerciseHandler}");

	}

	@Override
	public ViewParameters getViewParameters() {
		return new CourseViewParameters(this.getViewID());
	}

	@Override
	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> l = new ArrayList<NavigationCase>();
		l.add(new NavigationCase(CourseComponents.SAVED,
				new CourseViewParameters(AdminExerciciosProducer.VIEW_ID, true,
						false, false)));
		l.add(new NavigationCase(CourseComponents.UPDATED,
				new CourseViewParameters(AdminExerciciosProducer.VIEW_ID,
						false, true, false)));
		l.add(new NavigationCase(CourseComponents.DELETED,
				new CourseViewParameters(AdminExerciciosProducer.VIEW_ID,
						false, false, true)));
		return l;
	}
}