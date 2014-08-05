
package br.unicamp.iel.logic;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.Module;

import java.util.ArrayList;
import java.util.List;

import org.sakaiproject.site.api.Site;
import org.sakaiproject.user.api.User;

/**
 *
 * @author Virgilio Santos
 */

public interface ReadInWebClassManagementLogic {

    public Site createClass(Course course, String siteId);

    public boolean closeClass(String siteId);

    public List<User> getUsers(String siteId);

    public List<Site> getReadInWebClasses(Long course);

    public Long getManagerCourseId();

    public Course getCourse(Long managerCourseId);

    public List<User> getTeacherList();

    public void addProperty(Site site, String name, String value);

    public void saveSite(Site site);

    public Site getReadInWebClass(String siteId);

    public List<User> getStudents(Site riwClass);

    public List<Module> getModules(Long course);

    public boolean isModulePublished(Site riwClass, Long module);

    public List<Activity> getActivities(Long module);

    public boolean isPublished(Site riwClass, Long module, Long activity);

}
