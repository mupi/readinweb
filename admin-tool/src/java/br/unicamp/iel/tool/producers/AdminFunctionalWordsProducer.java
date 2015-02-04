package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.FunctionalWord;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.ActivityMenuComponent;
import br.unicamp.iel.tool.commons.CourseComponents;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */
public class AdminFunctionalWordsProducer implements ViewComponentProducer,
		ViewParamsReporter {

	private static Log logger = LogFactory
			.getLog(AdminFunctionalWordsProducer.class);

	@Setter
	private ReadInWebAdminLogic logic;

	public static final String VIEW_ID = "admin_functional";

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

		CourseComponents.checkParameters(course, module, activity);

		ActivityMenuComponent menu = new ActivityMenuComponent(logic,
				viewparams, activity, course, null, getViewID());
		menu.fillComponents(tofill, "activity_menu_replace:");

		CourseComponents.createBreadCrumb(tofill, activity, module,
				this.getViewID());

		List<FunctionalWord> l_fw = new ArrayList<FunctionalWord>(
				logic.getFunctionalWords(course));
		for (FunctionalWord fw : l_fw) {
			UIBranchContainer functional = UIBranchContainer.make(tofill,
					"functional:");
			functional.updateFullID("functional_row_" + fw.getId());
			UIOutput.make(functional, "functional_id",
					Long.toString(fw.getId()));
			UIOutput.make(functional, "functional_word", fw.getWord());
			UIOutput.make(functional, "functional_meaning", fw.getMeaning());
		}

		CourseComponents.createFunctionalForms(tofill, course);

	}

	@Override
	public ViewParameters getViewParameters() {
		return new CourseViewParameters(this.getViewID());
	}
}