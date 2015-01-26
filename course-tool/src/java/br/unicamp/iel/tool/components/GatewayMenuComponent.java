package br.unicamp.iel.tool.components;

import lombok.AllArgsConstructor;
import lombok.Setter;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIJointContainer;
import uk.org.ponder.rsf.components.decorators.UIStyleDecorator;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.tool.producers.ClassProducer;
import br.unicamp.iel.tool.producers.JustificationStudentProducer;
import br.unicamp.iel.tool.producers.JustificationsProducer;
import br.unicamp.iel.tool.producers.SummaryProducer;

@AllArgsConstructor
public class GatewayMenuComponent {
	public static final String COMPONENT_ID = "gateway_menu:"; 
	
	@Setter
	private ViewParameters viewparams;
	@Setter
	private Boolean isTeacher;
	
	public UIJointContainer make(UIContainer menu) {
		UIContainer parent = menu.parent;
		menu.parent.remove(menu);
		
		System.out.println(menu.ID);
		UIJointContainer joint = 
			new UIJointContainer(parent, menu.ID, COMPONENT_ID);
		
		loadMenu(parent);	
		joint.addComponent(menu);
		
		return joint;
	}
	
	private void loadMenu(UIContainer tofill){
		decorateActive(UIInternalLink.make(tofill, "link_home", 
				new SimpleViewParameters(SummaryProducer.VIEW_ID)));
		
		UIInternalLink link = UIInternalLink.make(tofill, "link_justification",
                new SimpleViewParameters(JustificationStudentProducer.VIEW_ID));

		if(isTeacher){
			decorateActive(UIInternalLink.make(tofill, "link_class",
                    new SimpleViewParameters(ClassProducer.VIEW_ID)));
			link.viewparams = 
					new SimpleViewParameters(JustificationsProducer.VIEW_ID);
        }
		
		decorateActive(link);
    }

	private UIInternalLink decorateActive(UIInternalLink link){
		if(viewparams.viewID.equals(link.viewparams.viewID)){
			link.decorate(new UIStyleDecorator("active"));
		}
		return link;
	}	
}
