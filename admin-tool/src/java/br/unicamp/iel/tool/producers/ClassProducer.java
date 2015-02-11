package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.ClassMenuComponent;
import br.unicamp.iel.tool.commons.ManagerComponents;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */

public class ClassProducer implements ViewComponentProducer,
		ViewParamsReporter, NavigationCaseReporter {

	private static Log logger = LogFactory.getLog(ClassProducer.class);

	@Setter
	private ReadInWebClassManagementLogic logic;

	public static final String VIEW_ID = "turma";

	@Override
	public String getViewID() {
		return VIEW_ID;
	} // end getViewID()

	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		ClassViewParameters cvp = (ClassViewParameters) viewparams;

		if (cvp.course == null) {
			return;
		}
		// ManagerComponents.loadMenu(viewparams, tofill);
		ClassMenuComponent menu = new ClassMenuComponent(cvp);
		menu.make(UIBranchContainer.make(tofill, "class_menu_replace:"));

		Course course = logic.getCourse(cvp.course);
		UIOutput.make(tofill, "course_name", course.getTitle());

		Site riwClass = null;
		if (cvp.classAdded) {
			System.out.println("class added");
			riwClass = logic.getLastAddedReadInWebClass(course.getId());
		} else if (cvp.classChanged) {
			System.out.println("class changed");
			riwClass = logic.getLastModifiedReadInWebClass(course.getId());
		} else {
			System.out.println("class just class!");
			riwClass = logic.getReadInWebClass(cvp.siteId);
		}

		UIOutput.make(tofill, "riw_class_name", riwClass.getTitle());

		UIOutput.make(tofill, "riw_startdate", logic.getStartDate(riwClass)
				.toString());

		UIOutput.make(tofill, "riw_students_count",
				Long.toString(logic.countStudents(riwClass)));

		UIOutput.make(tofill, "riw_activities_published",
				Long.toString(logic.countPublishedActivities(riwClass)));

		ArrayList<User> riwStudents = new ArrayList<User>(
				logic.getStudents(riwClass));
		UIBranchContainer studentsTable = UIBranchContainer.make(tofill,
				"riw_students:");

		// UIForm classState = UIForm.make(tofill, "form_class_state");
		// Boolean state = logic.getReadInWebClassState(riwClass);
		//
		// classState.parameters.add(new UIELBinding(
		// "#{ManageClassBean.riwClass}", riwClass.getId()));
		//
		// classState.parameters.add(new UIELBinding(
		// "#{ManageClassBean.classState}", Boolean.toString(!state)));
		// if (state) { // render pause
		// UICommand.make(classState, "pause_class",
		// "#{ManageClassBean.changeClassState}");
		// } else { // render play
		// UICommand.make(classState, "play_class",
		// "#{ManageClassBean.changeClassState}");
		// }

		for (User u : riwStudents) {
			UIBranchContainer studentRow = UIBranchContainer.make(
					studentsTable, "riw_student:");

			if (logic.isUserBlocked(u, riwClass)) {
				studentRow.decorate((new UIStyleDecorator("danger")));
			}

			UIOutput.make(studentRow, "student_name", u.getDisplayName());
			//
			// // TODO add class danger on blocked user
			// UIOutput.make(studentRow, "student_isblocked",
			// logic.isUserBlocked(u, riwClass) ? "Sim" : "Não");

			UIOutput.make(studentRow, "student_blocks",
					Integer.toString(logic.getUserBlocks(u, riwClass.getId())));

		}

		/**
		 * "riw_data:" "riw_module:" "riw_module_position" "riw_activities:"
		 * "activity_title" "activity_published" "publish_activity"
		 * "switch_publish"
		 */

		List<Module> modules = logic.getModules(course);
		UIBranchContainer riwData = UIBranchContainer.make(tofill, "riw_data:");
		for (Module m : modules) {
			UIBranchContainer riwModule = UIBranchContainer.make(riwData,
					"riw_module:");

			UIOutput.make(riwModule, "riw_module_position",
					Integer.toString(m.getPosition()));

			System.out.println("Modulo: " + m.getPosition() + ": "
					+ logic.isModulePublished(riwClass, m.getId()));

			List<Activity> activities = logic.getActivities(m);
			for (Activity a : activities) {

				UIBranchContainer riwActivity = UIBranchContainer.make(
						riwModule, "riw_activity:");

				UIOutput.make(riwActivity, "activity_title", a.getTitle());

				UIOutput.make(
						riwActivity,
						"activity_published",
						logic.isActivityPublished(riwClass, m.getId(),
								a.getId()) ? "Sim" : "Não");
			}
		}

	}

	@Override
	public ViewParameters getViewParameters() {
		return new ClassViewParameters(getViewID());
	}

	@Override
	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> l = new ArrayList<NavigationCase>();
		l.add(new NavigationCase(ManagerComponents.MODIFIED,
				new ClassViewParameters(ClassProducer.VIEW_ID, false, true)));
		return l;
	}
}