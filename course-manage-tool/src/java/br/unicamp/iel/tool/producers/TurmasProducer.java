package br.unicamp.iel.tool.producers;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import uk.org.ponder.rsf.components.UIContainer;
import uk.org.ponder.rsf.view.ComponentChecker;
import uk.org.ponder.rsf.view.DefaultView;
import uk.org.ponder.rsf.view.ViewComponentProducer;
import uk.org.ponder.rsf.viewstate.ViewParameters;
import br.unicamp.iel.logic.ReadInWebClassManagementLogic;

/**
  *
 * @author Virgilio Santos
 *
 */

public class TurmasProducer implements ViewComponentProducer, DefaultView {

    private static Log logger = LogFactory.getLog(TurmasProducer.class);

    @Setter
    private ReadInWebClassManagementLogic logic;

    public static final String VIEW_ID = "turmas";

    @Override
    public String getViewID() {
        return VIEW_ID;
    } // end getViewID()

    @Override
    public void fillComponents(UIContainer tofill, ViewParameters viewparams,
            ComponentChecker checker) {

        Long course = logic.getManagerCourseId();

        ArrayList<Site> riwClasses =
                new ArrayList<Site>(logic.getReadInWebClasses(course));
        for(Site s : riwClasses){
            System.out.println(s.getTitle());
            ArrayList<User> users =
                    new ArrayList<User>(logic.getUsers(s.getId()));

            for(User u : users){
                System.out.println(u.getFirstName());
            }
        }

    }
}