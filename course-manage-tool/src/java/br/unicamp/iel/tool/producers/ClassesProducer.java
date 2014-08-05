package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.javax.PagingPosition;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.site.api.SiteService.SelectionType;
import org.sakaiproject.site.api.SiteService.SortType;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.SimpleViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import uk.org.ponder.rsf.viewstate.ViewParamsReporter;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Property;
import br.unicamp.iel.tool.commons.ManagerComponents;
import br.unicamp.iel.tool.viewparameters.ClassViewParameters;
import br.unicamp.iel.tool.viewparameters.ClassesViewParameters;
import br.unicamp.iel.tool.viewparameters.JustificationViewParameters;

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
    private SessionManager sessionManager;

    public static final String VIEW_ID = "turmas";

    @Override
    public String getViewID() {
        return VIEW_ID;
    }

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {
        Long course = logic.getManagerCourseId();
        ClassesViewParameters classesViewParameters =
                (ClassesViewParameters) viewparams;

        ManagerComponents.loadMenu(viewparams, tofill);
        SimpleViewParameters createClass = new SimpleViewParameters();
        createClass.viewID = CreateClassProducer.VIEW_ID;
        UIInternalLink.make(tofill, "link_create_class", createClass);

        classesViewParameters.userId = getUserId(); // FIXME
        UIInternalLink.make(tofill, "link_only_mine", classesViewParameters);


        ArrayList<Site> riwClasses = getReadInWebClasses(course);

        UIBranchContainer riw_classes = UIBranchContainer.make(tofill,
                "riw_classes:");

        ClassViewParameters riwClassParams =
                new ClassViewParameters(ClassProducer.VIEW_ID);

        for(Site s : riwClasses) {
            riwClassParams.siteId = s.getId();
            ArrayList<User> users =
                    new ArrayList<User>(logic.getUsers(s.getId()));

            UIBranchContainer riw_class =
                    UIBranchContainer.make(riw_classes, "riw_class:");

            UIInternalLink.make(riw_class, "riw_class_title", s.getTitle(),
                    riwClassParams); // FIXME
            UIInternalLink.make(riw_class, "riw_class_students",
                    Integer.toString(users.size()),
                    riwClassParams); // FIXME

            riwClassParams.viewID = JustificationsProducer.VIEW_ID;
            UIInternalLink.make(riw_class, "riw_class_justifications",
                    "Show justification count for " + s.getId(),
                    // logic.countJustifications(s.getId()), //FIXME
                    riwClassParams); // FIXME

            try {
                User teacher = getTeacher((new ArrayList<String>(
                        s.getUsersHasRole("Instructor"))).get(0));

                if(teacher != null){
                    UIInternalLink.make(riw_class, "riw_class_teacher",
                            teacher.getDisplayName(), viewparams);
                }
            } catch (IndexOutOfBoundsException e){
                UIInternalLink.make(riw_class, "riw_class_teacher",
                        "-", viewparams);

            }
        }
    }

    @Override
    public ViewParameters getViewParameters() {
        return new ClassesViewParameters();
    }

    public ArrayList<Site> getReadInWebClasses(Long course){ // ManagementLogic
        Map<String, String> m = new HashMap<String, String>();
        m.put(Property.COURSE.getName(), Long.toString(course));

        return new ArrayList<Site>(siteService.getSites(SelectionType.ANY,
                null, null, m, SortType.CREATED_BY_ASC,
                null));
    }

    public User getTeacher(String teacherId){ // CommonLogic
        try {
            return userDirectoryService.getUser(teacherId);
        } catch (UserNotDefinedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserId(){
        return sessionManager.getCurrentSession().getUserId();
    }

}