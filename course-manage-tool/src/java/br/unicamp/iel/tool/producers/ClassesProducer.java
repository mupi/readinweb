package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import br.unicamp.iel.tool.viewparameters.ClassesViewParameters;

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
        System.out.println(riwClasses.size());

        UIBranchContainer riw_classes = UIBranchContainer.make(tofill, "riw_classes:");
        for(Site s : riwClasses) {
            ArrayList<User> users =
                    new ArrayList<User>(logic.getUsers(s.getId()));
            UIBranchContainer riw_class =
                    UIBranchContainer.make(riw_classes, "riw_class:");

            UIInternalLink.make(riw_class, "riw_class_title", s.getTitle(),
                    viewparams); // FIXME
            UIInternalLink.make(riw_class, "riw_class_students",
                    Integer.toString(users.size()),
                    viewparams); // FIXME
            UIInternalLink.make(riw_class, "riw_class_justifications",
                    "Show justification count for " + s.getId(),
                    //logic.countJustifications(s.getId()), //FIXME
                    viewparams); // FIXME

            UIInternalLink.make(riw_class, "riw_class_teacher",
                    s.getCreatedBy().getDisplayName(),
                    viewparams); // FIXME
//            <tr rsf:id="riw_class:">
//              <td><a rsf:id="riw_class_title" href="#">LA122 A</a></td>
//              <td><a rsf:id="riw_class_students" href="#">31</a></td>
//              <td><a rsf:id="riw_class_justifications" href="#justificativas">4</a></td>
//              <td rsf:id="riw_class_teacher">Lucia</td>
//            </tr>
        }

    }

    @Override
    public ViewParameters getViewParameters() {
        return new ClassesViewParameters();
    }

    public ArrayList<Site> getReadInWebClasses(Long course){
        Map<String, String> m = new HashMap<String, String>();
        m.put(Property.COURSE.getName(), Long.toString(course));
        System.out.println(Property.COURSE.getName() + " : " + course);
        return new ArrayList<Site>(siteService.getSites(SelectionType.ANY,
                        null, "", m, SortType.CREATED_BY_ASC,
                        new PagingPosition()));
    }

    public String getUserId(){
        return sessionManager.getCurrentSession().getUserId();
    }

}