package br.unicamp.iel.logic;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.entity.api.EntityPropertyNotDefinedException;
import org.sakaiproject.entity.api.EntityPropertyTypeException;
import org.sakaiproject.genericdao.api.search.Restriction;
import org.sakaiproject.genericdao.api.search.Search;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService.SelectionType;
import org.sakaiproject.site.api.SiteService.SortType;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.dao.ReadInWebDao;
import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.Module;
import br.unicamp.iel.model.Property;
import br.unicamp.iel.model.ReadInWebControl;
import br.unicamp.iel.model.reports.UserAccess;
import br.unicamp.iel.model.types.ControlTypes;

public class ReadInWebClassManagementLogicImpl implements
ReadInWebClassManagementLogic {

    private static final Logger log =
            Logger.getLogger(ReadInWebCourseLogic.class);

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
        sakaiProxy.setStringProperty(site, Property.COURSEFINISHED.getName(),
                Boolean.toString(false));
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
        return new ArrayList<Site>(sakaiProxy.getReadInWebSites(course));
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

    @Override
    public Site getReadInWebClass(String siteId) {
        return sakaiProxy.getSite(siteId);
    }

    @Override
    public List<User> getStudents(Site riwClass) {
        return sakaiProxy.getSiteStudents(riwClass);
    }

    @Override
    public List<Activity> getActivities(Module module) {
        return common.getActivities(module);
    }

    @Override
    public List<Module> getModules(Course course) {
        return common.getModules(course);
    }

    @Override
    public boolean isModulePublished(Site riwClass, Long module) {
        return common.isModulePublished(riwClass, module);
    }

    @Override
    public boolean isActivityPublished(Site riwClass, Long module,
            Long activity) {
        return common.isActivityPublished(riwClass, module, activity);
    }

    @Override
    public Integer getUserBlocks(User user, String siteId) {
        return common.getUserBlocks(common.getUserControl(user.getId(),
                siteId));
    }

    @Override
    public boolean isUserBlocked(User user, Site riwClass) {
        return common.isUserBLocked(common.getUserControl(user.getId(),
                riwClass.getId()));
    }

    @Override
    public User getTeacher(String teacherId) {
        return common.getTeacher(teacherId);
    }

   @Override
    public String getUserId(){
        return sakaiProxy.getCurrentUserId();
    }

    @Override
    public Long countPublishedActivities(Site riwClass) {
        return common.countPublishedActivities(riwClass);
    }

    @Override
    public Long countStudents(Site site) {
        return common.countUsers(site);
    }

    @Override
    public Date getStartDate(Site site) {
        return common.getStartDate(site);
    }

    @Override
    public Boolean getReadInWebClassState(Site site) {
        return common.getReadInWebClassState(site);
    }

    @Override
    public void setClassState(Site site, Boolean classState) {
        common.setClassState(site, classState);
    }

    @Override
    public Site getLastAddedReadInWebClass(Long course) {
        return sakaiProxy.getLastAddedSiteByProperty(course);
    }

    @Override
    public Site getLastModifiedReadInWebClass(Long course) {
        return sakaiProxy.getLastModifiedSiteByProperty(course);
    }

    @Override
    public boolean isReadInWebClassActive(Site s) {
        try {
            return s.getProperties().getBooleanProperty(
                    Property.COURSESTATE.getName());
        } catch (EntityPropertyNotDefinedException e) {
            e.printStackTrace();
        } catch (EntityPropertyTypeException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Long countActivities(User u, Site riwClass) {
    	Long[] ids = common.getAllPublishedActivities(riwClass.getId());
        
        Search s = new Search(
                new Restriction[] {
                        new Restriction("user", u.getId()),
                        new Restriction("activity.id", ids),
                        new Restriction("control", ControlTypes.getSum())
                });
        Long done = new Long(dao.countBySearch(ReadInWebControl.class, s));

        return done;
    }
    
    @Override
    public List<UserAccess> getAccessData(User student, Site riwClass) {
    	return dao.getUserAccessesReport(student.getId());
    }
}

