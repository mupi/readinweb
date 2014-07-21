package br.unicamp.iel.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sakaiproject.genericdao.hibernate.HibernateCompleteGenericDao;
import org.sakaiproject.site.api.Site;

public class ReadInWebDaoImpl extends HibernateCompleteGenericDao 
	implements ReadInWebDao {

	private static Log log = LogFactory.getLog(ReadInWebDaoImpl.class);

	public void init() {
		log.debug("init");
	}

	public List<Site> getReadInWebSites(){
		Site s = null;
		return null;
	}

}
