package br.unicamp.iel.tool.commons;

import java.util.ArrayList;
import java.util.List;

import uk.org.ponder.rsf.builtin.UVBProducer;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInitBlock;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ViewRoot;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.producers.AdminEstrategiasProducer;
import br.unicamp.iel.tool.producers.AdminExerciciosProducer;
import br.unicamp.iel.tool.producers.AdminGramaticaProducer;
import br.unicamp.iel.tool.producers.AdminTextProducer;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

public class CourseComponents {
	/**
	 * RSF Flow constants
	 *
	 * This strings should be returned within the binded methods on their beans
	 */
	public static final String CREATED = "Created";
	public static final String CREATE_FAIL = "Creation has failed";
	public static final String DATA_SAVED = "Data saved";
	public static final String DATA_SAVING_FAIL = "Fail to save data";
	public static final String DATA_EMPTY = "No data sent";
	public static final String UPDATE_FAIL = "Update failed";
	public static final String UPDATED = "Successfully updated";
	public static final String SAVED = "Saved";
	public static final String SAVE_FAIL = "Failed to be saved";
	public static final String DELETED = "Deleted";
	public static final String DELETE_FAIL = "Failed to delete strategy";
	public static final String ACTIVITY_ADDED = "Activity added";
	public static final String ACTIVITY_UPDATED = "Activity updated";
	public static final String ACTIVITY_DELETED = "Activity deleted";

	public static void loadMenu(Activity activity, Course course,
			Exercise exercise, UIContainer tofill) {
		CourseViewParameters parameters;

		// Checking view parameters
		if (exercise != null) {
			parameters = new CourseViewParameters(course.getId(),
					exercise.getId(), activity.getId());
		} else {
			parameters = new CourseViewParameters(course.getId(),
					activity.getId());
		}

		// Menu links

		parameters.viewID = AdminTextProducer.VIEW_ID;
		UIInternalLink.make(tofill, "linktext", parameters);

		parameters.viewID = AdminExerciciosProducer.VIEW_ID;
		UIInternalLink.make(tofill, "linkexercise", parameters);

		parameters.viewID = AdminEstrategiasProducer.VIEW_ID;
		UIInternalLink.make(tofill, "linkstrategy", parameters);

		parameters.viewID = AdminGramaticaProducer.VIEW_ID;
		UIInternalLink.make(tofill, "linkgrammar", parameters);
	}

	/**
	 *
	 * Module menu
	 *
	 * li-rowsMod: lnk_modulo div_atividades: p-rowsAct: input_link_a_
	 *
	 * @param tofill
	 * @param course
	 */
	public static void createModulesMenu(UIContainer tofill, Course course,
			String viewID, ReadInWebAdminLogic logic) {

		List<Module> modules = new ArrayList<Module>(logic.getModules(course));
		for (Module m : modules) {
			List<Activity> activities = new ArrayList<Activity>(
					logic.getActivities(m));

			UIBranchContainer ui_modules = UIBranchContainer.make(tofill,
					"li-rowsMod:");
			UIOutput.make(ui_modules, "lnk_modulo",
					("Módulo " + m.getPosition()));

			UIBranchContainer ui_activities = UIBranchContainer.make(
					ui_modules, "div_atividades:");
			for (Activity a : activities) {
				UIBranchContainer row = UIBranchContainer.make(ui_activities,
						"p-rowsAct:");
				CourseViewParameters cvp = new CourseViewParameters(
						course.getId(), m.getId(), a.getId());
				cvp.viewID = viewID;
				UIInternalLink.make(row, "input_link_a_",
						a.getPosition() + " - " + a.getTitle(), cvp)
						.updateFullID("input_link_a_" + a.getId());
			}
		}
	}

	public static void createBreadCrumb(UIContainer tofill, Activity activity,
			Module module, String viewID) {

		UIOutput.make(tofill, "current_mod",
				Long.toString(module.getPosition()));
		UIOutput.make(tofill, "current_act",
				Long.toString(activity.getPosition()));
		UIOutput.make(tofill, "current_title", activity.getTitle());
	}

	public static void debugIt(UIContainer tofill) {
		if (tofill instanceof ViewRoot) {
			((ViewRoot) tofill).debug = true;
		}
	}

	public static void checkParameters(Course course, Module module,
			Activity activity) {
		// activity belongs to module?
		// module belongs to course?
		if (!module.getId().equals(activity.getModule().getId())
				|| !course.getId().equals(module.getCourse().getId())) {
			return;

		}
	}

