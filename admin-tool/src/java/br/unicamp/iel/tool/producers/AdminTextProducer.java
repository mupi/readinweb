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
import uk.org.ponder.rsf.components.UILink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.DictionaryWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Question;
import br.unicamp.iel.tool.commons.ActivityMenuComponent;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminTextProducer implements ViewComponentProducer,
		ViewParamsReporter, NavigationCaseReporter {

	private static Log logger = LogFactory.getLog(AdminTextProducer.class);
	public static final String VIEW_ID = "admin_text";

	@Setter
	private ReadInWebAdminLogic logic;

	@Override
	public String getViewID() {
		return VIEW_ID;
	}

	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		Activity activity;
		Module module;
		Course course;

		CourseViewParameters parameters = (CourseViewParameters) viewparams;

		if (parameters.newdata) { // Nothing setted
			activity = logic.getLastActivityAdded();
		} else if (parameters.dataupdated) { // Nothing setted
			activity = logic.getLastUpdatedActivity();
		} else if (parameters.datadeleted) {
			activity = logic.getCourseFirstActivity(logic.getCourseId());
		} else if (parameters.activity != null) {
			activity = logic.getActivity(parameters.activity);
		} else { // Nothing setted =(
			return;
		}

		module = logic.getModule(activity.getModule().getId());
		course = logic.getCourse(module.getCourse().getId());

		ActivityMenuComponent menu = new ActivityMenuComponent(logic,
				viewparams, activity, course, null, getViewID());
		menu.fillComponents(tofill, "activity_menu_replace:");

		CourseComponents.createBreadCrumb(tofill, activity, module,
				this.getViewID());

		UIForm updateActivityForm = UIForm.make(tofill, "activity_form");
		updateActivityForm.parameters.add(new UIELBinding(
				"#{AdminActivityBean.activityId}", activity.getId()));
		updateActivityForm.parameters.add(new UIELBinding(
				"#{AdminActivityBean.activityModule}", module.getId()));

		UIInput.make(updateActivityForm, "title_text",
				"#{AdminActivityBean.title}", activity.getTitle());

		UIInput.make(
				updateActivityForm,
				"eta_read",
				"#{AdminActivityBean.etaRead}",
				activity.getEtaRead() != null ? Integer.toString(activity
						.getEtaRead()) : "");

		UIInput.make(updateActivityForm, "prelecture_text",
				"#{AdminActivityBean.preReading}",
				activity.getPrereading() != null ? activity.getPrereading()
						: "");

		UIInput.make(updateActivityForm, "position_text",
				"#{AdminActivityBean.position}",
				Integer.toString(activity.getPosition()));

		UIInput.make(updateActivityForm, "text_text",
				"#{AdminActivityBean.text}", activity.getText());

		UICommand.make(updateActivityForm, "send_activity",
				"#{AdminActivityBean.updateActivity}");
		UICommand.make(updateActivityForm, "delete_activity",
				"#{AdminActivityBean.deleteActivity}");

		List<Question> questions = logic.getQuestions(activity);
		for (Question q : questions) {
			UIBranchContainer row = UIBranchContainer.make(tofill, "li-rows:",
					Integer.toString(q.getPosition()));
			UILink ui_l = UILink.make(row, "input_link_q_",
					Integer.toString(q.getPosition()),
					"/#question_" + q.getId());
			ui_l.updateFullID("input_link_q_" + q.getId());

			UIBranchContainer ui_bc = UIBranchContainer.make(tofill,
					"div_questions:");
			ui_bc.updateFullID("question_" + q.getId());

			UIForm ui_form = UIForm.make(ui_bc, "question_form");

			ui_form.parameters.add(new UIELBinding(
					"#{AdminActivityBean.questionId}", q.getId()));

			UIInput.make(ui_form, "question_position",
					"#{AdminActivityBean.questionPosition}",
					Long.toString(q.getPosition()));

			UIInput.make(ui_form, "question",
					"#{AdminActivityBean.questionData}", q.getQuestion());

			UIInput.make(ui_form, "suggested_answer",
					"#{AdminActivityBean.questionSuggested}",
					q.getSuggestedAnswer());

			UICommand.make(ui_form, "send_question",
					"#{AdminActivityBean.updateQuestion}");

			UICommand.make(ui_form, "delete_question",
					"#{AdminActivityBean.deleteQuestion}");
		}

		UIForm addQuestionForm = UIForm.make(tofill, "add_question_form");

		addQuestionForm.parameters.add(new UIELBinding(
				"#{AdminActivityBean.questionActivity}", activity.getId()));
		UIInput.make(addQuestionForm, "add_question_position",
				"#{AdminActivityBean.questionPosition}");
		UIInput.make(addQuestionForm, "add_question_question",
				"#{AdminActivityBean.questionData}");
		UIInput.make(addQuestionForm, "add_question_suggested_answer",
				"#{AdminActivityBean.questionSuggested}");
		UICommand.make(addQuestionForm, "add_question_send",
				"#{AdminActivityBean.saveQuestion}");

		UIForm addActivityForm = UIForm.make(tofill, "add_activity_form");

		addActivityForm.parameters.add(new UIELBinding(
				"#{AdminActivityBean.activityModule}", module.getId()));

		UIInput.make(addActivityForm, "add_activity_title",
				"#{AdminActivityBean.title}");

		UIInput.make(addActivityForm, "add_activity_position",
				"#{AdminActivityBean.position}");

		UICommand.make(addActivityForm, "add_activity",
				"#{AdminActivityBean.addActivity}");

		List<DictionaryWord> l_dw = new ArrayList<DictionaryWord>(
				logic.getDictionary(activity));
		for (DictionaryWord dw : l_dw) {
			UIBranchContainer dictionary = UIBranchContainer.make(tofill,
					"dictionary:");
			dictionary.updateFullID("dictionary_row_" + dw.getId());
			UIOutput.make(dictionary, "dictionary_id",
					Long.toString(dw.getId()));
			UIOutput.make(dictionary, "dictionary_word", dw.getWord());
			UIOutput.make(dictionary, "dictionary_meaning", dw.getMeaning());
		}

		CourseComponents.createDictionaryForms(tofill, activity);
	}

	@Override
	public ViewParameters getViewParameters() {
		return new CourseViewParameters(this.getViewID());
	}

	@Override
	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> l = new ArrayList<NavigationCase>();
		l.add(new NavigationCase(CourseComponents.ACTIVITY_ADDED,
				new CourseViewParameters(AdminTextProducer.VIEW_ID, true,
						false, false)));
		l.add(new NavigationCase(CourseComponents.ACTIVITY_UPDATED,
				new CourseViewParameters(AdminTextProducer.VIEW_ID, false,
						true, false)));

		l.add(new NavigationCase(CourseComponents.ACTIVITY_DELETED,
				new CourseViewParameters(AdminTextProducer.VIEW_ID, false,
						false, true)));
		return l;
	}

}