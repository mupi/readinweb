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

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Activity;
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

        Long course = logic.getManagerCourseId();
        ClassViewParameters classViewParameters = (ClassViewParameters)viewparams;

        ManagerComponents.loadMenu(viewparams, tofill);

        Site riwClass = logic.getReadInWebClass(classViewParameters.siteId);

        ArrayList<User> riwStudents =
                new ArrayList<User>(logic.getStudents(riwClass));

        List<Module> modules = logic.getModules(course);
        for(Module m : modules){

            System.out.println("Modulo: "
                    + m.getPosition()
                    + ": "
                    + logic.isModulePublished(riwClass, m.getId()));
            List<Activity> activities = logic.getActivities(m.getId());
            for(Activity a : activities){
                System.out.println(a.getTitle() + ": " + logic.isPublished(riwClass, m.getId(), a.getId()));
            }
        }

    }

    @Override
    public ViewParameters getViewParameters() {
        return new ClassViewParameters(getViewID());
    }
}