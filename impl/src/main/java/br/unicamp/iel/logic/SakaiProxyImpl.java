package br.unicamp.iel.logic;

import lombok.Getter;
import lombok.Setter;

import org.apache.log4j.Logger;
import org.sakaiproject.authz.api.Role;
import org.sakaiproject.authz.api.SecurityService;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.entity.api.EntityPropertyNotDefinedException;
import org.sakaiproject.entity.api.EntityPropertyTypeException;
import org.sakaiproject.event.api.EventTrackingService;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.tool.api.Placement;
import org.sakaiproject.tool.api.SessionManager;
import org.sakaiproject.tool.api.ToolManager;
import org.sakaiproject.user.api.User;
import org.sakaiproject.user.api.UserDirectoryService;

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
	public void postEvent(String event,String reference,boolean modify) {
		eventTrackingService.post(eventTrackingService.newEvent(event,reference,modify));
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

		String skin = siteService.findTool(sessionManager.getCurrentToolSession().getPlacementId()).getSkin();			

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
            return site.getProperties().getLongProperty("readinwebcourse");
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

}
