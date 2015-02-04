package br.unicamp.iel.tool.commons;

import lombok.AllArgsConstructor;
import lombok.Setter;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import br.unicamp.iel.tool.producers.AdminProducer;
import br.unicamp.iel.tool.producers.ClassesProducer;
import br.unicamp.iel.tool.producers.CreateClassProducer;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;

@AllArgsConstructor
public class ClassMenuComponent {
	public static final String COMPONENT_ID = "class_menu:";

	@Setter
	private ClassViewParameters viewparams;

	public UIJointContainer make(UIContainer menu) {
		UIContainer parent = menu.parent;
		menu.parent.remove(menu);

		UIJointContainer joint = new UIJointContainer(parent, menu.ID,
				COMPONENT_ID);

		loadMenu(parent);
		joint.addComponent(menu);

		return joint;
	}

	private void loadMenu(UIContainer tofill) {

		ClassViewParameters cvp = new ClassViewParameters();

		cvp.course = viewparams.course;
		cvp.viewID = ClassesProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "link_class_list", cvp));

		cvp.viewID = CreateClassProducer.VIEW_ID;
		decorateActive(UIInternalLink.make(tofill, "link_create_class", cvp));

		UIInternalLink.make(tofill, "link_courses", new SimpleViewParameters(
				AdminProducer.VIEW_ID));

	}

	private UIInternalLink decorateActive(UIInternalLink link) {
		if (viewparams.viewID.equals(link.viewparams.viewID)) {
			link.decorate(new UIStyleDecorator("active"));
		}
		return link;
	}
}
