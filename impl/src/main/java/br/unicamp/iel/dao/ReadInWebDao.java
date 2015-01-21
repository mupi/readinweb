/******************************************************************************
 * ReadinwebDao.java - created by Sakai App Builder -AZ
 * 
 * Copyright (c) 2008 Sakai Project/Sakai Foundation
 * Licensed under the Educational Community License version 1.0
 * 
 * A copy of the Educational Community License has been included in this 
 * distribution and is available at: http://www.opensource.org/licenses/ecl1.php
 * 
 *****************************************************************************/

package br.unicamp.iel.dao;

import java.util.List;

import org.sakaiproject.genericdao.api.GeneralGenericDao;
import org.sakaiproject.site.api.Site;

import br.unicamp.iel.model.reports.UserAccess;


/**
 * This is a specialized DAO that allows the developer to extend
 * the functionality of the generic dao package
 * @author Sakai App Builder -AZ
 */
public interface ReadInWebDao extends GeneralGenericDao {
	public List<Site> getReadInWebSites();
	public List<UserAccess> getUserAccessesReport(String userId);
}
