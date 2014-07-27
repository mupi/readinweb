package br.unicamp.iel.logic;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.sakaiproject.authz.api.Role;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.user.api.User;

/**
 * An interface to abstract all Sakai related API calls in a central method that can be injected into our app.
 *
 * @author Mike Jennings (mike_jennings@unc.edu)
 *
 */
public interface SakaiProxy {

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

    public void setCourseId(String siteId, Long id);

    public Site createSite(String siteId);

    public String getJsonStringProperty(String siteId, String property);

    public void setJsonStringProperty(String siteId, String property, String value);

    public void setJoinable(String siteId);

    public void archiveSite(String siteId);

    public List<Site> getReadInWebClasses(Long course);

    public List<User> getSiteUsers(String siteId);
}
