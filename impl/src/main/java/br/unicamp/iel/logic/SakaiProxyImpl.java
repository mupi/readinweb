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
import org.sakaiproject.authz.api.AuthzGroup;
import org.sakaiproject.authz.api.AuthzGroupService;
import org.sakaiproject.authz.api.AuthzPermissionException;
import org.sakaiproject.authz.api.FunctionManager;
import org.sakaiproject.authz.api.GroupNotDefinedException;
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
import org.sakaiproject.user.api.UserAlreadyDefinedException;
import org.sakaiproject.user.api.UserDirectoryService;
import org.sakaiproject.user.api.UserEdit;
import org.sakaiproject.user.api.UserLockedException;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.sakaiproject.user.api.UserPermissionException;

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

    @Getter @Setter
    private FunctionManager functionManager;

    @Getter @Setter
    private AuthzGroupService authzGroupService;


    /**
     * init - perform any actions required here for when this bean starts up
     */
    public void init() {
        log.info(SakaiProxyImpl.class + " init");
        functionManager.registerFunction(READINWEB_ACCESS);
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
            return site.getProperties()
                    .getLongProperty(Property.COURSE.getName());
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
    public Long getManagerCourseId() {
        try {
            Site site = siteService.getSite(
                    toolManager.getCurrentPlacement().getContext());
            return site.getProperties()
                    .getLongProperty(Property.COURSEMANAGED.getName());
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
    public void setCourseId(Site site, Long id) {
        try {
            site.getProperties().addProperty(Property.COURSE.getName(),
                    Long.toString(id));
            siteService.save(site);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site createSite(String siteId) {
        Site site = null;
        MessageDigest md;

        byte[] digest;
        StringBuffer sb;

        try {
            md = MessageDigest.getInstance("MD5");
            md.update(siteId.getBytes());
            digest = md.digest();
            sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            siteId = sb.toString();
            while(siteService.siteExists(siteId)){
                md.reset();
                String s = Long.toString(System.currentTimeMillis());
                md.update((s + siteId).getBytes());
                digest = md.digest();
                sb = new StringBuffer();
                for (byte b : digest) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                siteId = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (this.siteService.allowAddSite(siteId)) {
            try {
                site = siteService.addSite(siteId, "course");
                // Calendar Tool
                SitePage sitePage = site.addPage();
                sitePage.addTool("sakai.schedule");
                sitePage.setTitle("Calendário");

                // Read In Web Tool
                sitePage = site.addPage();
                sitePage.addTool("sakai.readinwebcourse");
                sitePage.setTitle("Curso Read in Web");

                // Announcements  Tool
                sitePage = site.addPage();
                sitePage.addTool("sakai.announcements");
                sitePage.setTitle("Avisos");

                site.setMaintainRole("Instructor");
                site.setJoinerRole("Student");
                siteService.save(site);


                AuthzGroup ag = authzGroupService.getAuthzGroup(
                        site.getReference());

                if(authzGroupService.allowUpdate(ag.getId())){
                    Role r = ag.getRole("Student");
                    r.allowFunction(READINWEB_ACCESS);
                    authzGroupService.save(ag);
                }

            } catch (IdInvalidException e) {
                e.printStackTrace();
            } catch (IdUsedException e) {
                e.printStackTrace();
            } catch (PermissionException e) {
                e.printStackTrace();
            } catch (IdUnusedException e) {
                e.printStackTrace();
            } catch (GroupNotDefinedException e) {
                e.printStackTrace();
            } catch (AuthzPermissionException e) {
                e.printStackTrace();
            }
        }
        return site;
    }

    @Override
    public void setJoinable(Site site, Boolean joinable){
        try {
            site.setJoinable(joinable);
            siteService.save(site);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void archiveSite(String siteId){
        Site site;
        try {
            site = siteService.getSite(siteId);
            site.setPublished(false);
            siteService.save(site);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getJsonUserStringProperty(String userId, String property){
        try {
            User user = userDirectoryService.getUser(userId);
            return user.getProperties().getProperty(property);
        } catch (UserNotDefinedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setJsonUserStringProperty(String userId, String name,
            String value){
        try {
            UserEdit ue = userDirectoryService.editUser(userId);
            ue.getProperties().addProperty(name, value);
            userDirectoryService.commitEdit(ue);
        } catch (UserNotDefinedException e) {
            e.printStackTrace();
        } catch (UserPermissionException e) {
            e.printStackTrace();
        } catch (UserLockedException e) {
            e.printStackTrace();
        } catch (UserAlreadyDefinedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getStringProperty(String siteId, String property){
        try {
            Site site = siteService.getSite(siteId);
            return site.getProperties().getProperty(property);
        } catch (IdUnusedException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setStringProperty(Site site, String name, String value){
        try {
            site.getProperties().addProperty(name, value);
            System.out.println("Olha aí: " + name + ": " + value);
            siteService.save(site);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Site> getReadInWebClasses(Long course) {
        Map<String, String> m = new HashMap<String, String>();
        m.put(Property.COURSE.getName(), Long.toString(course));
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

    @Override
    public Integer countUsers(String siteId){
        try {
            return siteService.getSite(siteId).getUsers().size();
        } catch (IdUnusedException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public User getUser() {
        return userDirectoryService.getCurrentUser();
    }

    @Override
    public List<User> getTeachers() {
        List<User> allUsers = userDirectoryService.getUsers();
        ArrayList<User> teachers = new ArrayList<User>();

        for(User u : allUsers){
            if(u.getType().equals("Professor")){
                teachers.add(u);
            }
        }
        return teachers;
    }

    @Override
    public void saveSite(Site site) {
        try {
            siteService.save(site);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        } catch (PermissionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Site getSite(String siteId) {
        try {
            return siteService.getSite(siteId);
        } catch (IdUnusedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
