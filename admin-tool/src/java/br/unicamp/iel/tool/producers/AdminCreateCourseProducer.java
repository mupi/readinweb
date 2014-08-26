package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UICommand;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIForm;
import uk.org.ponder.rsf.components.UIInput;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCase;
import uk.org.ponder.rsf.flow.jsfnav.NavigationCaseReporter;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.tool.commons.CourseComponents;

/**
 * @author Virgilio N Santos
 *
 */
public class AdminCreateCourseProducer implements ViewComponentProducer,
    NavigationCaseReporter {

    private static Log logger = LogFactory.getLog(AdminCreateCourseProducer.class);
    public static final String VIEW_ID = "create_course";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(AdminCreateCourseProducer.VIEW_ID));


        UIForm ui_form = UIForm.make(tofill, "create_course_form");
        UIInput.make(ui_form, "course_title", "#{AdminBean.title}");
        UIInput.make(ui_form, "course_language", "#{AdminBean.language}");
        UIInput.make(ui_form, "course_description", "#{AdminBean.description}");
        UICommand.make(ui_form, "create_course", "#{AdminBean.createCourse}");
    }

    @Override
    public List<NavigationCase> reportNavigationCases() {
        List<NavigationCase> l = new ArrayList<NavigationCase>();
        l.add(new NavigationCase(CourseComponents.CREATED,
                new SimpleViewParameters(AdminProducer.VIEW_ID)));
        //FIXME put some view parameters here =)
        l.add(new NavigationCase(CourseComponents.CREATE_FAIL,
                new SimpleViewParameters(AdminProducer.VIEW_ID)));
        return l;
    }

}