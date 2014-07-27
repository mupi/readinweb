package br.unicamp.iel.logic;

import java.util.List;

import lombok.Setter;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Property;

public class ReadInWebClassManagementLogicImpl implements
        ReadInWebClassManagementLogic {

    @Setter
    ReadInWebCommonLogic logic;

    @Setter
    SakaiProxy sakaiProxy;

    @Override
    public boolean createClass(Course course, String siteId) {
        Site site = sakaiProxy.createSite(siteId);
        siteId = site.getId();
        sakaiProxy.setCourseId(siteId, course.getId());
        sakaiProxy.setJsonStringProperty(siteId, Property.COURSEDATA.getName(),
                logic.getDefaultCoursePropertyString(course));

        return true;
    }

    @Override
    public boolean closeClass(String siteId) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<User> getUsers(String siteId) {
        return sakaiProxy.getSiteUsers(siteId);
    }

    @Override
    public List<Site> getReadInWebClasses(Long course) {
        return sakaiProxy.getReadInWebClasses(course);
    }

}
