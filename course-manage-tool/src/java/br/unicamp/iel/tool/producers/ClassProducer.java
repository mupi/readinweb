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
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

import uk.org.ponder.rsf.components.UIBranchContainer;
import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.components.UIInternalLink;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;
import br.unicamp.iel.model.Property;

/**
  *
 * @author Virgilio Santos
 *
 */

public class ClassProducer implements ViewComponentProducer {

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

        ArrayList<Site> riwClasses = getReadInWebClasses(course);

    }

    public ArrayList<Site> getReadInWebClasses(Long course){
        Map<String, String> m = new HashMap<String, String>();
        m.put(Property.COURSE.getName(), Long.toString(course));


        return new ArrayList<Site>(siteService.getSites(SelectionType.ANY,
                        null, "", m, SortType.CREATED_BY_ASC,
                        new PagingPosition()));
    }

}