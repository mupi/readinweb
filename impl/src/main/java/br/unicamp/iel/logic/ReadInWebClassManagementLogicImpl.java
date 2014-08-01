package br.unicamp.iel.logic;

import java.util.List;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Property;

public class ReadInWebClassManagementLogicImpl implements
ReadInWebClassManagementLogic {

    private static final Logger log = Logger.getLogger(ReadInWebCourseLogic.class);

    @Setter
    private ReadInWebDao dao;

    @Setter
    SakaiProxy sakaiProxy;

    @Setter
    ReadInWebCommonLogic common;

    public void init() {
        log.info(ReadInWebClassManagementLogic.class + " init");
    }

    @Override
    public Site createClass(Course course, String siteId) {
        Site site = sakaiProxy.createSite(siteId);
        siteId = site.getId();
        sakaiProxy.setCourseId(site, course.getId());
        sakaiProxy.setStringProperty(site, Property.COURSEDATA.getName(),
                common.getDefaultCoursePropertyString(course));
        return site;
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

    @Override
    public Long getManagerCourseId() {
        return sakaiProxy.getManagerCourseId();
    }

    @Override
    public Course getCourse(Long managerCourseId) {
        return common.getCourse(managerCourseId);
    }

    @Override
    public List<User> getTeacherList() {
        return sakaiProxy.getTeachers();
    }

    @Override
    public void addProperty(Site site, String name, String value) {
        sakaiProxy.setStringProperty(site, name, value);
    }

    @Override
    public void saveSite(Site site) {
        sakaiProxy.saveSite(site);
    }
}
