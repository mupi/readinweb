package br.unicamp.iel.tool.producers;

import java.util.ArrayList;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.tool.commons.ClassMenuComponent;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */

public class ClassesProducer implements ViewComponentProducer,
		ViewParamsReporter, DefaultView {

	private static Log logger = LogFactory.getLog(ClassesProducer.class);

	@Setter
	private ReadInWebClassManagementLogic logic;

	@Setter
	private UserDirectoryService userDirectoryService;

	@Setter
	private SiteService siteService;

	@Setter
	private ToolManager toolManager;

	@Setter
	private SessionManager sessionManager;

	public static final String VIEW_ID = "turmas";

	@Override
	public String getViewID() {
		return VIEW_ID;
	}

	@Override
	public void fillComponents(UIContainer tofill, ViewParameters viewparams,
			ComponentChecker checker) {

		ClassViewParameters cvp = (ClassViewParameters) viewparams;
		if (cvp.course == null) {
			return;
		}

		ClassMenuComponent menu = new ClassMenuComponent(cvp);
		menu.make(UIBranchContainer.make(tofill, "class_menu_replace:"));

		ArrayList<Site> riwClasses = new ArrayList<Site>(
				logic.getReadInWebClasses(cvp.course));

		UIBranchContainer riwClassesContainer = UIBranchContainer.make(tofill,
				"riw_classes:");

		ClassViewParameters riwClassParams = new ClassViewParameters();

		for (Site s : riwClasses) {
			riwClassParams.siteId = s.getId();
			riwClassParams.viewID = ClassProducer.VIEW_ID;
			riwClassParams.course = cvp.course;
			ArrayList<User> users = new ArrayList<User>(logic.getUsers(s
					.getId()));

			UIBranchContainer riwClass = UIBranchContainer.make(
					riwClassesContainer, "riw_class:");

			if (!logic.isReadInWebClassActive(s)) {
				riwClass.decorate(new UIStyleDecorator("danger"));
			}

			UIInternalLink.make(riwClass, "riw_class_title", s.getTitle(),
					riwClassParams); // FIXME
			UIInternalLink.make(riwClass, "riw_class_students",
					Integer.toString(users.size()), riwClassParams); // FIXME

			riwClassParams.viewID = VIEW_ID;
			UIInternalLink.make(riwClass, "riw_class_justifications",
					"Show justification count for " + s.getId(),
					// logic.countJustifications(s.getId()), //FIXME
					riwClassParams); // FIXME

			try {
				User teacher = logic.getTeacher((new ArrayList<String>(s
						.getUsersHasRole("Instructor"))).get(0));

				if (teacher != null) {
					UIInternalLink.make(riwClass, "riw_class_teacher",
							teacher.getDisplayName(), viewparams);
				}
			} catch (IndexOutOfBoundsException e) {
				UIInternalLink.make(riwClass, "riw_class_teacher", "-",
						viewparams);

			}
		}
	}

	@Override
	public ViewParameters getViewParameters() {
		return new ClassViewParameters(getViewID());
	}
}