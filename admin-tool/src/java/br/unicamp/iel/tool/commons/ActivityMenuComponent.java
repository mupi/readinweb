package br.unicamp.iel.tool.commons;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import uk.org.ponder.rsf.components.UIAnchor;
import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.producers.BasicProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Exercise;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.producers.AdminEstrategiasProducer;
import br.unicamp.iel.tool.producers.AdminExerciciosProducer;
import br.unicamp.iel.tool.producers.AdminFunctionalWordsProducer;
import br.unicamp.iel.tool.producers.AdminGramaticaProducer;
import br.unicamp.iel.tool.producers.AdminProducer;
import br.unicamp.iel.tool.producers.AdminTextProducer;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;

@AllArgsConstructor
public class ActivityMenuComponent implements BasicProducer {
	ReadInWebAdminLogic logic;

	public static final String COMPONENT_ID = "activity_menu:";
	private ViewParameters viewparams;

	private Activity activity;
	private Course course;
	private Exercise exercise;
	private String viewID;

	@Override
	public void fillComponents(UIContainer tofill, String clientID) {
		UIJointContainer joint = new UIJointContainer(tofill, clientID,
				"activity_menu:", "" + 1);

		loadMenu(joint);

		List<Module> modules = new ArrayList<Module>(logic.getModules(course));
		for (Module m : modules) {
			List<Activity> activities = new ArrayList<Activity>(
					logic.getActivities(m));

			// UIBranchContainer ui_modules = UIBranchContainer.make(joint,
			// "modules_menu_item:");
			UIJointContainer ui_modules = new UIJointContainer(joint,
					"modules_menu_item_replace:", "modules_menu_item:");
			UIOutput.make(ui_modules, "lnk_modulo",
					("Módulo " + m.getPosition()));

			UIBranchContainer ui_activities = UIBranchContainer.make(
					ui_modules, "div_atividades:");
			for (Activity a : activities) {
				UIBranchContainer row = UIBranchContainer.make(ui_activities,
						"p_rowsAct:");
				CourseViewParameters cvp = new CourseViewParameters(
						course.getId(), m.getId(), a.getId());
				cvp.viewID = viewID;
				UIInternalLink.make(row, "input_link_a_",
						a.getPosition() + " - " + a.getTitle(), cvp)
						.updateFullID("input_link_a_" + a.getId());
			}
		}
	}

	private void loadMenu(UIContainer tofill) {
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
		decorateActive(UIInternalLink.make(tofill, "linktext", parameters));

		parameters.viewID = AdminExerciciosProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "linkexercise", parameters));

		parameters.viewID = AdminEstrategiasProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "linkstrategy", parameters));

		parameters.viewID = AdminGramaticaProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "linkgrammar", parameters));

		if (viewID.equalsIgnoreCase(AdminTextProducer.VIEW_ID)) {
			UIAnchor.make(tofill, "link_questions");
			UIAnchor.make(tofill, "link_dictionary");
		}

		parameters.viewID = AdminFunctionalWordsProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "link_functional",
				parameters));

		parameters.viewID = AdminProducer.VIEW_ID;
		UIInternalLink.make(tofill, "link_courses", parameters);

	}

	private UIInternalLink decorateActive(UIInternalLink link) {
		if (viewID.equals(link.viewparams.viewID)) {
			link.parent.decorate(new UIStyleDecorator("active"));
		}
		return link;
	}
}
