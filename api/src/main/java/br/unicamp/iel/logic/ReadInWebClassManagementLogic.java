
package br.unicamp.iel.logic;

import br.unicamp.iel.model.Activity;
import br.unicamp.iel.model.Course;
import br.unicamp.iel.model.JustificationMessage;
import br.unicamp.iel.model.Module;

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

    public List<Module> getModules(Course course);

    public boolean isModulePublished(Site riwClass, Long module);

    public List<Activity> getActivities(Module module);

    public boolean isActivityPublished(Site riwClass, Long module, Long activity);

    public Integer getUserBlocks(User user, String siteId);

    public boolean isUserBlocked(User user, Site riwClass);

    public User getTeacher(String string);

    public String getUserId();

    public void sendJustificationMessage(JustificationMessage message);

    public void deleteJustificationMessage(JustificationMessage message);

    public void unblockUser(String userId, String siteId);
}
