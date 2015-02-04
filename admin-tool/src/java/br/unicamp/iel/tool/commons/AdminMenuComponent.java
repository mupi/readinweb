package br.unicamp.iel.tool.commons;

import lombok.AllArgsConstructor;
import lombok.Setter;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.tool.producers.AdminCreateCourseProducer;
import br.unicamp.iel.tool.producers.AdminProducer;

@AllArgsConstructor
public class AdminMenuComponent {
	public static final String COMPONENT_ID = "admin_menu:";

	@Setter
	private ViewParameters viewparams;

	public UIJointContainer make(UIContainer menu) {
		UIContainer parent = menu.parent;
		menu.parent.remove(menu);

		System.out.println(menu.ID);
		UIJointContainer joint = new UIJointContainer(parent, menu.ID,
				COMPONENT_ID);

		loadMenu(parent);
		joint.addComponent(menu);

		return joint;
	}

	private void loadMenu(UIContainer tofill) {

		decorateActive(UIInternalLink.make(tofill, "course_list",
				new SimpleViewParameters(AdminProducer.VIEW_ID)));

		decorateActive(UIInternalLink.make(tofill, "course_create",
				new SimpleViewParameters(AdminCreateCourseProducer.VIEW_ID)));
	}

	private UIInternalLink decorateActive(UIInternalLink link) {
		if (viewparams.viewID.equals(link.viewparams.viewID)) {
			link.decorate(new UIStyleDecorator("active"));
		}
		return link;
	}
}
