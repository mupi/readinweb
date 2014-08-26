package br.unicamp.iel.logic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.sakaiproject.authz.api.Role;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.user.api.User;

import br.unicamp.iel.model.Course;

/**
 * An interface to abstract all Sakai related API calls in a central method that can be injected into our app.
 *
 * @author Mike Jennings (mike_jennings@unc.edu)
 *
 */
public interface SakaiProxy {

    public final static String READINWEB_ACCESS = "readinweb.access";
    public final static String READINWEB_MANAGE = "readinweb.manage";
    public final static String READINWEB_MANAGE_TOOL = "sakai.readinwebmanage";
    public final static String READINWEB_ADMIN_TOOL = "sakai.readinwebadmin";

    /**
     * Get current siteid
     * @return
     */
    public String getCurrentSiteId();

    /**
     * Get current user id
     * @return
     */
    public String getCurrentUserId();

    /**
     * Get the current user object, not just the ID
     *
     * @return
     */
    public User getCurrentUser();

    public Role getUserRole(String userId);

    /**
     * Get current user display name
     * @return
     */
    public String getCurrentUserDisplayName();

    /**
     * Is the current user a superUser? (anyone in admin realm)
     * @return
     */
    public boolean isSuperUser();


    /**
     * Post an event to Sakai
     *
     * @param event			name of event
     * @param reference		reference
     * @param modify		true if something changed, false if just access
     *
     */
    public void postEvent(String event, String reference, boolean modify);

    /**
     * Wrapper for ServerConfigurationService.getString("skin.repo")
     * @return
     */
    public String getSkinRepoProperty();

    /**
     * Gets the tool skin CSS first by checking the tool, otherwise by using the default property.
     * @param	the location of the skin repo
     * @return
     */
    public String getToolSkinCSS(String skinRepo);

    public Placement getCurrentPlacement();

    public Long getCourseId();

    public Long getManagerCourseId();

    public void setCourseId(Site site, Long id);

    public Site createSite(String siteId);

    public String getJsonUserStringProperty(String userId, String property);

    public void setJsonUserStringProperty(String userId, String name, String value);

    public String getStringProperty(Site site, String property);

    public void setStringProperty(Site site, String property, String value);

    public void setJoinable(Site site, Boolean joinable);

    public void archiveSite(String siteId);

    public List<User> getSiteUsers(String siteId);

    public List<User> getSiteStudents(Site site);

    public User getUser();

    public User getUser(String teacherId);

    public Integer countUsers(String siteId);

    public List<User> getTeachers();

    public void saveSite(Site site);

    public Site getSite(String siteId);

    public Site getCurrentSite();

    public List<Site> getReadInWebSites(Long course);

    public List<Site> getReadInWebArchivedSites(Long course);

    public void createReadInWebAdminPage();

    public void createManagerPage(Course course);

    public SitePage findCurrentPage();

 }
