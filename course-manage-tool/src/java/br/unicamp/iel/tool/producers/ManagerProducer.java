package br.unicamp.iel.tool.producers;

import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebAdminLogic;
import br.unicamp.iel.model.Course;

/**
  *
 * @author Virgilio Santos
 *
 */
public class ManagerProducer implements ViewComponentProducer, DefaultView {

    private static Log logger = LogFactory.getLog(ManagerProducer.class);

    @Setter
    private ReadInWebAdminLogic logic;

    public static final String VIEW_ID = "manager";

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

        }

    }
}