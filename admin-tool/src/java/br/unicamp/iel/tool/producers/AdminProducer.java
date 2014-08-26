package br.unicamp.iel.tool.producers;

import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.ReadInWebCourseData;
import br.unicamp.iel.tool.viewparameters.CourseViewParameters;
import br.unicamp.iel.tool.viewparameters.SendContentViewParameters;

/**
  *
 * @author Virgilio Santos
 *
 */
public class AdminProducer implements ViewComponentProducer, DefaultView {

    private static Log logger = LogFactory.getLog(AdminProducer.class);

    @Setter
    private ReadInWebAdminLogic logic;

    public static final String VIEW_ID = "admin";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        List<Course> courses = logic.getCourses();
        System.out.println("Numero de cursos" + courses.size());

        UIBranchContainer uiCourses = UIBranchContainer.make(tofill, "courses:");
        for(Course c : courses){
            UIBranchContainer uiCourse =
                    UIBranchContainer.make(uiCourses, "course:");
            UIOutput.make(uiCourse, "course_title",
                            c.getTitle() + " (" + c.getId() + ")");
            UIOutput.make(uiCourse, "course_idiom", c.getIdiom());
            UIOutput.make(uiCourse, "course_description", c.getDescription());

            ReadInWebCourseData riwData = logic.getReadInWebData(c);

            UIOutput.make(uiCourse, "course_data_classes",
                    riwData.getCountClasses().toString());
            UIOutput.make(uiCourse, "course_data_classes_finished",
                    riwData.getCountClassesFinished().toString());
            UIOutput.make(uiCourse, "course_data_attendance",
                    riwData.getCountUsers().toString());
            UIOutput.make(uiCourse, "course_data_graduates",
                    riwData.getCountGraduates().toString());


            UIInternalLink.make(uiCourse, "course_send_content",
                    new SendContentViewParameters(false, null));

            CourseViewParameters cvp =
                    new CourseViewParameters(AdminTextProducer.VIEW_ID);
            cvp.course = c.getId();
            UIInternalLink.make(uiCourse, "course_edit_content", cvp);
        }

        UIInternalLink.make(tofill, "link_home",
                new SimpleViewParameters(AdminCreateCourseProducer.VIEW_ID));

    }
}