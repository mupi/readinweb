package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.components.UIOutput;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.tool.commons.ManagerComponents;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;

/**
 *
 * @author Virgilio Santos
 *
 */

public class ClassProducer implements ViewComponentProducer, ViewParamsReporter {

    private static Log logger = LogFactory.getLog(ClassProducer.class);

    @Setter
    private ReadInWebClassManagementLogic logic;

    @Setter
    private UserDirectoryService userDirectoryService;

    @Setter
    private SiteService siteService;

    public static final String VIEW_ID = "turma";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        Course course = logic.getCourse(logic.getManagerCourseId());
        ClassViewParameters classViewParameters = (ClassViewParameters)viewparams;

        ManagerComponents.loadMenu(viewparams, tofill);

        System.out.println(course.getTitle());

        Site riwClass = logic.getReadInWebClass(classViewParameters.siteId);

        ArrayList<User> riwStudents =
                new ArrayList<User>(logic.getStudents(riwClass));
        UIBranchContainer studentsTable =
                UIBranchContainer.make(tofill, "riw_students:");

        // TODO if no students

        for(User u : riwStudents){
            UIBranchContainer studentRow =
                    UIBranchContainer.make(studentsTable, "riw_student:");

            UIOutput.make(studentRow, "student_name", u.getDisplayName());

            // TODO add class danger on blocked user
            UIOutput.make(studentRow, "student_isblocked",
                    logic.isUserBlocked(u, riwClass) ? "Sim" : "Não");

            UIOutput.make(studentRow, "student_blocks",
                    Integer.toString(logic.getUserBlocks(u, riwClass.getId())));

        }

        /**
            "riw_data:"
                "riw_module:"
                    "riw_module_position"
                    "riw_activities:"
                        "activity_title"
                        "activity_published"
                        "publish_activity"
                            "switch_publish"
         */

        List<Module> modules = logic.getModules(course);
        UIBranchContainer riwData = UIBranchContainer.make(tofill, "riw_data:");
        for(Module m : modules){
            UIBranchContainer riwModule =
                    UIBranchContainer.make(riwData, "riw_module:");

            UIOutput.make(riwModule, "riw_module_position",
                    Integer.toString(m.getPosition()));


            System.out.println("Modulo: "
                    + m.getPosition()
                    + ": "
                    + logic.isModulePublished(riwClass, m.getId()));

            List<Activity> activities = logic.getActivities(m);
            for(Activity a : activities){

                UIBranchContainer riwActivity =
                        UIBranchContainer.make(riwModule, "riw_activity:");

                UIOutput.make(riwActivity, "activity_title", a.getTitle());

                UIOutput.make(riwActivity, "activity_published",
                        logic.isActivityPublished(riwClass, m.getId(),
                                a.getId()) ? "Sim" : "Não");

                // TODO Criar formulário, com ajax que libera
                //          ou não uma atividade
                System.out.println(a.getTitle()
                        + ": "
                        + logic.isActivityPublished(riwClass, m.getId(),
                                a.getId()));
            }
        }

    }

    @Override
    public ViewParameters getViewParameters() {
        return new ClassViewParameters(getViewID());
    }
}