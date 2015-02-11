package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIELBinding;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UISelect;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.tool.commons.ClassMenuComponent;
import br.unicamp.iel.tool.commons.ManagerComponents;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */

public class CreateClassProducer implements ViewComponentProducer,
		NavigationCaseReporter, ViewParamsReporter {

	private static Log logger = LogFactory.getLog(CreateClassProducer.class);

	@Setter
	private ReadInWebClassManagementLogic logic;

	public static final String VIEW_ID = "criar_turma";

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
		// UIOutput.make(tofill, "riw_course_title", course.getTitle());
		/**
		 *
		 * title type: course descricao published joinable : true role : Student
		 * readinwebcourse : this readinwebcourse.data : generate
		 *
		 */

		// Render form
		UIForm form = UIForm.make(tofill, "add_riw_class");
		form.parameters.add(new UIELBinding("#{ManageClassBean.course}", course
				.getId()));
		form.addResultingViewBinding("course", course.getId());

		UIInput.make(form, "title", "#{ManageClassBean.title}");
		UIInput.make(form, "type", "#{ManageClassBean.type}");
		UIInput.make(form, "description", "#{ManageClassBean.description}");

		List<User> teachers = logic.getTeacherList();

		String[] teacherOptions = new String[teachers.size()];
		String[] teacherLabels = new String[teachers.size()];
		for (int i = 0; i < teachers.size(); i++) {
			teacherOptions[i] = teachers.get(i).getId();
			teacherLabels[i] = teachers.get(i).getDisplayName();
		}

		UISelect.make(form, "teacher", teacherOptions, teacherLabels,
				"#{ManageClassBean.teacherUserId}");

		UIInput.make(form, "weekly_activities",
				"#{ManageClassBean.weeklyActivities}").setValue(
				Integer.toString(2));
		UIInput.make(form, "published", "#{ManageClassBean.published}");
		UIInput.make(form, "start_date", "#{ManageClassBean.startDate}");
		UICommand.make(form, "create_class_button",
				"#{ManageClassBean.createReadInWebClass}");
	}

	@Override
	public ViewParameters getViewParameters() {
		return new ClassViewParameters(getViewID());
	}

	@Override
	public List<NavigationCase> reportNavigationCases() {
		List<NavigationCase> l = new ArrayList<NavigationCase>();
		l.add(new NavigationCase(ManagerComponents.CREATED,
				new ClassViewParameters(ClassesProducer.VIEW_ID, true, false)));
		return l;
	}
}