	public static void createDictionaryForms(UIContainer tofill,
			Activity activity) {
		UIBranchContainer DFormContainer = UIBranchContainer.make(tofill,
				"edit_dictionary_form_container:");

		UIForm updateDForm = UIForm
				.make(DFormContainer, "edit_dictionary_form");

		updateDForm.viewparams = new SimpleViewParameters(UVBProducer.VIEW_ID);

		UIInput id = UIInput.make(updateDForm, "edit_dictionary_id",
				"DictionaryAjaxBean.id");
		id.updateFullID("edit_dictionary_id");

		UIInput updateword = UIInput.make(updateDForm, "edit_dictionary_word",
				"DictionaryAjaxBean.word");
		updateword.updateFullID("edit_dictionary_word");

		UIInput updatemeaning = UIInput.make(updateDForm,
				"edit_dictionary_meaning", "DictionaryAjaxBean.meaning");
		updatemeaning.updateFullID("edit_dictionary_meaning");

		UICommand dicUpdate = UICommand.make(updateDForm,
				"edit_dictionary_update");
		dicUpdate.updateFullID("edit_dictionary_update");

		UIInitBlock.make(tofill, "update_call:", "RIW.updateWord",
				new Object[] { id, updateword, updatemeaning, dicUpdate,
						"DictionaryAjaxBean.update" });

		UICommand dicDelete = UICommand.make(updateDForm,
				"edit_dictionary_delete");
		dicDelete.updateFullID("edit_dictionary_delete");

		UIInitBlock.make(tofill, "update_call:", "RIW.deleteWord",
				new Object[] { id, dicDelete, "DictionaryAjaxBean.delete" });

		/** Add Form **/
		UIForm addDForm = UIForm.make(tofill, "add_dictionary_form");
		addDForm.viewparams = new SimpleViewParameters(UVBProducer.VIEW_ID);

		UIInput activityId = UIInput.make(addDForm, "add_dictionary_activity",
				"DictionaryAjaxBean.activity");
		activityId.updateFullID("add_dictionary_activity");
		activityId.setValue(Long.toString(activity.getId()));

		UIInput word = UIInput.make(addDForm, "add_dictionary_word",
				"DictionaryAjaxBean.word");
		word.updateFullID("add_dictionary_word");

		UIInput meaning = UIInput.make(addDForm, "add_dictionary_meaning",
				"DictionaryAjaxBean.meaning");
		meaning.updateFullID("add_dictionary_meaning");

		UICommand dicAdd = UICommand.make(addDForm, "add_dictionary_send");
		dicAdd.updateFullID("add_dictionary_send");

		UIInitBlock.make(tofill, "add_call:", "RIW.addWord", new Object[] {
				activityId, word, meaning, dicAdd, "DictionaryAjaxBean.add" });
	}

	public static void createFunctionalForms(UIContainer tofill, Course course) {
		UIBranchContainer FFormContainer = UIBranchContainer.make(tofill,
				"edit_functional_form_container:");

		UIForm updateFForm = UIForm
				.make(FFormContainer, "edit_functional_form");

		updateFForm.viewparams = new SimpleViewParameters(UVBProducer.VIEW_ID);

		UIInput id = UIInput.make(updateFForm, "edit_functional_id",
				"FunctionalWordAjaxBean.id");
		id.updateFullID("edit_functional_id");

		UIInput updateword = UIInput.make(updateFForm, "edit_functional_word",
				"FunctionalWordAjaxBean.word");
		updateword.updateFullID("edit_functional_word");

		UIInput updatemeaning = UIInput.make(updateFForm,
				"edit_functional_meaning", "FunctionalWordAjaxBean.meaning");
		updatemeaning.updateFullID("edit_functional_meaning");

		UICommand funcUpdate = UICommand.make(updateFForm,
				"edit_functional_update");
		funcUpdate.updateFullID("edit_functional_update");

		UIInitBlock.make(tofill, "update_call:", "RIW.updateWord",
				new Object[] { id, updateword, updatemeaning, funcUpdate,
						"FunctionalWordAjaxBean.update" });

		UICommand funcDelete = UICommand.make(updateFForm,
				"edit_functional_delete");
		funcDelete.updateFullID("edit_functional_delete");

		UIInitBlock
				.make(tofill, "update_call:", "RIW.deleteWord", new Object[] {
						id, funcDelete, "FunctionalWordAjaxBean.delete" });

		/** Add Form **/
		UIForm addFForm = UIForm.make(tofill, "add_functional_form");
		addFForm.viewparams = new SimpleViewParameters(UVBProducer.VIEW_ID);

		UIInput courseId = UIInput.make(addFForm, "add_functional_course",
				"FunctionalWordAjaxBean.course");
		courseId.updateFullID("add_functional_course");
		courseId.setValue(Long.toString(course.getId()));

		UIInput word = UIInput.make(addFForm, "add_functional_word",
				"FunctionalWordAjaxBean.word");
		word.updateFullID("add_functional_word");

		UIInput meaning = UIInput.make(addFForm, "add_functional_meaning",
				"FunctionalWordAjaxBean.meaning");
		meaning.updateFullID("add_functional_meaning");

		UICommand funcAdd = UICommand.make(addFForm, "add_functional_send");
		funcAdd.updateFullID("add_functional_send");

		UIInitBlock.make(tofill, "add_call:", "RIW.addWord",
				new Object[] { courseId, word, meaning, funcAdd,
						"FunctionalWordAjaxBean.add" });
	}
}
