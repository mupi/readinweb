package br.unicamp.iel.logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.entity.api.EntityPropertyNotDefinedException;
import org.sakaiproject.entity.api.EntityPropertyTypeException;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.exception.IdInvalidException;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.IdUsedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SitePage;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.site.api.SiteService.SelectionType;
import org.sakaiproject.site.api.SiteService.SortType;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserNotDefinedException;

import br.unicamp.iel.model.Property;

/**
 * Implementation of {@link SakaiProxy}
 *
 * @author Steve Swinsburg (steve.swinsburg@anu.edu.au)
 *
 */
public class SakaiProxyImpl implements SakaiProxy {

    private static final Logger log = Logger.getLogger(SakaiProxyImpl.class);


    @Getter @Setter
    private UserDirectoryService userDirectoryService;

    @Getter @Setter
    private ToolManager toolManager;

    @Getter @Setter
    private SessionManager sessionManager;

    @Getter @Setter
    private SecurityService securityService;

    @Getter @Setter
    private EventTrackingService eventTrackingService;

    @Getter @Setter
    private ServerConfigurationService serverConfigurationService;

    @Getter @Setter
    private SiteService siteService;


    /**
     * init - perform any actions required here for when this bean starts up
     */
    public void init() {
        log.info(SakaiProxyImpl.class + " init");
    }


    /**
     * {@inheritDoc}
     */
    public String getCurrentSiteId(){
        return toolManager.getCurrentPlacement().getContext();
    }

    /**
     * {@inheritDoc}
     */
    public String getCurrentUserId() {
        return sessionManager.getCurrentSessionUserId();
    }

    /**
     * {@inheritDoc}
     */
    public String getCurrentUserDisplayName() {
        return userDirectoryService.getCurrentUser().getDisplayName();
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSuperUser() {
        return securityService.isSuperUser();
    }

    /**
     * {@inheritDoc}
     */
    public void postEvent(String event, String reference, boolean modify) {
        eventTrackingService.post(
                eventTrackingService.newEvent(event, reference, modify));
    }

    /**
     * {@inheritDoc}
     */
    public String getSkinRepoProperty(){
        return serverConfigurationService.getString("skin.repo");
    }

    /**
     * {@inheritDoc}
     */
    public String getToolSkinCSS(String skinRepo){

        String skin =
                siteService.findTool(sessionManager.getCurrentToolSession()
                        .getPlacementId()).getSkin();

        if(skin == null) {
            skin = serverConfigurationService.getString("skin.default");
        }

        return skinRepo + "/" + skin + "/tool.css";
    }

    @Override
    public User getCurrentUser() {
        return userDirectoryService.getCurrentUser();
    }

    @Override
    public Role getUserRole(String userId) {
        return null;
    }


    @Override
    public Placement getCurrentPlacement() {
        return toolManager.getCurrentPlacement();
    }

    @Override
    public Long getCourseId(){
        try {
            Site site = siteService.getSite(
                    toolManager.getCurrentPlacement().getContext());
            return site.getProperties().getLongProperty(Property.COURSE.getName());
        } catch (IdUnusedException e) {
            e.printStackTrace();
            return 0L;
        } catch (EntityPropertyNotDefinedException e) {
            e.printStackTrace();
            return 0L;
        } catch (EntityPropertyTypeException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public void setCourseId(String siteId, Long id) {
        try {
            Site site = siteService.getSite(siteId);
            site.getProperties().addProperty(Property.COURSE.getName(),
                    Long.toString(id));
        } catch (IdUnusedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site createSite(String siteId) {
        Site site;
        MessageDigest md;

        if (this.siteService.allowAddSite(siteId)) {
            try {
                md = MessageDigest.getInstance("MD5");
                siteId = md.digest(siteId.getBytes()).toString();
                while(siteService.siteExists(siteId)){
                    md.reset();
                    String s = new Date(System.currentTimeMillis()).toString();
                    siteId = md.digest((siteId + s).getBytes()).toString();
                }
                site = siteService.addSite(siteId, "course");

                // Calendar Tool
                SitePage sitePage = site.addPage();
                sitePage.addTool("sakai.schedule");
                sitePage.setTitle("Calendario");

                // Read In Web Tool
                sitePage = site.addPage();
                sitePage.addTool("sakai.readinweb");
                sitePage.setTitle("Curso Read in Web");

                // Announcements  Tool
                sitePage = site.addPage();
                sitePage.addTool("sakai.announcements");
                sitePage.setTitle("Avisos");

                // defines Joiner as 'Student'
                site.setJoinerRole("Student");

                // TODO Verify roles, realms and actions

                site.setJoinable(false);

                return site;
            } catch (IdInvalidException e) {
                e.printStackTrace();
            } catch (IdUsedException e) {
                 e.printStackTrace();
            } catch (PermissionException e) {
                 e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void setJoinable(String siteId){
        Site site;
        try {
            site = siteService.getSite(siteId);
            site.setJoinable(true);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void archiveSite(String siteId){
        Site site;
        try {
            site = siteService.getSite(siteId);
            site.setPublished(false);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getJsonStringProperty(String siteId, String property){
        String empty = "";
        try {
            Site site = siteService.getSite(siteId);
            return site.getProperties().getProperty(property);
        } catch (IdUnusedException e) {
            e.printStackTrace();
            return empty;
        }
    }

    @Override
    public void setJsonStringProperty(String siteId, String name, String value){
        try {
            Site site = siteService.getSite(siteId);
            site.getProperties().addProperty(name, value);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Site> getReadInWebClasses(Long course) {
        Map<String, String> m = new HashMap<String, String>();
        m.put(Property.COURSE.name(), Long.toString(course));
        return siteService.getSites(SelectionType.ANY, null, null, m,
                SortType.CREATED_BY_DESC,
                null);
    }

    @Override
    public List<User> getSiteUsers(String siteId) {
        Site site;
        List<User> users = new ArrayList<User>();
        try {
            site = siteService.getSite(siteId);
            List<String> usersIds = new ArrayList<String>(site.getUsers());
            for(String user : usersIds){
                users.add(userDirectoryService.getUser(user));
            }
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (UserNotDefinedException e) {
            e.printStackTrace();
        }

        return users;
    }
}
